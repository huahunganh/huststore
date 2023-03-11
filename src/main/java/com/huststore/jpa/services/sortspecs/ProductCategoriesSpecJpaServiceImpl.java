

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.ProductCategory;
import com.huststore.jpa.entities.QProductCategory;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductCategoriesSpecJpaServiceImpl
  extends GenericSortSpecJpaService<ProductCategory> {

  @Override
  public QProductCategory getBasePath() {
    return QProductCategory.productCategory;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name", getBasePath().name.asc(),
            "code", getBasePath().code.asc()
    );
  }
}
