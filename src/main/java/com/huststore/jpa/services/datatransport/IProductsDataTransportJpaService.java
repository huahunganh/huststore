

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ProductPojo;
import com.huststore.jpa.entities.Product;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IProductsDataTransportJpaService
  extends IDataTransportJpaService<ProductPojo, Product> {
}
