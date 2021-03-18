package com.calendarassistant.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.calendarassistant.contract.model.TimeSlot;

public class TimeSlotValidator implements ConstraintValidator<CustomTimeSlotValidator, TimeSlot> {

  @Override
  public boolean isValid(TimeSlot value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    Date meetingStart = value.getMeetingStartDateTime();
    Date meetingEnd = value.getMeetingEndDateTime();
    if (meetingStart == null || meetingEnd == null) {
      return false;
    }
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime meetingStartInLocalDateTime =
        meetingStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    LocalDateTime meetingEndInLocalDateTime =
        meetingEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    return meetingStartInLocalDateTime.compareTo(now) >= 0
        && meetingEndInLocalDateTime.compareTo(meetingStartInLocalDateTime) >= 0;
  }
}
