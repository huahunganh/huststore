

package com.huststore.security.services;

import com.huststore.config.SecurityProperties;
import com.huststore.dto.UserDetailsPojo;
import com.huststore.jpa.entities.Permission;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.entities.UserRolePermission;
import com.huststore.jpa.repositories.IUserRolePermissionsJpaRepository;
import com.huststore.jpa.repositories.IUsersJpaRepository;
import com.huststore.security.IUserPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service required by the DaoAuthenticationProvider bean.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final IUsersJpaRepository usersRepository;
  private final IUserRolePermissionsJpaRepository rolePermissionsRepository;
  private final IUserPermissionsService userPermissionsService;
  private final SecurityProperties securityProperties;

  @Autowired
  public UserDetailsServiceImpl(IUsersJpaRepository usersRepository,
                                IUserRolePermissionsJpaRepository rolePermissionsRepository,
                                IUserPermissionsService userPermissionsService,
                                SecurityProperties securityProperties) {
    this.usersRepository = usersRepository;
    this.rolePermissionsRepository = rolePermissionsRepository;
    this.userPermissionsService = userPermissionsService;
    this.securityProperties = securityProperties;
  }

  private List<SimpleGrantedAuthority> convertPermissionList(Iterable<Permission> sourceList) {
    List<SimpleGrantedAuthority> targetList = new ArrayList<>();
    for (Permission source : sourceList) {
      SimpleGrantedAuthority target = new SimpleGrantedAuthority(source.getCode());
      targetList.add(target);
    }
    return targetList;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (securityProperties.isGuestUserEnabled() &&
        username.equals(securityProperties.getGuestUserName())) {
      // TODO parameterize role ID
      Iterable<UserRolePermission> rawPermissions = rolePermissionsRepository.deepFindPermissionsByUserRoleId(4L);
      List<Permission> permissions = new ArrayList<>();
      for (UserRolePermission rp : rawPermissions) {
        permissions.add(rp.getPermission());
      }
      List<SimpleGrantedAuthority> authorities = convertPermissionList(permissions);
      return new UserDetailsPojo(authorities, username, "",
                                 true, true, true, true);
    }
    Optional<User> foundUser = usersRepository.findByNameWithRole(username);
    if (foundUser.isPresent()) {
      User user = foundUser.get();
      Iterable<Permission> permissions = userPermissionsService.loadPermissionsForUser(user);
      List<SimpleGrantedAuthority> authorities = convertPermissionList(permissions);
      return new UserDetailsPojo(authorities, username, user.getPassword(),
          true, true, true, true);
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

}
