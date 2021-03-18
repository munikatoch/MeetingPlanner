package com.test.calendarassistant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.calendarassistant.contract.model.Employee;
import com.calendarassistant.contract.model.Meeting;
import com.calendarassistant.contract.model.TimeSlot;

public class MeetingValidatorTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validator;

  @BeforeClass
  public static void createValidator() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @AfterClass
  public static void close() {
    validatorFactory.close();
  }

  @Test
  public void testStartDateNotBeforeToday() {
    Meeting meeting = createMeetingForDateValidation();
    TimeSlot timeSlot = getTimeSlot("2021-03-16 04:15PM", "2022-03-16 05:15PM");
    meeting.setTimeSlot(timeSlot);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Meeting start date before today or end date before start date");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  @Test
  public void testEndDateNotBeforeStartDate() {
    Meeting meeting = createMeetingForDateValidation();
    TimeSlot timeSlot = getTimeSlot("2022-03-16 04:15PM", "2022-02-16 05:15PM");
    meeting.setTimeSlot(timeSlot);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Meeting start date before today or end date before start date");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  @Test
  public void testTimeSlotCanNotBeNull() {
    Meeting meeting = createMeetingForDateValidation();
    TimeSlot timeSlot = null;
    meeting.setTimeSlot(timeSlot);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Meeting Time Slot Is Mandatory");
    expected.add("Meeting start date before today or end date before start date");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  @Test
  public void testOrganizerNameCanNotBeEmpty() {
    Employee organizer = createEmployeeWithNameConstraint("");
    Meeting meeting = createMeetingForOrganizerValidation();
    meeting.setOrganizer(organizer);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Name is required");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  @Test
  public void testOrganizerNameCanNotBeWhiteSpaces() {
    Employee organizer = createEmployeeWithNameConstraint("     ");
    Meeting meeting = createMeetingForOrganizerValidation();
    meeting.setOrganizer(organizer);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Name is required");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  @Test
  public void testOrganizerNameCanNotBeNull() {
    Employee organizer = createEmployeeWithNameConstraint(null);
    Meeting meeting = createMeetingForOrganizerValidation();
    meeting.setOrganizer(organizer);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Name is required");
    expected.add("Name is required");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  @Test
  public void testOrganizerCanNotBeNull() {
    Employee organizer = null;
    Meeting meeting = createMeetingForOrganizerValidation();
    meeting.setOrganizer(organizer);
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    List<String> expected = new ArrayList<>();
    expected.add("Organizer is mandatory");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    while (it.hasNext()) {
      Assert.assertTrue(expected.contains(it.next().getMessage()));
    }
  }

  private Meeting createMeetingForOrganizerValidation() {
    TimeSlot timeSlot = getTimeSlot("2022-03-16 04:15PM", "2022-03-16 05:15PM");
    Meeting meeting = new Meeting();
    meeting.setEmployees(createEmployeeList());
    meeting.setTimeSlot(timeSlot);
    return meeting;
  }

  private Meeting createMeetingForDateValidation() {
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createDeveloperEmployee());
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Employee createDeveloperEmployee() {
    Employee employee = new Employee();
    employee.setFullName("Developer");
    employee.setRank("Developer");
    return employee;
  }

  private Employee createEmployeeWithNameConstraint(String name) {
    Employee employee = new Employee();
    employee.setFullName(name);
    employee.setRank("Developer");
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
}
