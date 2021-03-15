package com.calendarassistant.utils;

import java.util.Comparator;

import com.calendarassistant.contract.model.TimeSlot;

public class TimeSlotComparator implements Comparator<TimeSlot> {
  @Override
  public int compare(TimeSlot first, TimeSlot second) {
    return first.getMeetingStartDateTime().compareTo(second.getMeetingStartDateTime());
  }
}
