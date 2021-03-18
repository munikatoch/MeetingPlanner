package com.calendarassistant.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.calendarassistant.contract.interfaces.ICalendarAssistantService;
import com.calendarassistant.contract.messages.AvailableMeetingSlotResponse;
import com.calendarassistant.contract.messages.MeetingConflictsRequest;
import com.calendarassistant.contract.messages.MeetingConflictsResponse;
import com.calendarassistant.contract.messages.MeetingForTwoRequest;
import com.calendarassistant.contract.messages.MeetingForTwoResponse;
import com.calendarassistant.contract.messages.ResolveMeetingRequest;
import com.calendarassistant.contract.messages.ResolveMeetingResponse;
import com.calendarassistant.contract.model.Employee;
import com.calendarassistant.contract.model.Meeting;
import com.calendarassistant.contract.model.TimeSlot;
import com.calendarassistant.utils.MeetingComparator;
import com.calendarassistant.utils.TimeSlotComparator;

@Service("calendarAssistant")
public class CalendarAssistantService implements ICalendarAssistantService {

  private static final List<String> ranks =
      new ArrayList<>(Arrays.asList("DEVELOPER", "MANAGER", "DIRECTOR", "CEO"));

  @Override
  public MeetingConflictsResponse getMeetingConflicts(MeetingConflictsRequest request) {
    MeetingConflictsResponse response = new MeetingConflictsResponse();
    List<Meeting> conflicts = new ArrayList<>();
    List<Meeting> meetings = request.getMeetings();
    boolean[] isConflict = new boolean[meetings.size()];
    int i;
    int j;
    for (i = 0; i < meetings.size(); i++) {
      for (j = i + 1; j < meetings.size(); j++) {
        if (isConflict[j]) {
          continue;
        }
        TimeSlot firstMeetingTimeSlot = meetings.get(i).getTimeSlot();
        TimeSlot secondMeetingTimeSlot = meetings.get(j).getTimeSlot();
        if (isConflict(
            firstMeetingTimeSlot.getMeetingStartDateTime(),
            firstMeetingTimeSlot.getMeetingEndDateTime(),
            secondMeetingTimeSlot.getMeetingStartDateTime(),
            secondMeetingTimeSlot.getMeetingEndDateTime())) {
          conflicts.add(meetings.get(j));
          isConflict[j] = true;
        }
      }
    }
    response.setMeetings(conflicts);
    return response;
  }

  @Override
  public ResolveMeetingResponse getResolvedMeetings(ResolveMeetingRequest request) {

    List<Meeting> meetings = new ArrayList<>(request.getMeetings());
    Collections.sort(meetings, new MeetingComparator());
    ResolveMeetingResponse response = new ResolveMeetingResponse();
    List<Meeting> resolvedMeeting = resolveMeeting(request.getOwner(), meetings);
    response.setMeetings(resolvedMeeting);
    return response;
  }

  @Override
  public AvailableMeetingSlotResponse getAvailableSlots(ResolveMeetingResponse request) {
    AvailableMeetingSlotResponse response = new AvailableMeetingSlotResponse();
    List<Meeting> meetings = request.getMeetings();
    List<TimeSlot> availableSlots = new ArrayList<>();
    int i;
    for (i = 1; i < meetings.size(); i++) {
      Date avaiableTimeSlotStart = meetings.get(i - 1).getTimeSlot().getMeetingEndDateTime();
      Date avaiableTimeSlotEnd = meetings.get(i).getTimeSlot().getMeetingStartDateTime();
      if (avaiableTimeSlotEnd.after(avaiableTimeSlotStart)) {
        TimeSlot timeSlot = new TimeSlot(avaiableTimeSlotStart, avaiableTimeSlotEnd);
        availableSlots.add(timeSlot);
      }
    }
    response.setTimeSlots(availableSlots);
    return response;
  }

  @Override
  public MeetingForTwoResponse getAvaiableMeetingForTwo(MeetingForTwoRequest request) {
    MeetingForTwoResponse response = new MeetingForTwoResponse();
    List<TimeSlot> firstTimeSlot = request.getMeetingCalendarOfFirst();
    List<TimeSlot> secondTimeSlot = request.getMeetingCalendarOfSecond();
    Collections.sort(firstTimeSlot, new TimeSlotComparator());
    Collections.sort(secondTimeSlot, new TimeSlotComparator());
    List<TimeSlot> availableSlots = new ArrayList<>();
    TimeSlot availableSlot = null;
    int i;
    getAvailableTimeSlots(firstTimeSlot, secondTimeSlot, availableSlots);
    for (i = 1; i < availableSlots.size(); i++) {
      Date end = availableSlots.get(i - 1).getMeetingEndDateTime();
      Date start = availableSlots.get(i).getMeetingStartDateTime();
      if (end.after(start)
          && Duration.between(start.toInstant(), end.toInstant()).getSeconds()
              >= (request.getDurationInMinutes() * 60)) {
        availableSlot = new TimeSlot(start, end);
        break;
      }
    }
    response.setAvailableTimeSlot(availableSlot);
    return response;
  }

