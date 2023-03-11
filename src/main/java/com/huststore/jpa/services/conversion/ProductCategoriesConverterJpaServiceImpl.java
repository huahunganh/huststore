

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductCategoryPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductCategoriesConverterJpaServiceImpl
  implements IProductCategoriesConverterJpaService {

  @Autowired
  public ProductCategoriesConverterJpaServiceImpl() {
  }

  @Override
  public ProductCategoryPojo convertToPojo(ProductCategory source) {
    return ProductCategoryPojo.builder()
      .id(source.getId())
      .code(source.getCode())
      .name(source.getName())
      .build();
  }

  @Override
  public ProductCategory convertToNewEntity(ProductCategoryPojo source) {
    ProductCategory target = new ProductCategory();
    target.setCode(source.getCode());
    target.setName(source.getName());
    return target;
  }

  @Override
  public ProductCategory applyChangesToExistingEntity(ProductCategoryPojo source, ProductCategory target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
