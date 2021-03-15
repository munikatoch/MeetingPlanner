package com.calendarassistant.contract.messages;

import java.util.List;

import javax.validation.Valid;

import com.calendarassistant.contract.model.Employee;
import com.calendarassistant.contract.model.Meeting;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingConflictsRequest {

  @JsonProperty("meetings")
  private List<@Valid Meeting> meetings;

  @Valid
  @JsonProperty("owner")
  private Employee owner;

  public Employee getOwner() {
    return owner;
  }

  public void setOwner(Employee owner) {
    this.owner = owner;
  }

  public List<Meeting> getMeetings() {
    return meetings;
  }

  public void setMeetings(List<Meeting> meetings) {
    this.meetings = meetings;
  }
}
