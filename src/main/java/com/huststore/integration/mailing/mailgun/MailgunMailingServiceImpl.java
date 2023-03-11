

package com.huststore.integration.mailing.mailgun;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huststore.config.Constants;
import com.huststore.dto.*;
import com.huststore.integration.IMailingIntegrationService;
import com.huststore.integration.exceptions.MailingServiceException;
import com.huststore.integration.mailing.MailingProperties;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Implements Mailgun HTTP API as a mail service provider.<br/>
 * A Mailgun account is required to use this.<br/>
 * Read about Mailgun on their website https://www.mailgun.com/
 */
@Service
@Profile("mailgun")
public class MailgunMailingServiceImpl
  implements IMailingIntegrationService {

  private final Logger logger = LoggerFactory.getLogger(MailgunMailingServiceImpl.class);

  private final MailingProperties internalMailingProperties;
  private final MailgunMailingProperties mailgunProperties;
  private final Map<String, String> orderStatus2MailgunTemplatesMap;
  private final Map<String, String> orderStatus2MailSubjectMap;
  private final ConversionService conversionService;
  private final ObjectMapper mailObjectMapper;

  private static final String CUSTOMER_MAPS_KEY_PREFIX = "customer:";
  private static final String OWNERS_MAPS_KEY_PREFIX = "owners:";

  @Autowired
  public MailgunMailingServiceImpl(MailingProperties mailingProperties,
                                   MailgunMailingProperties mailgunProperties,
                                   ConversionService conversionService) {
    this.internalMailingProperties = mailingProperties;
    this.mailgunProperties = mailgunProperties;
    this.conversionService = conversionService;
    this.orderStatus2MailgunTemplatesMap = this.makeTemplatesMap();
    this.orderStatus2MailSubjectMap = this.makeSubjectsMap();
    this.mailObjectMapper = this.mailObjectMapper();
  }

  @Override
  public void notifyOrderStatusToClient(SellPojo sell)
      throws MailingServiceException {
    String url = "https://api.mailgun.net/v3/" + mailgunProperties.getDomain() + "/messages";
    String mapsKey = CUSTOMER_MAPS_KEY_PREFIX + sell.getStatus();
    if (orderStatus2MailSubjectMap.containsKey(mapsKey)) {
      PersonPojo customer = sell.getCustomer().getPerson();
      String customerName = customer.getFirstName() + " " + customer.getLastName();
      String recipient = customerName + " <" + customer.getEmail() + ">";
      String messageSubject = orderStatus2MailSubjectMap.get(mapsKey);
      String mailgunTemplateName = orderStatus2MailgunTemplatesMap.get(mapsKey);
      String fullSubject = messageSubject + " [#" + sell.getBuyOrder() + "]";
      String variables = this.makeMailgunVariablesFrom(sell);

      HttpResponse<JsonNode> request = Unirest.post(url)
          .basicAuth("api", mailgunProperties.getApiKey())
          .field("from", internalMailingProperties.getSenderEmail())
          .field("to", recipient)
          .field("subject", fullSubject)
          .field("template", mailgunTemplateName)
          .field("h:X-Mailgun-Variables", variables)
          .asJson();
      try {
        if (((String) request.getBody().getObject().get("id")).isBlank()) {
          logger.warn("Mailgun returned the following JSON: {}", request.getBody());
          throw new MailingServiceException(
              "Status of the sent e-mail is unknown, Mailgun did not provide an ID for this operation");
        }
      } catch (JSONException ex) {
        throw new MailingServiceException(
            "Status of the sent e-mail is unknown, Mailgun threw an exception while validating the response", ex);
      }
    }
  }

  @Override
  public void notifyOrderStatusToOwners(SellPojo sell)
      throws MailingServiceException {
    String url = "https://api.mailgun.net/v3/" + mailgunProperties.getDomain() + "/messages";
    String mapsKey = OWNERS_MAPS_KEY_PREFIX + sell.getStatus();
    if (orderStatus2MailSubjectMap.containsKey(mapsKey)) {
      String messageSubject = orderStatus2MailSubjectMap.get(mapsKey);
      String mailgunTemplateName = orderStatus2MailgunTemplatesMap.get(mapsKey);
      String fullSubject = messageSubject + " [#" + sell.getBuyOrder() + "]";
      String variables = this.makeMailgunVariablesFrom(sell);

      HttpResponse<JsonNode> request = Unirest.post(url)
          .basicAuth("api", mailgunProperties.getApiKey())
          .field("from", internalMailingProperties.getSenderEmail())
          .field("to", internalMailingProperties.getOwnerEmail())
          .field("subject", fullSubject)
          .field("template", mailgunTemplateName)
          .field("h:X-Mailgun-Variables", variables)
          .asJson();
      try {
        if (((String) request.getBody().getObject().get("id")).isBlank()) {
          logger.warn("Mailgun returned the following JSON: {}", request.getBody());
          throw new MailingServiceException(
              "Status of the sent e-mail is unknown, Mailgun did not provide an ID for this operation");
        }
      } catch (JSONException ex) {
        throw new MailingServiceException(
            "Status of the sent e-mail is unknown, Mailgun threw an exception while validating the response", ex);
      }
    }
  }

  private Map<String, String> makeTemplatesMap() {
    return Map.of(
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_PAID_UNCONFIRMED, mailgunProperties.getCustomerOrderPaymentTemplate(),
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_PAID_CONFIRMED, mailgunProperties.getCustomerOrderConfirmationTemplate(),
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_REJECTED, mailgunProperties.getCustomerOrderRejectionTemplate(),
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_COMPLETED, mailgunProperties.getCustomerOrderCompletionTemplate(),
        OWNERS_MAPS_KEY_PREFIX + Constants.SELL_STATUS_PAID_CONFIRMED, mailgunProperties.getOwnerOrderConfirmationTemplate(),
        OWNERS_MAPS_KEY_PREFIX + Constants.SELL_STATUS_REJECTED, mailgunProperties.getOwnerOrderRejectionTemplate(),
        OWNERS_MAPS_KEY_PREFIX + Constants.SELL_STATUS_COMPLETED, mailgunProperties.getOwnerOrderCompletionTemplate()
    );
  }

  private Map<String, String> makeSubjectsMap() {
    return Map.of(
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_PAID_UNCONFIRMED, internalMailingProperties.getCustomerOrderPaymentSubject(),
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_PAID_CONFIRMED, internalMailingProperties.getCustomerOrderConfirmationSubject(),
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_REJECTED, internalMailingProperties.getCustomerOrderRejectionSubject(),
        CUSTOMER_MAPS_KEY_PREFIX + Constants.SELL_STATUS_COMPLETED, internalMailingProperties.getCustomerOrderCompletionSubject(),
        OWNERS_MAPS_KEY_PREFIX + Constants.SELL_STATUS_PAID_CONFIRMED, internalMailingProperties.getOwnerOrderConfirmationSubject(),
        OWNERS_MAPS_KEY_PREFIX + Constants.SELL_STATUS_REJECTED, internalMailingProperties.getOwnerOrderRejectionSubject(),
        OWNERS_MAPS_KEY_PREFIX + Constants.SELL_STATUS_COMPLETED, internalMailingProperties.getOwnerOrderCompletionSubject()
    );
  }

  private ObjectMapper mailObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper
        .configOverride(Instant.class)
        .setFormat(JsonFormat.Value
                       .forPattern(internalMailingProperties.getDateFormat())
                       .withTimeZone(TimeZone.getTimeZone(internalMailingProperties.getDateTimezone())));
    return objectMapper;
  }

  private String makeMailgunVariablesFrom(SellPojo sell) {
    String variables;
    try {
      ReceiptPojo receipt = this.turnIntoReceipt(sell);
      String transactionJson = mailObjectMapper.writeValueAsString(receipt);
      String customerJson = mailObjectMapper.writeValueAsString(sell.getCustomer());
      variables = "{\"transaction\": " + transactionJson +
          ", \"customer\": " + customerJson + "}";
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Could not stringify transaction object", e);
    }
    return variables;
  }

  private ReceiptPojo turnIntoReceipt(SellPojo sell) {
    ReceiptPojo receipt = conversionService.convert(sell, ReceiptPojo.class);
    if (receipt != null && sell.getDetails() != null) {
      List<ReceiptDetailPojo> receiptDetails = new ArrayList<>();
      for (SellDetailPojo detail : sell.getDetails()) {
        ReceiptDetailPojo convert = conversionService.convert(detail, ReceiptDetailPojo.class);
        receiptDetails.add(convert);
      }
      receipt.setDetails(receiptDetails);
    }
    return receipt;
  }
}
