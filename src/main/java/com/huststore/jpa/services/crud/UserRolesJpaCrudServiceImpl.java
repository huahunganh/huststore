

package com.huststore.jpa.services.crud;

import com.huststore.dto.UserRolePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.UserRole;
import com.huststore.jpa.repositories.IUserRolesJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IUserRolesConverterJpaService;
import com.huststore.jpa.services.datatransport.IUserRolesDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserRolesJpaCrudServiceImpl
  extends GenericCrudJpaService<UserRolePojo, UserRole>
  implements IUserRolesCrudService {

  private final IUserRolesJpaRepository userRolesRepository;

  @Autowired
  public UserRolesJpaCrudServiceImpl(IUserRolesJpaRepository repository,
                                     IUserRolesConverterJpaService converter,
                                     IUserRolesDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.userRolesRepository = repository;
  }

  @Override
  public Optional<UserRole> getExisting(UserRolePojo input) throws BadInputException {
    String name = input.getName();
    if (name == null || name.isBlank()) {
      throw new BadInputException("Invalid user role name");
    } else {
      return userRolesRepository.findByName(name);
    }
  }
}
