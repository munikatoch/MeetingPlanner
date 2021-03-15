package com.calendarassistant.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RankValidator.class})
@Documented
public @interface CustomRankValidator {
  String message() default "Rank must be either of developer, manager, director, CEO";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
