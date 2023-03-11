

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ProductListPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductListDataTransportJpaServiceImpl
  implements IProductListsDataTransportJpaService {

  @Autowired
  public ProductListDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public ProductList applyChangesToExistingEntity(ProductListPojo source, ProductList existing) throws BadInputException {
    ProductList target = new ProductList(existing);

    if (source.getName() != null && !source.getName().isEmpty() && !source.getName().equals(target.getName())) {
      target.setName(source.getName());
    }

    if (source.getCode() != null && !source.getCode().isEmpty() && !source.getCode().equals(target.getCode())) {
      target.setCode(source.getCode());
    }

    return target;
  }
}
