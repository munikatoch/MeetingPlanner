package com.test.calendarassistant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
    Meeting meeting = createMeetingBeforeToday("2021-03-16 04:15PM", "2022-03-16 05:15PM");
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    Set<String> expected = new HashSet<>();
    expected.add("Meeting start date before today or end date before start date");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    for (String message : expected) {
      Assert.assertEquals(message, it.next().getMessage());
    }
  }

  @Test
  public void testEndDateNotBeforeStartDate() {
    Meeting meeting = createMeetingBeforeToday("2022-03-16 04:15PM", "2022-02-16 05:15PM");
    Set<ConstraintViolation<Meeting>> actual = validator.validate(meeting);
    Set<String> expected = new HashSet<>();
    expected.add("Meeting start date before today or end date before start date");
    Assert.assertEquals(expected.size(), actual.size());
    Iterator<ConstraintViolation<Meeting>> it = actual.iterator();
    for (String message : expected) {
      Assert.assertEquals(message, it.next().getMessage());
    }
  }

  private Meeting createMeetingBeforeToday(String start, String end) {
    TimeSlot timeSlot = getTimeSlot(start, end);
    Meeting meeting = new Meeting();
    meeting.setOrganizer(createDeveloperEmployee());
    meeting.setTimeSlot(timeSlot);
    meeting.setEmployees(createEmployeeList());
    return meeting;
  }

  private Employee createDeveloperEmployee() {
    Employee employee = new Employee();
    employee.setFullName("Developer");
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
