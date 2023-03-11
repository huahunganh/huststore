

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ProductListPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductList;
import com.huststore.jpa.entities.QProductListItem;
import com.huststore.jpa.repositories.IProductListItemsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductListConverterJpaServiceImpl
  implements IProductListsConverterJpaService {

  private final IProductListItemsJpaRepository productListItemRepository;

  @Autowired
  public ProductListConverterJpaServiceImpl(IProductListItemsJpaRepository productListItemRepository) {
    this.productListItemRepository = productListItemRepository;
  }

  @Override
  public ProductListPojo convertToPojo(ProductList source) {
    Long sourceListId = source.getId();
    long itemCount = productListItemRepository.count(QProductListItem.productListItem.list.id.eq(sourceListId));
    return ProductListPojo.builder()
      .id(sourceListId)
      .name(source.getName())
      .code(source.getCode())
      .totalCount(itemCount)
      .build();
  }

  @Override
  public ProductList convertToNewEntity(ProductListPojo source) throws BadInputException {
    return new ProductList(source.getName(), source.getCode());
  }

  @Override
  public ProductList applyChangesToExistingEntity(ProductListPojo source, ProductList target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
