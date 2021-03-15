package com.calendarassistant.contract.messages;

import javax.validation.Valid;

import com.calendarassistant.contract.model.TimeSlot;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingForTwoResponse extends Response {

  @Valid
  @JsonProperty("availableTimeSlot")
  private TimeSlot availableTimeSlot;

  public TimeSlot getAvailableTimeSlot() {
    return availableTimeSlot;
  }

  public void setAvailableTimeSlot(TimeSlot availableTimeSlot) {
    this.availableTimeSlot = availableTimeSlot;
  }
}
