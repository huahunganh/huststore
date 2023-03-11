

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRolesJpaRepository
    extends IJpaRepository<UserRole> {

  @Query
  Optional<UserRole> findByName(String name);

}
