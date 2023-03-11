

package com.huststore.security;

import com.huststore.jpa.entities.Permission;
import com.huststore.jpa.entities.User;

import java.util.Set;

public interface IUserPermissionsService {
  Set<Permission> loadPermissionsForUser(User source);
}
