

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.UserRolePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserRolesDataTransportJpaServiceImpl
  implements IUserRolesDataTransportJpaService {

  public UserRolesDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public UserRole applyChangesToExistingEntity(UserRolePojo source, UserRole existing) throws BadInputException {
    UserRole target = new UserRole(existing);

    String name = source.getName();
    if (name != null && !name.isBlank() && target.getName().equals(name)) {
      target.setName(name);
    }

    return target;
  }
}
