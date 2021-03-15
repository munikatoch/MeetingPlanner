package com.calendarassistant.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calendarassistant.contract.interfaces.ICalendarAssistantService;
import com.calendarassistant.contract.interfaces.IErrorService;
import com.calendarassistant.contract.messages.AvailableMeetingSlotResponse;
import com.calendarassistant.contract.messages.ErrorResponse;
import com.calendarassistant.contract.messages.MeetingConflictsRequest;
import com.calendarassistant.contract.messages.MeetingConflictsResponse;
import com.calendarassistant.contract.messages.MeetingForTwoRequest;
import com.calendarassistant.contract.messages.MeetingForTwoResponse;
import com.calendarassistant.contract.messages.ResolveMeetingRequest;
import com.calendarassistant.contract.messages.ResolveMeetingResponse;
import com.calendarassistant.contract.messages.Response;

@RestController
@RequestMapping(value = "/meeting")
public class MeetingAssistantController {

  @Autowired private ICalendarAssistantService calendarAssistant;
  @Autowired private IErrorService errorService;

  @PostMapping(value = "/conflicts")
  public ResponseEntity<Response> getMeetingConflicts(
      @Valid @RequestBody MeetingConflictsRequest request, BindingResult result) {
    try {
      if (result.hasErrors()) {
        ErrorResponse errors = errorService.getObjectErrors(result.getAllErrors());
        errors.getErrors().addAll(errorService.getFieldErrors(result.getFieldErrors()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
      }
      MeetingConflictsResponse response = this.calendarAssistant.getMeetingConflicts(request);
      if (response.getMeetings().isEmpty()) {
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/resolve")
  public ResponseEntity<Response> getResolvedMeetingConflicts(
      @Valid @RequestBody ResolveMeetingRequest request, BindingResult result) {
    try {
      if (result.hasErrors()) {
        ErrorResponse errors = errorService.getObjectErrors(result.getAllErrors());
        errors.getErrors().addAll(errorService.getFieldErrors(result.getFieldErrors()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
      }
      ResolveMeetingResponse response = this.calendarAssistant.getResolvedMeetings(request);
      if (response.getMeetings().isEmpty()) {
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/vacant")
  public ResponseEntity<Response> getAvailableMeetingSlots(
      @Valid @RequestBody ResolveMeetingRequest request, BindingResult result) {
    try {
      if (result.hasErrors()) {
        ErrorResponse errors = errorService.getObjectErrors(result.getAllErrors());
        errors.getErrors().addAll(errorService.getFieldErrors(result.getFieldErrors()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
      }
      ResolveMeetingResponse resolvedMeeting = this.calendarAssistant.getResolvedMeetings(request);
      if (resolvedMeeting.getMeetings().isEmpty()) {
        return new ResponseEntity<>(resolvedMeeting, HttpStatus.NO_CONTENT);
      }
      AvailableMeetingSlotResponse response =
          this.calendarAssistant.getAvailableSlots(resolvedMeeting);
      if (response.getTimeSlots().isEmpty()) {
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/fortwo")
  public ResponseEntity<Response> getAvaiableMeetingForTwo(
      @Valid @RequestBody MeetingForTwoRequest request, BindingResult result) {
    try {
      if (result.hasErrors()) {
        ErrorResponse errors = errorService.getObjectErrors(result.getAllErrors());
        errors.getErrors().addAll(errorService.getFieldErrors(result.getFieldErrors()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
      }
      MeetingForTwoResponse response = this.calendarAssistant.getAvaiableMeetingForTwo(request);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
