

package com.huststore.presentation;

import com.huststore.dto.ReceiptPojo;

import javax.persistence.EntityNotFoundException;

public interface IReceiptService {
  ReceiptPojo fetchReceiptByTransactionToken(String token) throws EntityNotFoundException;
}
