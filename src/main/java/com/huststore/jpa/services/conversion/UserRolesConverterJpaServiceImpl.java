

package com.huststore.jpa.services.conversion;

import com.huststore.dto.UserRolePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRolesConverterJpaServiceImpl
  implements IUserRolesConverterJpaService {

  @Autowired
  public UserRolesConverterJpaServiceImpl() {
  }

  @Override
  public UserRolePojo convertToPojo(UserRole source) {
    return UserRolePojo.builder()
      .id(source.getId())
      .name(source.getName())
      .build();
  }

  @Override
  public UserRole convertToNewEntity(UserRolePojo source) {
    UserRole convert = new UserRole();
    convert.setName(source.getName());
    return convert;
  }

  @Override
  public UserRole applyChangesToExistingEntity(UserRolePojo source, UserRole target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
