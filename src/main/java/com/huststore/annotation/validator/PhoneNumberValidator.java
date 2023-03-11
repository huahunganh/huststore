package com.huststore.annotation.validator;

import com.huststore.annotation.PhoneNumber;
import com.huststore.config.ValidationProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  @Autowired
  private ValidationProperties validationProperties;

  private Pattern pattern;

  @Override
  public void initialize(PhoneNumber constraintAnnotation) {
    pattern = Pattern.compile(validationProperties.getPhoneNumberRegexp());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
