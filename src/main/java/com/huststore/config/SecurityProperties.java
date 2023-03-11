

package com.huststore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Validated
@Configuration
@ConfigurationProperties(prefix = "huststore.security")
public class SecurityProperties {

  @NotBlank
  private String jwtSecretKey;
  @PositiveOrZero
  private int jwtExpirationAfterMinutes;
  @PositiveOrZero
  private int jwtExpirationAfterHours;
  @PositiveOrZero
  private int jwtExpirationAfterDays;
  @Min(6)
  private int bcryptEncoderStrength;
  private boolean guestUserEnabled;
  private String guestUserName;
  private boolean accountProtectionEnabled;
  @Min(1)
  private long protectedAccountId;

  public String getJwtSecretKey() {
    return jwtSecretKey;
  }

  public void setJwtSecretKey(String jwtSecretKey) {
    this.jwtSecretKey = jwtSecretKey;
  }

  public int getJwtExpirationAfterDays() {
    return jwtExpirationAfterDays;
  }

  public void setJwtExpirationAfterDays(int jwtExpirationAfterDays) {
    this.jwtExpirationAfterDays = jwtExpirationAfterDays;
  }

  public int getJwtExpirationAfterMinutes() {
    return jwtExpirationAfterMinutes;
  }

  public void setJwtExpirationAfterMinutes(int jwtExpirationAfterMinutes) {
    this.jwtExpirationAfterMinutes = jwtExpirationAfterMinutes;
  }

  public int getJwtExpirationAfterHours() {
    return jwtExpirationAfterHours;
  }

  public void setJwtExpirationAfterHours(int jwtExpirationAfterHours) {
    this.jwtExpirationAfterHours = jwtExpirationAfterHours;
  }

  public int getBcryptEncoderStrength() {
    return bcryptEncoderStrength;
  }

  public void setBcryptEncoderStrength(int bcryptEncoderStrength) {
    this.bcryptEncoderStrength = bcryptEncoderStrength;
  }

  public boolean isGuestUserEnabled() {
    return guestUserEnabled;
  }

  public void setGuestUserEnabled(boolean guestUserEnabled) {
    this.guestUserEnabled = guestUserEnabled;
  }

  public String getGuestUserName() {
    return guestUserName;
  }

  public void setGuestUserName(String guestUserName) {
    this.guestUserName = guestUserName;
  }

  public boolean isAccountProtectionEnabled() {
    return accountProtectionEnabled;
  }

  public void setAccountProtectionEnabled(boolean accountProtectionEnabled) {
    this.accountProtectionEnabled = accountProtectionEnabled;
  }

  public long getProtectedAccountId() {
    return protectedAccountId;
  }

  public void setProtectedAccountId(long protectedAccountId) {
    this.protectedAccountId = protectedAccountId;
  }

}
