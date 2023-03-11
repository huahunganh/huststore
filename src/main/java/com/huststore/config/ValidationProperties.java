

package com.huststore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties(prefix = "huststore.validation")
public class ValidationProperties {
  @NotBlank
  private String idNumberRegexp;
  @NotBlank
  private String phoneNumberRegexp;

  public String getIdNumberRegexp() {
    return idNumberRegexp;
  }

  public void setIdNumberRegexp(String idNumberRegexp) {
    this.idNumberRegexp = idNumberRegexp;
  }

  public String getPhoneNumberRegexp() {
    return phoneNumberRegexp;
  }

  public void setPhoneNumberRegexp(String phoneNumberRegexp) {
    this.phoneNumberRegexp = phoneNumberRegexp;
  }
}
