

package com.huststore.presentation.controllers;

import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.presentation.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("/account/profile")
@PreAuthorize("isAuthenticated()")
public class AccountProfileController {

  private final IProfileService userProfileService;

  @Autowired
  public AccountProfileController(IProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  @GetMapping({"", "/"})
  public PersonPojo getProfile(Principal principal)
      throws EntityNotFoundException {
    String username = principal.getName();
    return userProfileService.getProfileFromUserName(username);
  }

  @PutMapping({"", "/"})
  public void updateProfile(Principal principal, @RequestBody PersonPojo newProfile)
      throws EntityNotFoundException, BadInputException {
    String username = principal.getName();
    userProfileService.updateProfileForUserWithName(principal.getName(), newProfile);
  }
}
