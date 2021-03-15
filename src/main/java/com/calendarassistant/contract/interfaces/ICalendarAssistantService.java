package com.calendarassistant.contract.interfaces;

import com.calendarassistant.contract.messages.AvailableMeetingSlotResponse;
import com.calendarassistant.contract.messages.MeetingConflictsRequest;
import com.calendarassistant.contract.messages.MeetingConflictsResponse;
import com.calendarassistant.contract.messages.MeetingForTwoRequest;
import com.calendarassistant.contract.messages.MeetingForTwoResponse;
import com.calendarassistant.contract.messages.ResolveMeetingRequest;
import com.calendarassistant.contract.messages.ResolveMeetingResponse;

public interface ICalendarAssistantService {

  MeetingConflictsResponse getMeetingConflicts(MeetingConflictsRequest request);

  ResolveMeetingResponse getResolvedMeetings(ResolveMeetingRequest meetings);

  AvailableMeetingSlotResponse getAvailableSlots(ResolveMeetingResponse request);

  MeetingForTwoResponse getAvaiableMeetingForTwo(MeetingForTwoRequest request);
}
