

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductListPojo;
import com.huststore.jpa.entities.ProductList;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IProductListsConverterJpaService
  extends ITwoWayConverterJpaService<ProductListPojo, ProductList> {
}
