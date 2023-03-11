

package com.huststore.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huststore.config.SecurityProperties;
import com.huststore.dto.CustomerPojo;
import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.services.crud.ICustomersCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.crypto.SecretKey;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtGuestAuthenticationFilter
  extends GenericJwtAuthenticationFilter {

  private final Logger myLogger = LoggerFactory.getLogger(JwtGuestAuthenticationFilter.class);
  private final AuthenticationManager authenticationManager;
  private final ICustomersCrudService customersService;

  public JwtGuestAuthenticationFilter(SecurityProperties jwtProperties,
                                      SecretKey secretKey,
                                      AuthenticationManager authenticationManager,
                                      ICustomersCrudService customersService) {
    super(jwtProperties, secretKey);
    this.authenticationManager = authenticationManager;
    this.customersService = customersService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException {
    if (!HttpMethod.POST.matches(request.getMethod())) {
      return null;
    } else {
      try {
        PersonPojo guestCustomerData = new ObjectMapper().readValue(request.getInputStream(), PersonPojo.class);
        this.saveCustomerData(guestCustomerData);
        Authentication authentication = new UsernamePasswordAuthenticationToken("guest", "guest");
        return authenticationManager.authenticate(authentication);
      } catch (IOException e) {
        throw new BadCredentialsException("Invalid request body for guest session");
      } catch (BadInputException e) {
        throw new BadCredentialsException("Insufficient or invalid profile data for guest");
      }
    }
  }

  private void saveCustomerData(PersonPojo guestData) throws BadInputException {
    try {
      CustomerPojo targetCustomer = CustomerPojo.builder().person(guestData).build();
      customersService.create(targetCustomer);
    } catch (EntityExistsException e) {
      myLogger.info("Guest with idNumber={} is already registered in the database", guestData.getIdNumber());
    }
  }
}
