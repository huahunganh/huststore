

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ProductCategoryPojo;
import com.huststore.jpa.entities.ProductCategory;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IProductCategoriesDataTransportJpaService
  extends IDataTransportJpaService<ProductCategoryPojo, ProductCategory> {
}
