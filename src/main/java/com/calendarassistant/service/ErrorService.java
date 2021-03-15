package com.calendarassistant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.calendarassistant.contract.interfaces.IErrorService;
import com.calendarassistant.contract.messages.ErrorResponse;

@Service("errorService")
public class ErrorService implements IErrorService {

  public List<String> getFieldErrors(List<FieldError> fieldErrors) {
    List<String> errors = new ArrayList<>();
    for (FieldError fe : fieldErrors) {
      errors.add(fe.getField() + ":" + fe.getDefaultMessage());
    }
    return errors;
  }

  @Override
  public ErrorResponse getObjectErrors(List<ObjectError> allErrors) {
    ErrorResponse response = new ErrorResponse();
    List<String> errors = new ArrayList<>();
    for (ObjectError fe : allErrors) {
      errors.add(fe.getObjectName() + ":" + fe.getDefaultMessage());
    }
    response.setErrors(errors);
    return response;
  }
}
