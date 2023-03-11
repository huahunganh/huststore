

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersJpaRepository extends IJpaRepository<User> {

  Optional<User> findByName(String name);

  @Query("SELECT u FROM User u JOIN FETCH u.userRole WHERE u.name = :name")
  Optional<User> findByNameWithRole(@Param("name") String name);

  @Query("SELECT u FROM User u JOIN FETCH u.person WHERE u.name = :name")
  Optional<User> findByNameWithProfile(@Param("name") String name);

  @Query("SELECT u FROM User u JOIN FETCH u.person WHERE u.id = :id")
  Optional<User> findByIdWithProfile(@Param("id") Long id);

  @Query("SELECT u FROM User u JOIN FETCH u.person p WHERE p.idNumber = :idNumber")
  Optional<User> findByPersonIdNumber(@Param("idNumber") String idNumber);
}
