

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ProductListPojo;
import com.huststore.jpa.entities.ProductList;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IProductListsDataTransportJpaService
  extends IDataTransportJpaService<ProductListPojo, ProductList> {
}
