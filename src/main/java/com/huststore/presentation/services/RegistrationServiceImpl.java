

package com.huststore.presentation.services;

import com.huststore.dto.PersonPojo;
import com.huststore.dto.RegistrationPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.*;
import com.huststore.jpa.repositories.ICustomersJpaRepository;
import com.huststore.jpa.repositories.IPeopleJpaRepository;
import com.huststore.jpa.repositories.IUserRolesJpaRepository;
import com.huststore.jpa.repositories.IUsersJpaRepository;
import com.huststore.presentation.IRegistrationService;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class RegistrationServiceImpl
  implements IRegistrationService {

  private final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
  private final IPeopleJpaRepository peopleRepository;
  private final IUsersJpaRepository usersRepository;
  private final IUserRolesJpaRepository rolesRepository;
  private final ICustomersJpaRepository customersRepository;
  private final PasswordEncoder passwordEncoder;
  private final ConversionService conversionService;

  @Autowired
  public RegistrationServiceImpl(IPeopleJpaRepository peopleRepository,
    IUsersJpaRepository usersRepository, IUserRolesJpaRepository rolesRepository,
    ICustomersJpaRepository customersRepository, PasswordEncoder passwordEncoder,
    ConversionService conversionService) {
    this.peopleRepository = peopleRepository;
    this.usersRepository = usersRepository;
    this.rolesRepository = rolesRepository;
    this.customersRepository = customersRepository;
    this.passwordEncoder = passwordEncoder;
    this.conversionService = conversionService;
  }

  @Override
  public void register(RegistrationPojo registration)
      throws BadInputException, EntityExistsException {
    String username = registration.getName();
    Predicate userWithSameName = QUser.user.name.eq(username);
    if (usersRepository.exists(userWithSameName)) {
      throw new EntityExistsException("That username is taken.");
    }

    PersonPojo sourcePerson = registration.getProfile();
    Person newPerson = conversionService.convert(sourcePerson, Person.class);
    if (newPerson == null) {
      throw new BadInputException("Input profile has insufficient or invalid data.");
    }

    Predicate sameProfileData = QPerson.person.idNumber.eq(newPerson.getIdNumber());
    if (peopleRepository.exists(sameProfileData)) {
      throw new EntityExistsException("That ID number is already registered and associated to an account.");
    } else {
      newPerson = peopleRepository.saveAndFlush(newPerson);
    }

    User newUser = this.convertToUser(registration);
    newUser.setPerson(newPerson);
    usersRepository.saveAndFlush(newUser);
    logger.info("New user created with name '{}' and idNumber '{}'", newUser.getName(), newPerson.getIdNumber());

    Customer newCustomer = new Customer();
    newCustomer.setPerson(newPerson);
    customersRepository.saveAndFlush(newCustomer);
  }

  protected User convertToUser(RegistrationPojo registration) {
    String password = passwordEncoder.encode(registration.getPassword());
    User target = new User();
    target.setName(registration.getName());
    target.setPassword(password);

    Optional<UserRole> customerRole = rolesRepository.findByName("Customer");
    if (customerRole.isEmpty()) {
      throw new IllegalStateException("No user role matches 'Customer', database might be compromised");
    } else {
      target.setUserRole(customerRole.get());
    }
    return target;
  }

}
