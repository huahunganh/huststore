

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.ProductListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IProductListItemsJpaRepository
  extends IJpaRepository<ProductListItem> {

  @Modifying
  @Transactional
  @Query("DELETE FROM ProductListItem pi WHERE pi.list.id = :id")
  void deleteByListId(@Param("id") Long id);

  @Modifying
  @Transactional
  @Query("DELETE FROM ProductListItem pi WHERE pi.product.id = :id")
  void deleteByProductId(@Param("id") Long id);
}
