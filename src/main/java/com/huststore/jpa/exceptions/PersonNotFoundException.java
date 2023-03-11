

package com.huststore.jpa.exceptions;

import javax.persistence.EntityNotFoundException;

public class PersonNotFoundException
    extends EntityNotFoundException {

  public PersonNotFoundException() { }

  public PersonNotFoundException(String message) {
    super(message);
  }
}
