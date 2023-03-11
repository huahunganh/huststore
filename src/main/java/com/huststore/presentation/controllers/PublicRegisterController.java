

package com.huststore.presentation.controllers;

import com.huststore.dto.RegistrationPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.presentation.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

@RestController
@RequestMapping("/public/register")
public class PublicRegisterController {

  private final IRegistrationService registrationService;

  @Autowired
  public PublicRegisterController(IRegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @PostMapping({"", "/"})
  public void register(@Valid @RequestBody RegistrationPojo userProfile)
      throws BadInputException, EntityExistsException {
    this.registrationService.register(userProfile);
  }
}
