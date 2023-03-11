

package com.huststore.presentation;

import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.exceptions.UserNotFoundException;

import javax.persistence.EntityNotFoundException;

public interface IProfileService {

  PersonPojo getProfileFromUserName(String userName) throws EntityNotFoundException;

  void updateProfileForUserWithName(String userName, PersonPojo profile) throws BadInputException, UserNotFoundException;
}
