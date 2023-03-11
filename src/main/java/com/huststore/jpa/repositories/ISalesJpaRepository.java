

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.entities.SellStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISalesJpaRepository
  extends IJpaRepository<Sell> {

  Optional<Sell> findByTransactionToken(String token);

  @Query(value = "SELECT s FROM Sell s "
      + "JOIN FETCH s.details "
      + "WHERE s.id = :id")
  Optional<Sell> findByIdWithDetails(@Param("id") Long id);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Sell s "
      + "SET s.status = :status "
      + "WHERE s.id = :id")
  int setStatus(@Param("id") Long id, @Param("status") SellStatus status);

  @Modifying
  @Query("UPDATE Sell s "
      + "SET s.transactionToken = :token "
      + "WHERE s.id = :id")
  int setTransactionToken(@Param("id") Long id, @Param("token") String token);
}
