

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ProductCategoryPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductCategoriesDataTransportJpaServiceImpl
  implements IProductCategoriesDataTransportJpaService {

  @Autowired
  public ProductCategoriesDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public ProductCategory applyChangesToExistingEntity(ProductCategoryPojo source, ProductCategory existing)
          throws BadInputException {
    ProductCategory target = new ProductCategory(existing);

    String name = source.getName();
    if (name != null && !name.isBlank() && !target.getName().equals(name)) {
      target.setName(name);
    }

    return target;
  }
}
