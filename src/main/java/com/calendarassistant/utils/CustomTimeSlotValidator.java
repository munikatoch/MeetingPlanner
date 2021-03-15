package com.calendarassistant.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TimeSlotValidator.class})
@Documented
public @interface CustomTimeSlotValidator {
  String message() default "Invalid meeting";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
