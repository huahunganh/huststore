

package com.huststore.integration.exceptions;

public class MailingServiceException
  extends Exception {

  public MailingServiceException() {
  }

  public MailingServiceException(String string) {
    super(string);
  }

  public MailingServiceException(String string, Throwable throwable) {
    super(string, throwable);
  }

  public MailingServiceException(Throwable throwable) {
    super(throwable);
  }

}
