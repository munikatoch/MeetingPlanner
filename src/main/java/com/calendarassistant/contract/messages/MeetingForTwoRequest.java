package com.calendarassistant.contract.messages;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.calendarassistant.contract.model.TimeSlot;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingForTwoRequest {

  @JsonProperty("first")
  List<@Valid TimeSlot> meetingCalendarOfFirst;

  @JsonProperty("second")
  List<@Valid TimeSlot> meetingCalendarOfSecond;

  @Min(1)
  @JsonProperty("duration")
  private int durationInMinutes;

  public List<TimeSlot> getMeetingCalendarOfFirst() {
    return meetingCalendarOfFirst;
  }

  public void setMeetingCalendarOfFirst(List<TimeSlot> meetingCalendarOfFirst) {
    this.meetingCalendarOfFirst = meetingCalendarOfFirst;
  }

  public List<TimeSlot> getMeetingCalendarOfSecond() {
    return meetingCalendarOfSecond;
  }

  public void setMeetingCalendarOfSecond(List<TimeSlot> meetingCalendarOfSecond) {
    this.meetingCalendarOfSecond = meetingCalendarOfSecond;
  }

  public int getDurationInMinutes() {
    return durationInMinutes;
  }

  public void setDurationInMinutes(int durationInMinutes) {
    this.durationInMinutes = durationInMinutes;
  }
}
