

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductPojo;
import com.huststore.jpa.entities.ProductListItem;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IProductListItemsConverterJpaService
  extends ITwoWayConverterJpaService<ProductPojo, ProductListItem> {
}
