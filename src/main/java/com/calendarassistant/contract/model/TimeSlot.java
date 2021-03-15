package com.calendarassistant.contract.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeSlot {

  @JsonProperty("start")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mma", timezone = "Asia/Kolkata")
  private Date meetingStartDateTime;

  @JsonProperty("end")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mma", timezone = "Asia/Kolkata")
  private Date meetingEndDateTime;

  public TimeSlot() {}

  public TimeSlot(Date meetingStartDateTime, Date meetingEndDateTime) {
    this.meetingStartDateTime = meetingStartDateTime;
    this.meetingEndDateTime = meetingEndDateTime;
  }

  public Date getMeetingStartDateTime() {
    return meetingStartDateTime;
  }

  public void setMeetingStartDateTime(Date meetingStartDateTime) {
    this.meetingStartDateTime = meetingStartDateTime;
  }

  public Date getMeetingEndDateTime() {
    return meetingEndDateTime;
  }

  public void setMeetingEndDateTime(Date meetingEndDateTime) {
    this.meetingEndDateTime = meetingEndDateTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((meetingEndDateTime == null) ? 0 : meetingEndDateTime.hashCode());
    result =
        prime * result + ((meetingStartDateTime == null) ? 0 : meetingStartDateTime.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TimeSlot timeSlot = (TimeSlot) obj;
    if (meetingEndDateTime == null) {
      if (timeSlot.meetingEndDateTime != null) return false;
    } else if (!meetingEndDateTime.equals(timeSlot.meetingEndDateTime)) return false;
    if (meetingStartDateTime == null) {
      if (timeSlot.meetingStartDateTime != null) return false;
    } else if (!meetingStartDateTime.equals(timeSlot.meetingStartDateTime)) return false;
    return true;
  }
}
