

package com.huststore.presentation;

import com.huststore.dto.SellPojo;
import com.huststore.exceptions.BadInputException;

import javax.persistence.EntityNotFoundException;

/**
 * Declares methods to advance through steps of transaction.
 */
public interface ISalesProcessService {

  /**
   * Updates status of a sell to "started", meaning its bill must be paid.
   * @param sell The sell whose status will be updated
   * @throws BadInputException When the transaction is not in "pending" state, or when it doesn't have a token
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsStarted(SellPojo sell) throws BadInputException, EntityNotFoundException;

  /**
   * Updates status of a sell to "aborted", meaning nothing more can be done about it.
   * @param sell The sell whose status will be updated
   * @throws BadInputException When the transaction is not in "started" state
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsAborted(SellPojo sell) throws BadInputException, EntityNotFoundException;

  /**
   * Updates status of a sell to "failed", meaning nothing more can be done about it.
   * @param sell The sell whose status will be updated
   * @throws BadInputException When the transaction is not in "started" state
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsFailed(SellPojo sell) throws BadInputException, EntityNotFoundException;

  /**
   * Updates status of a sell to "paid/unconfirmed", meaning it can be "rejected" or "confirmed" afterwards.
   * This must be only possible right after the sell has been created.
   * @param sell The sell whose status will be updated
   * @throws BadInputException When the transaction is not in "started" state
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsPaid(SellPojo sell) throws BadInputException, EntityNotFoundException;

  /**
   * Updates status of a sell to "confirmed", meaning it must be eventually delivered, or fail to do so.
   * @param sell The sell whose status will be updated
   * @throws BadInputException When the transaction is not in "paid/unconfirmed" state
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsConfirmed(SellPojo sell) throws BadInputException, EntityNotFoundException;

  /**
   * Updates status of a sell to "rejected", meaning a refund must be issued to the customer.
   * This must be only possible after payment is completed, notified and saved in the application state.
   * @throws BadInputException When the transaction is not "paid/unconfirmed" state
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsRejected(SellPojo sell) throws BadInputException, EntityNotFoundException;

  /**
   * Updates status of a sell to "completed", meaning the whole process is finished and the customer is contempt.
   * @param sell The sell whose status will be updated
   * @throws BadInputException When the transaction is not in "confirmed" state
   * @throws EntityNotFoundException When the transaction is not found in the persistence context
   */
  SellPojo markAsCompleted(SellPojo sell) throws BadInputException, EntityNotFoundException;
}
