

package com.huststore.security.services;

import com.huststore.jpa.entities.Permission;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.entities.UserRole;
import com.huststore.jpa.entities.UserRolePermission;
import com.huststore.jpa.repositories.IUserRolePermissionsJpaRepository;
import com.huststore.security.IUserPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service required by the DaoAuthenticationProvider bean.
 */
@Service
public class UserPermissionsServiceImpl
    implements IUserPermissionsService {

  private final IUserRolePermissionsJpaRepository userRolePermissionsRepository;

  @Autowired
  public UserPermissionsServiceImpl(IUserRolePermissionsJpaRepository userRolePermissionsRepository) {
    this.userRolePermissionsRepository = userRolePermissionsRepository;
  }

  @Override
  public Set<Permission> loadPermissionsForUser(User source) {
    UserRole sourceUserRole = source.getUserRole();
    Long userRoleId = sourceUserRole.getId();
    Iterable<UserRolePermission> userRolePermissions = userRolePermissionsRepository
        .deepFindPermissionsByUserRoleId(userRoleId);

    Set<Permission> targetList = new HashSet<>();
    for (UserRolePermission rolePermission : userRolePermissions) {
      Permission p = rolePermission.getPermission();
      targetList.add(p);
    }

    return targetList;
  }

}
