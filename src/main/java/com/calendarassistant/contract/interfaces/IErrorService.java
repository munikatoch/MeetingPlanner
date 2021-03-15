package com.calendarassistant.contract.interfaces;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.calendarassistant.contract.messages.ErrorResponse;

public interface IErrorService {
  List<String> getFieldErrors(List<FieldError> errors);

  ErrorResponse getObjectErrors(List<ObjectError> allErrors);
}
