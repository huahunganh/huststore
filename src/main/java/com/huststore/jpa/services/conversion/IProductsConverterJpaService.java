

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductPojo;
import com.huststore.jpa.entities.Product;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IProductsConverterJpaService
  extends ITwoWayConverterJpaService<ProductPojo, Product> {
}
