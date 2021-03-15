package com.calendarassistant.contract.messages;

import java.util.List;

import com.calendarassistant.contract.model.TimeSlot;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMeetingSlotResponse extends Response {
  @JsonProperty("timeSlots")
  private List<TimeSlot> timeSlots;

  public List<TimeSlot> getTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(List<TimeSlot> meetings) {
    this.timeSlots = meetings;
  }
}
