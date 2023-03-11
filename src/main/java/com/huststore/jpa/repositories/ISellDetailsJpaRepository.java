

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.SellDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISellDetailsJpaRepository
  extends IJpaRepository<SellDetail> {

  @Query(value = "SELECT d FROM SellDetail d WHERE d.sell.id = :sellId")
  List<SellDetail> findBySellId(@Param("sellId") Long sellId);
}
