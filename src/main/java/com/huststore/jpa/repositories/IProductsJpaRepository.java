

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Product;
import com.huststore.jpa.entities.ProductCategory;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface IProductsJpaRepository
    extends IJpaRepository<Product> {

  @Query(value = "SELECT p FROM Product p JOIN FETCH p.productCategory", countQuery = "SELECT p FROM Product p")
  Page<Product> deepReadAll(Pageable pageable);

  @Query(value = "SELECT p FROM Product p JOIN FETCH p.productCategory", countQuery = "SELECT p FROM Product p")
  Page<Product> deepReadAll(Predicate filters, Pageable pageable);

  Optional<Product> findByBarcode(String barcode);

  @Modifying
  @Transactional
  @Query("UPDATE Product p SET p.productCategory.id = :categoryId WHERE p.id = :id")
  void setProductCategoryById(@Param("id") Long productId, @Param("categoryId") Long categoryId);

  @Modifying
  @Transactional
  @Query("UPDATE Product p SET p.productCategory = null WHERE p.productCategory IN (:categories)")
  void orphanizeByCategories(@Param("categories") Collection<ProductCategory> categories);
}
