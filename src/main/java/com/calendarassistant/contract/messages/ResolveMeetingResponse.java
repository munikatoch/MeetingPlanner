package com.calendarassistant.contract.messages;

import java.util.List;

import com.calendarassistant.contract.model.Meeting;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResolveMeetingResponse extends Response {

  @JsonProperty("meetings")
  private List<Meeting> meetings;

  public List<Meeting> getMeetings() {
    return meetings;
  }

  public void setMeetings(List<Meeting> meetings) {
    this.meetings = meetings;
  }
}
