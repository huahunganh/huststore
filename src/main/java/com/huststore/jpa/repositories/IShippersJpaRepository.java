

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Shipper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IShippersJpaRepository
  extends IJpaRepository<Shipper> {

  Optional<Shipper> findByName(String name);
}
