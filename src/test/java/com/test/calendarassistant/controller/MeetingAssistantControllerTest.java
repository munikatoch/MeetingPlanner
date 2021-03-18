package com.test.calendarassistant.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.calendarassistant.contract.interfaces.ICalendarAssistantService;
import com.calendarassistant.contract.messages.MeetingConflictsRequest;
import com.calendarassistant.contract.messages.MeetingConflictsResponse;
import com.calendarassistant.contract.model.Employee;
import com.calendarassistant.contract.model.Meeting;
import com.calendarassistant.contract.model.TimeSlot;
import com.calendarassistant.controller.MeetingAssistantController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MeetingAssistantController.class)
public class MeetingAssistantControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ICalendarAssistantService service;

  @Test
  public void getConflictMeetingResponseWithOkStatus() throws Exception {
    MeetingConflictsRequest request = createMeetingConflictsRequest();
    MeetingConflictsResponse expected = createMeetingConflictsResponse();
    Mockito.when(service.getMeetingConflicts(ArgumentMatchers.any(MeetingConflictsRequest.class)))
        .thenReturn(expected);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/meeting/conflicts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));
  }

  private MeetingConflictsResponse createMeetingConflictsResponse() {
    MeetingConflictsResponse response = new MeetingConflictsResponse();
    List<Meeting> meetings = new ArrayList<>();
    meetings.add(createMeetingTwo());
    meetings.add(createMeetingFour());
    meetings.add(createMeetingSix());
    meetings.add(createMeetingEight());
    response.setMeetings(meetings);
    return response;
  }

  private MeetingConflictsRequest createMeetingConflictsRequest() {
    MeetingConflictsRequest request = new MeetingConflictsRequest();
    request.setOwner(createDeveloperEmployee());
    request.setMeetings(createMeetingList());
    return request;
  }

  private List<Meeting> createMeetingList() {
    List<Meeting> meetings = new ArrayList<>();
    meetings.add(createMeetingOne());
    meetings.add(createMeetingTwo());
    meetings.add(createMeetingThree());
    meetings.add(createMeetingFour());
    meetings.add(createMeetingFive());
    meetings.add(createMeetingSix());
    meetings.add(createMeetingSeven());
    meetings.add(createMeetingEight());
    return meetings;
  }

  private Meeting createMeetingOne() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 08:53AM", "2022-03-16 10:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createDeveloperEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingTwo() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 08:15AM", "2022-03-16 09:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createDirectorEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingThree() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 10:15AM", "2022-03-16 11:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingFour() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 10:15AM", "2022-03-16 11:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createCEOEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingFive() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 01:15PM", "2022-03-16 02:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingSix() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 01:15PM", "2022-03-16 02:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    List<Employee> employees = createEmployeeList();
    employees.add(createEmployeeFour());
    meeting.setEmployees(employees);
    return meeting;
  }

  private Meeting createMeetingSeven() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 04:15PM", "2022-03-16 05:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    List<Employee> employees = createEmployeeList();
    employees.add(createEmployeeFour());
    meeting.setEmployees(employees);
    return meeting;
  }

  private Meeting createMeetingEight() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 04:15PM", "2022-03-16 05:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    List<Employee> employees = createEmployeeList();
    employees.add(createManagerEmployee());
    meeting.setEmployees(employees);
    return meeting;
  }

  private TimeSlot getTimeSlot(String start, String end) {
    try {
      Date dateStart = new SimpleDateFormat("yyyy-MM-dd hh:mma").parse(start);
      Date dateEnd = new SimpleDateFormat("yyyy-MM-dd hh:mma").parse(end);
      TimeSlot timeSlot = new TimeSlot(dateStart, dateEnd);
      return timeSlot;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Employee createDeveloperEmployee() {
    Employee employee = new Employee();
    employee.setFullName("Developer");
    employee.setRank("Developer");
    return employee;
  }

  private Employee createDirectorEmployee() {
    Employee employee = new Employee();
    employee.setFullName("Director");
    employee.setRank("Director");
    return employee;
  }

  private Employee createManagerEmployee() {
    Employee employee = new Employee();
    employee.setFullName("Manager");
    employee.setRank("Manager");
    return employee;
  }

  private Employee createCEOEmployee() {
    Employee employee = new Employee();
    employee.setFullName("CEO");
    employee.setRank("CEO");
    return employee;
  }

  private List<Employee> createEmployeeList() {
    List<Employee> employees = new ArrayList<>();
    employees.add(createEmployeeOne());
    employees.add(createEmployeeTwo());
    employees.add(createEmployeeThree());
    return employees;
  }

  private Employee createEmployeeOne() {
    Employee employee = new Employee();
    employee.setFullName("Developer1");
    employee.setRank("Developer");
    return employee;
  }

  private Employee createEmployeeTwo() {
    Employee employee = new Employee();
    employee.setFullName("Developer2");
    employee.setRank("Developer");
    return employee;
  }

  private Employee createEmployeeThree() {
    Employee employee = new Employee();
    employee.setFullName("Developer3");
    employee.setRank("Developer");
    return employee;
  }

  private Employee createEmployeeFour() {
    Employee employee = new Employee();
    employee.setFullName("Developer4");
    employee.setRank("Developer");
    return employee;
  }
}
