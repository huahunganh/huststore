

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPeopleJpaRepository
  extends IJpaRepository<Person> {

  Optional<Person> findByIdNumber(String idNumber);

}
