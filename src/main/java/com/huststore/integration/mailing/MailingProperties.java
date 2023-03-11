

package com.huststore.integration.mailing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * General, implementation-agnostic properties for mailing services
 */
@Validated
@Configuration
@ConfigurationProperties(prefix = "huststore.integration.mailing")
public class MailingProperties {
  private String dateFormat;
  private String dateTimezone;
  private String ownerName;
  private String ownerEmail;
  private String senderEmail;
  private String customerOrderPaymentSubject;
  private String customerOrderConfirmationSubject;
  private String customerOrderRejectionSubject;
  private String customerOrderCompletionSubject;
  private String ownerOrderConfirmationSubject;
  private String ownerOrderRejectionSubject;
  private String ownerOrderCompletionSubject;

  public String getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  public String getDateTimezone() {
    return dateTimezone;
  }

  public void setDateTimezone(String dateTimezone) {
    this.dateTimezone = dateTimezone;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }

  public String getSenderEmail() {
    return senderEmail;
  }

  public void setSenderEmail(String senderEmail) {
    this.senderEmail = senderEmail;
  }

  public String getCustomerOrderPaymentSubject() {
    return customerOrderPaymentSubject;
  }

  public void setCustomerOrderPaymentSubject(String customerOrderPaymentSubject) {
    this.customerOrderPaymentSubject = customerOrderPaymentSubject;
  }

  public String getCustomerOrderConfirmationSubject() {
    return customerOrderConfirmationSubject;
  }

  public void setCustomerOrderConfirmationSubject(String customerOrderConfirmationSubject) {
    this.customerOrderConfirmationSubject = customerOrderConfirmationSubject;
  }

  public String getCustomerOrderRejectionSubject() {
    return customerOrderRejectionSubject;
  }

  public void setCustomerOrderRejectionSubject(String customerOrderRejectionSubject) {
    this.customerOrderRejectionSubject = customerOrderRejectionSubject;
  }

  public String getCustomerOrderCompletionSubject() {
    return customerOrderCompletionSubject;
  }

  public void setCustomerOrderCompletionSubject(String customerOrderCompletionSubject) {
    this.customerOrderCompletionSubject = customerOrderCompletionSubject;
  }

  public String getOwnerOrderConfirmationSubject() {
    return ownerOrderConfirmationSubject;
  }

  public void setOwnerOrderConfirmationSubject(String ownerOrderConfirmationSubject) {
    this.ownerOrderConfirmationSubject = ownerOrderConfirmationSubject;
  }

  public String getOwnerOrderRejectionSubject() {
    return ownerOrderRejectionSubject;
  }

  public void setOwnerOrderRejectionSubject(String ownerOrderRejectionSubject) {
    this.ownerOrderRejectionSubject = ownerOrderRejectionSubject;
  }

  public String getOwnerOrderCompletionSubject() {
    return ownerOrderCompletionSubject;
  }

  public void setOwnerOrderCompletionSubject(String ownerOrderCompletionSubject) {
    this.ownerOrderCompletionSubject = ownerOrderCompletionSubject;
  }
}
