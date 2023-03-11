

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Salesperson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISalespeopleJpaRepository
    extends IJpaRepository<Salesperson> {

  @Query(value = "SELECT s FROM Salesperson s JOIN FETCH s.person p WHERE p.idNumber = :idNumber")
  Optional<Salesperson> findByPersonIdNumber(@Param("idNumber") String idNumber);

}
