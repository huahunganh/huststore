

package com.huststore.jpa.exceptions;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException
    extends EntityNotFoundException {

  public UserNotFoundException() { }

  public UserNotFoundException(String message) {
    super(message);
  }
}
