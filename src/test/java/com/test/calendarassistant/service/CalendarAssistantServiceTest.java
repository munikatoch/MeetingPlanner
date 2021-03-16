package com.test.calendarassistant.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.calendarassistant.contract.interfaces.ICalendarAssistantService;
import com.calendarassistant.contract.messages.MeetingConflictsRequest;
import com.calendarassistant.contract.messages.MeetingConflictsResponse;
import com.calendarassistant.contract.model.Employee;
import com.calendarassistant.contract.model.Meeting;
import com.calendarassistant.contract.model.TimeSlot;
import com.calendarassistant.service.CalendarAssistantService;

public class CalendarAssistantServiceTest {

  private ICalendarAssistantService service;

  @Before
  public void setup() {
    service = new CalendarAssistantService();
  }

  @Test
  public void testConflictingMeetings() {
    MeetingConflictsRequest request = createMeetingConflictsRequest();
    MeetingConflictsResponse actual = service.getMeetingConflicts(request);
    MeetingConflictsResponse expected = createMeetingConflictsResponse();
    Assert.assertNotNull(actual);
    Assert.assertTrue(expected.getMeetings().containsAll(actual.getMeetings()));
  }

  private MeetingConflictsResponse createMeetingConflictsResponse() {
    MeetingConflictsResponse response = new MeetingConflictsResponse();
    List<Meeting> meetings = new ArrayList<>();
    meetings.add(createMeetingTwo());
    meetings.add(createMeetingFour());
    meetings.add(createMeetingSix());
    meetings.add(createMeetingSeven());
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
    TimeSlot timeSlot = getTimeSlot("2021-03-16 08:53AM", "2021-03-16 10:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createDeveloperEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingTwo() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 08:15AM", "2021-03-16 09:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createDirectorEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingThree() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 10:15AM", "2021-03-16 11:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingFour() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 10:15AM", "2021-03-16 11:15AM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createCEOEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingFive() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 01:15PM", "2021-03-16 02:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Meeting createMeetingSix() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 01:15PM", "2021-03-16 02:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    List<Employee> employees = createEmployeeList();
    employees.add(createEmployeeFour());
    meeting.setEmployees(employees);
    return meeting;
  }

  private Meeting createMeetingSeven() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 04:15PM", "2021-03-16 05:15PM");
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createManagerEmployee());
    meeting.setTimeSlot(timeSlot);
    List<Employee> employees = createEmployeeList();
    employees.add(createEmployeeFour());
    meeting.setEmployees(employees);
    return meeting;
  }

  private Meeting createMeetingEight() {
    TimeSlot timeSlot = getTimeSlot("2021-03-16 04:15PM", "2021-03-16 05:15PM");
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
