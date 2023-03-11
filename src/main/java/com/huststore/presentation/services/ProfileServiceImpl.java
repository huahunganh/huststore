

package com.huststore.presentation.services;

import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.exceptions.PersonNotFoundException;
import com.huststore.jpa.exceptions.UserNotFoundException;
import com.huststore.jpa.repositories.IPeopleJpaRepository;
import com.huststore.jpa.repositories.IUsersJpaRepository;
import com.huststore.jpa.services.conversion.IPeopleConverterJpaService;
import com.huststore.jpa.services.crud.IPeopleCrudService;
import com.huststore.jpa.services.datatransport.IPeopleDataTransportJpaService;
import com.huststore.presentation.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProfileServiceImpl
    implements IProfileService {

  private final IUsersJpaRepository usersRepository;
  private final IPeopleCrudService peopleService;
  private final IPeopleConverterJpaService peopleConverter;
  private final IPeopleDataTransportJpaService peopleDataTransportService;
  private final IPeopleJpaRepository peopleRepository;

  @Autowired
  public ProfileServiceImpl(IUsersJpaRepository usersRepository,
                            IPeopleCrudService peopleService,
                            IPeopleConverterJpaService peopleConverter,
                            IPeopleDataTransportJpaService peopleDataTransportService,
                            IPeopleJpaRepository peopleRepository) {
    this.usersRepository = usersRepository;
    this.peopleService = peopleService;
    this.peopleConverter = peopleConverter;
    this.peopleDataTransportService = peopleDataTransportService;
    this.peopleRepository = peopleRepository;
  }

  @Override
  public PersonPojo getProfileFromUserName(String userName)
      throws EntityNotFoundException {
    User user = this.getUserFromName(userName);
    Person person = user.getPerson();
    if (person == null) {
      throw new PersonNotFoundException("The account does not have an associated profile");
    } else {
      return peopleConverter.convertToPojo(person);
    }
  }

  @Transactional
  @Override
  public void updateProfileForUserWithName(String userName, PersonPojo profile)
      throws BadInputException, UserNotFoundException {
    User targetUser = this.getUserFromName(userName);
    Person target = targetUser.getPerson();
    if (target == null) {
      Optional<Person> existingProfile = peopleService.getExisting(profile);
      if (existingProfile.isPresent()) {
        Person person = existingProfile.get();
        Optional<User> userByIdNumber = usersRepository.findByPersonIdNumber(person.getIdNumber());
        if (userByIdNumber.isPresent()) {
          throw new BadInputException("Person profile is associated to another account. Cannot use it.");
        } else {
          targetUser.setPerson(person);
          usersRepository.saveAndFlush(targetUser);
        }
      } else {
        Person newProfile = peopleConverter.convertToNewEntity(profile);
        newProfile = peopleRepository.saveAndFlush(newProfile);
        targetUser.setPerson(newProfile);
        usersRepository.saveAndFlush(targetUser);
      }
    } else {
      target = peopleDataTransportService.applyChangesToExistingEntity(profile, target);
      peopleRepository.saveAndFlush(target);
    }
  }

  private User getUserFromName(String userName)
      throws UserNotFoundException {
    Optional<User> userByName = usersRepository.findByName(userName);
    if (userByName.isEmpty()) {
      throw new UserNotFoundException("There is no account with the specified username");
    } else {
      return userByName.get();
    }
  }
}
