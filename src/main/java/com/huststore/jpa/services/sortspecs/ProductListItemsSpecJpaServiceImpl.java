

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.ProductListItem;
import com.huststore.jpa.entities.QProductListItem;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductListItemsSpecJpaServiceImpl
  extends GenericSortSpecJpaService<ProductListItem> {

  @Override
  public QProductListItem getBasePath() {
    return QProductListItem.productListItem;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name",     getBasePath().product.name.asc(),
            "barcode",  getBasePath().product.barcode.asc()
    );
  }
}
