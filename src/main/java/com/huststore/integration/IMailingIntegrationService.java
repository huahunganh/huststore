

package com.huststore.integration;

import com.huststore.dto.SellPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.integration.exceptions.MailingServiceException;

/**
 * Point of entry for services to send mail to customers and owners alike
 */
public interface IMailingIntegrationService {
  /**
   * Generate and send an e-mail to the customer, regarding an update on their transaction' status.<br/>
   * Should support all transaction stages
   * @param sell The transaction metadata
   * @throws BadInputException When there is a problem with the order reference
   * @throws MailingServiceException When any error occurs while interacting with the
   *                                                                   mail server/service provider
   */
  void notifyOrderStatusToClient(SellPojo sell) throws BadInputException, MailingServiceException;

  /**
   * Generate and send an e-mail to store owners, regarding an update on a certain transaction' status.<br/>
   * It is not mandatory to support all transaction stages; owners may only need to be aware of some events.
   * @param sell The transaction metadata
   * @throws MailingServiceException When any error occurs while interacting with the
   *                                                                   mail server/service provider
   */
  void notifyOrderStatusToOwners(SellPojo sell) throws MailingServiceException;
}
