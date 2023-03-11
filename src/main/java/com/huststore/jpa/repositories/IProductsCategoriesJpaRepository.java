

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductsCategoriesJpaRepository
  extends IJpaRepository<ProductCategory> {

  Optional<ProductCategory> findByCode(String code);

  List<ProductCategory> findByName(String code);

  List<ProductCategory> findByParent(ProductCategory parent);

  @Query("SELECT r.id FROM ProductCategory r WHERE r.parent.id = :parentId")
  List<Long> findIdsByParentId(@Param("parentId") Long parentId);
}
