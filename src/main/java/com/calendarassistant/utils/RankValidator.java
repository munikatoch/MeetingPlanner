package com.calendarassistant.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RankValidator implements ConstraintValidator<CustomRankValidator, String> {

  private static final List<String> ranks =
      new ArrayList<>(Arrays.asList("DEVELOPER", "MANAGER", "DIRECTOR", "CEO"));

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return ranks.contains(value.toUpperCase());
  }
}
