

package com.huststore.jpa.services.crud;

import com.huststore.config.SecurityProperties;
import com.huststore.dto.UserPojo;
import com.huststore.exceptions.AccountProtectionViolationException;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.repositories.IUsersJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IUsersConverterJpaService;
import com.huststore.jpa.services.datatransport.IUsersDataTransportJpaService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Transactional
@Service
public class UsersJpaCrudServiceImpl
  extends GenericCrudJpaService<UserPojo, User> implements IUsersCrudService {

  private final IUsersJpaRepository userRepository;
  private final SecurityProperties securityProperties;

  @Autowired
  public UsersJpaCrudServiceImpl(IUsersJpaRepository repository,
                                 IUsersConverterJpaService converter,
                                 IUsersDataTransportJpaService dataTransportService,
                                 SecurityProperties securityProperties) {
    super(repository,
          converter,
          dataTransportService);
    this.userRepository = repository;
    this.securityProperties = securityProperties;
  }

  @Override
  public Optional<User> getExisting(UserPojo input) throws BadInputException {
    String name = input.getName();
    if (name == null || name.isBlank()) {
      throw new BadInputException("Invalid user name");
    } else {
      return userRepository.findByName(name);
    }
  }

  @Override
  public void delete(Predicate filters) throws EntityNotFoundException {
    if (securityProperties.isAccountProtectionEnabled()) {
      Optional<User> optionalUser = userRepository.findOne(filters);
      if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        if (user.getId() == securityProperties.getProtectedAccountId()) {
          throw new AccountProtectionViolationException("Protected account cannot be deleted");
        }
      }
    }
    super.delete(filters);
  }

}
