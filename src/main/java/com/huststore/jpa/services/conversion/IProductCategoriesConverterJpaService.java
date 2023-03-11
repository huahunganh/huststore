

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductCategoryPojo;
import com.huststore.jpa.entities.ProductCategory;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IProductCategoriesConverterJpaService
  extends ITwoWayConverterJpaService<ProductCategoryPojo, ProductCategory> {
}
