

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IParamsJpaRepository
    extends IJpaRepository<Param> {

  @Query("SELECT p FROM Param p WHERE p.category = :category")
  Iterable<Param> findParamsByCategory(
      @org.springframework.data.repository.query.Param("category") String category);
}
