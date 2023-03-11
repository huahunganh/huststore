

package com.huststore.presentation.controllers;

import com.huststore.dto.ReceiptPojo;
import com.huststore.presentation.IReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/public/receipt")
public class PublicReceiptController {
  private final Logger LOG = LoggerFactory.getLogger(PublicReceiptController.class);

  private final IReceiptService receiptService;

  @Autowired
  public PublicReceiptController(IReceiptService receiptService) {
    this.receiptService = receiptService;
  }

  @GetMapping({"/{token}", "/{token}/"})
  public ReceiptPojo fetchReceiptById(@PathVariable("token") String token)
      throws EntityNotFoundException {
    if (token == null) {
      throw new RuntimeException("An incorrect receipt token was provided");
    }
    return this.receiptService.fetchReceiptByTransactionToken(token);
  }
}
