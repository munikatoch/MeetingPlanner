package com.calendarassistant.utils;

import java.util.Comparator;

import com.calendarassistant.contract.model.Meeting;
import com.calendarassistant.contract.model.TimeSlot;

public class MeetingComparator implements Comparator<Meeting> {
  @Override
  public int compare(Meeting first, Meeting second) {
    TimeSlot firstTimeSlot = first.getTimeSlot();
    TimeSlot secondTimeSlot = second.getTimeSlot();
    return firstTimeSlot
        .getMeetingStartDateTime()
        .compareTo(secondTimeSlot.getMeetingStartDateTime());
  }
}
