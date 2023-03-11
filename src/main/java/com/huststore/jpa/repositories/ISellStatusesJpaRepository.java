

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.SellStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISellStatusesJpaRepository
  extends IJpaRepository<SellStatus> {

  Optional<SellStatus> findByName(String name);
}
