package com.calendarassistant.contract.model;

import java.util.List;

import javax.validation.Valid;

import com.calendarassistant.utils.CustomTimeSlotValidator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Meeting {

  @Valid
  @JsonProperty("organizer")
  private Employee organizer;

  @CustomTimeSlotValidator(
      message = "Meeting start date before today or end date before start date")
  @JsonProperty("timeslot")
  private TimeSlot timeSlot;

  @JsonProperty("employees")
  private List<@Valid Employee> employees;

  public TimeSlot getTimeSlot() {
    return timeSlot;
  }

  public void setTimeSlot(TimeSlot timeSlot) {
    this.timeSlot = timeSlot;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }

  public Employee getOrganizer() {
    return organizer;
  }

  public void setOrganizer(Employee organizer) {
    this.organizer = organizer;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((employees == null) ? 0 : employees.hashCode());
    result = prime * result + ((organizer == null) ? 0 : organizer.hashCode());
    result = prime * result + ((timeSlot == null) ? 0 : timeSlot.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Meeting other = (Meeting) obj;
    if (employees == null) {
      if (other.employees != null) return false;
    } else if (!employees.containsAll(other.employees)) return false;
    if (organizer == null) {
      if (other.organizer != null) return false;
    } else if (!organizer.equals(other.organizer)) return false;
    if (timeSlot == null) {
      if (other.timeSlot != null) return false;
    } else if (!timeSlot.equals(other.timeSlot)) return false;
    return true;
  }
}
