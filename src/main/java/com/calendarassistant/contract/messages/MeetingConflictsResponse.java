package com.calendarassistant.contract.messages;

import java.util.List;

import com.calendarassistant.contract.model.Meeting;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingConflictsResponse extends Response {

  @JsonProperty("meetings")
  private List<Meeting> meetings;

  public List<Meeting> getMeetings() {
    return meetings;
  }

  public void setMeetings(List<Meeting> meetings) {
    this.meetings = meetings;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((meetings == null) ? 0 : meetings.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    MeetingConflictsResponse other = (MeetingConflictsResponse) obj;
    if (meetings == null) {
      if (other.meetings != null) return false;
    } else if (!meetings.containsAll(other.meetings)) return false;
    return true;
  }
}
