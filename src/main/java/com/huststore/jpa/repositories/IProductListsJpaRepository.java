

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.ProductList;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductListsJpaRepository
  extends IJpaRepository<ProductList> {

  Optional<ProductList> findByName(String name);
}
