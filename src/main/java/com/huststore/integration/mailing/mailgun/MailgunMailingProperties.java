

package com.huststore.integration.mailing.mailgun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

/**
 * Holds configuration properties for using Mailgun as a mail service provider.<br/>
 * A Mailgun account is required to use this.<br/>
 * Read about Mailgun on their website https://www.mailgun.com/
 */
@Validated
@Configuration
@ConfigurationProperties(prefix = "huststore.integration.mailing.mailgun")
@Profile("mailgun")
public class MailgunMailingProperties {
  private String apiKey;
  private String domain;
  private String customerOrderPaymentTemplate;
  private String customerOrderConfirmationTemplate;
  private String customerOrderRejectionTemplate;
  private String customerOrderCompletionTemplate;
  private String ownerOrderConfirmationTemplate;
  private String ownerOrderRejectionTemplate;
  private String ownerOrderCompletionTemplate;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getCustomerOrderPaymentTemplate() {
    return customerOrderPaymentTemplate;
  }

  public void setCustomerOrderPaymentTemplate(String customerOrderPaymentTemplate) {
    this.customerOrderPaymentTemplate = customerOrderPaymentTemplate;
  }

  public String getCustomerOrderConfirmationTemplate() {
    return customerOrderConfirmationTemplate;
  }

  public void setCustomerOrderConfirmationTemplate(String customerOrderConfirmationTemplate) {
    this.customerOrderConfirmationTemplate = customerOrderConfirmationTemplate;
  }

  public String getCustomerOrderRejectionTemplate() {
    return customerOrderRejectionTemplate;
  }

  public void setCustomerOrderRejectionTemplate(String customerOrderRejectionTemplate) {
    this.customerOrderRejectionTemplate = customerOrderRejectionTemplate;
  }

  public String getCustomerOrderCompletionTemplate() {
    return customerOrderCompletionTemplate;
  }

  public void setCustomerOrderCompletionTemplate(String customerOrderCompletionTemplate) {
    this.customerOrderCompletionTemplate = customerOrderCompletionTemplate;
  }

  public String getOwnerOrderConfirmationTemplate() {
    return ownerOrderConfirmationTemplate;
  }

  public void setOwnerOrderConfirmationTemplate(String ownerOrderConfirmationTemplate) {
    this.ownerOrderConfirmationTemplate = ownerOrderConfirmationTemplate;
  }

  public String getOwnerOrderRejectionTemplate() {
    return ownerOrderRejectionTemplate;
  }

  public void setOwnerOrderRejectionTemplate(String ownerOrderRejectionTemplate) {
    this.ownerOrderRejectionTemplate = ownerOrderRejectionTemplate;
  }

  public String getOwnerOrderCompletionTemplate() {
    return ownerOrderCompletionTemplate;
  }

  public void setOwnerOrderCompletionTemplate(String ownerOrderCompletionTemplate) {
    this.ownerOrderCompletionTemplate = ownerOrderCompletionTemplate;
  }}
