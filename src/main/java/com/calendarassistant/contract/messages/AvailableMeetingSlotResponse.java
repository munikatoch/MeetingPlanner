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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((timeSlots == null) ? 0 : timeSlots.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AvailableMeetingSlotResponse other = (AvailableMeetingSlotResponse) obj;
    if (timeSlots == null) {
      if (other.timeSlots != null) return false;
    } else if (!timeSlots.containsAll(other.timeSlots)) return false;
    return true;
  }
}
