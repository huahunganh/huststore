

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductListItemsConverterJpaServiceImpl
  implements IProductListItemsConverterJpaService {
  private final IProductsConverterJpaService productsConverterService;

  @Autowired
  public ProductListItemsConverterJpaServiceImpl(IProductsConverterJpaService productsConverterService) {
    this.productsConverterService = productsConverterService;
  }

  @Override
  public ProductPojo convertToPojo(ProductListItem source) {
    return productsConverterService.convertToPojo(source.getProduct());
  }

  @Override
  public ProductListItem convertToNewEntity(ProductPojo source) throws BadInputException {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public ProductListItem applyChangesToExistingEntity(ProductPojo source, ProductListItem target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
