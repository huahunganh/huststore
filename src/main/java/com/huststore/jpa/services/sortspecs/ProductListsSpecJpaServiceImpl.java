

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.ProductList;
import com.huststore.jpa.entities.QProductList;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductListsSpecJpaServiceImpl
  extends GenericSortSpecJpaService<ProductList> {

  @Override
  public QProductList getBasePath() {
    return QProductList.productList;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name",       getBasePath().name.asc(),
            "code",       getBasePath().code.asc(),
            "totalCount", getBasePath().items.size().asc()
    );
  }
}