  private void getAvailableTimeSlots(
      List<TimeSlot> firstTimeSlot, List<TimeSlot> secondTimeSlot, List<TimeSlot> availableSlots) {
    int i = 1;
    int j = 1;
    while (i < firstTimeSlot.size() || j < secondTimeSlot.size()) {
      if (i == firstTimeSlot.size()) {
        checkToAddInTimeSlot(secondTimeSlot.get(j - 1), secondTimeSlot.get(j), availableSlots);
        j++;
      } else if (j == secondTimeSlot.size()) {
        checkToAddInTimeSlot(firstTimeSlot.get(i - 1), firstTimeSlot.get(i), availableSlots);
        i++;
      } else if (firstTimeSlot
          .get(i - 1)
          .getMeetingEndDateTime()
          .before(secondTimeSlot.get(j - 1).getMeetingEndDateTime())) {
        checkToAddInTimeSlot(firstTimeSlot.get(i - 1), firstTimeSlot.get(i), availableSlots);
        i++;
      } else {
        checkToAddInTimeSlot(secondTimeSlot.get(j - 1), secondTimeSlot.get(j), availableSlots);
        j++;
      }
    }
  }

  private void checkToAddInTimeSlot(
      TimeSlot availableSlotStart, TimeSlot availableSlotEnd, List<TimeSlot> availableSlots) {
    Date avaiableTimeSlotStart = availableSlotStart.getMeetingEndDateTime();
    Date avaiableTimeSlotEnd = availableSlotEnd.getMeetingStartDateTime();
    if (avaiableTimeSlotStart.before(avaiableTimeSlotEnd)) {
      TimeSlot timeSlot = new TimeSlot(avaiableTimeSlotStart, avaiableTimeSlotEnd);
      availableSlots.add(timeSlot);
    }
  }

  private boolean isConflict(Date leftBound, Date rightBound, Date start, Date end) {
    if (start.compareTo(leftBound) >= 0 && start.compareTo(rightBound) < 0) {
      return true;
    } else if (end.compareTo(leftBound) > 0 && end.compareTo(rightBound) <= 0) {
      return true;
    }
    return false;
  }

  private List<Meeting> resolveMeeting(Employee owner, List<Meeting> meetings) {
    List<Meeting> resolvedMeeting = new ArrayList<>();
    int i;
    int lastMeetingIndex = 0;
    for (i = 1; i < meetings.size(); i++) {
      TimeSlot firstMeetingTimeSlot = meetings.get(lastMeetingIndex).getTimeSlot();
      TimeSlot secondMeetingTimeSlot = meetings.get(i).getTimeSlot();
      if (isConflict(
          firstMeetingTimeSlot.getMeetingStartDateTime(),
          firstMeetingTimeSlot.getMeetingEndDateTime(),
          secondMeetingTimeSlot.getMeetingStartDateTime(),
          secondMeetingTimeSlot.getMeetingEndDateTime())) {
        boolean isResolved = resolve(meetings.get(lastMeetingIndex), meetings.get(i), owner);
        if (!isResolved) {
          lastMeetingIndex = i;
        }
      } else {
        resolvedMeeting.add(meetings.get(lastMeetingIndex));
        lastMeetingIndex = i;
      }
    }
    resolvedMeeting.add(meetings.get(lastMeetingIndex));
    return resolvedMeeting;
  }

  private boolean resolve(Meeting first, Meeting second, Employee owner) {
    Meeting meeting = resolveForOrganizer(first, second, owner);
    if (meeting != null) {
      System.out.print("Organizer resolve : ");
      System.out.println(meeting.equals(first));
      return meeting.equals(first);
    }
    meeting = resolveForRank(first, second);
    if (meeting != null) {
      return meeting.equals(first);
    }
    if (first.getEmployees().size() != second.getEmployees().size()) {
      return first.getEmployees().size() > second.getEmployees().size();
    }
    meeting = resolveForRankWeight(first, second);
    return meeting.equals(first);
  }

  private Meeting resolveForRankWeight(Meeting first, Meeting second) {
    int meetingFirstWeight = 0;
    int meetingSecondWeight = 0;
    for (Employee e : first.getEmployees()) {
      meetingFirstWeight += ranks.indexOf(e.getRank().toUpperCase());
    }

    for (Employee e : second.getEmployees()) {
      meetingSecondWeight += ranks.indexOf(e.getRank().toUpperCase());
    }

    if (meetingFirstWeight > meetingSecondWeight) {
      return first;
    }
    return second;
  }

  private Meeting resolveForRank(Meeting first, Meeting second) {
    int compareValue = compareRanks(first.getOrganizer(), second.getOrganizer());
    if (compareValue < 0) {
      return first;
    } else if (compareValue > 0) {
      return second;
    } else {
      return null;
    }
  }

  private int compareRanks(Employee first, Employee second) {
    return ranks.indexOf(second.getRank().toUpperCase())
        - ranks.indexOf(first.getRank().toUpperCase());
  }

  private Meeting resolveForOrganizer(Meeting first, Meeting second, Employee owner) {
    System.out.println(owner.getFullName() + " " + owner.getRank());
    if (first.getOrganizer().equals(owner)) {
      System.out.println("First");
      return first;
    } else if (second.getOrganizer().equals(owner)) {
      System.out.println("Second");
      return second;
    } else {
      return null;
    }
  }
}
