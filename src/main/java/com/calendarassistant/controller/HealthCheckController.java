package com.calendarassistant.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping(value = "/health")
  @ResponseStatus(HttpStatus.OK)
  public String healthCheck() {
    return "OK";
  }

  @GetMapping(value = "/currentTime")
  @ResponseStatus(HttpStatus.OK)
  public String getCurrentTime() {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
    return df.format(date);
  }
}
