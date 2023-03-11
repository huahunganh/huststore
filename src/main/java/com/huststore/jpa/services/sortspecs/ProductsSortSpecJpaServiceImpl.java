

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.Product;
import com.huststore.jpa.entities.QProduct;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductsSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<Product> {

  @Override
  public QProduct getBasePath() {
    return QProduct.product;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name",     getBasePath().name.asc(),
            "barcode",  getBasePath().barcode.asc(),
            "price",    getBasePath().price.asc(),
            "category", getBasePath().productCategory.name.asc()
    );
  }
}
