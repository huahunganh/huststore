

package com.huststore.jpa.services.conversion;

import com.huststore.dto.UserRolePojo;
import com.huststore.jpa.entities.UserRole;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IUserRolesConverterJpaService
  extends ITwoWayConverterJpaService<UserRolePojo, UserRole> {
}
