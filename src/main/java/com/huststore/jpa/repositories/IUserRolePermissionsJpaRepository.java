

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.UserRolePermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRolePermissionsJpaRepository
  extends IJpaRepository<UserRolePermission> {

  @Query("SELECT urp FROM UserRolePermission urp JOIN FETCH urp.permission WHERE urp.userRole.id = :userRoleId")
  Iterable<UserRolePermission> deepFindPermissionsByUserRoleId(@Param("userRoleId") Long userRoleId);
}
