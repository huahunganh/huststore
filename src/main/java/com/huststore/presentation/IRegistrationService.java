

package com.huststore.presentation;

import com.huststore.dto.RegistrationPojo;
import com.huststore.exceptions.BadInputException;

import javax.persistence.EntityExistsException;

public interface IRegistrationService {
  void register(RegistrationPojo registration) throws BadInputException, EntityExistsException;
}
