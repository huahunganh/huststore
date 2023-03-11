

package com.huststore.exceptions;

public class AccountProtectionViolationException
  extends RuntimeException {

  public AccountProtectionViolationException(String message) {
    super(message);
  }

}
