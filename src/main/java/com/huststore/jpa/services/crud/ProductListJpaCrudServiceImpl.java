

package com.huststore.jpa.services.crud;

import com.huststore.dto.ProductListPojo;
import com.huststore.jpa.entities.ProductList;
import com.huststore.jpa.repositories.IProductListItemsJpaRepository;
import com.huststore.jpa.repositories.IProductListsJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IProductListsConverterJpaService;
import com.huststore.jpa.services.datatransport.IProductListsDataTransportJpaService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Transactional
@Service
public class ProductListJpaCrudServiceImpl
  extends GenericCrudJpaService<ProductListPojo, ProductList>
  implements IProductListCrudService {

  private final IProductListsJpaRepository productListRepository;
  private final IProductListItemsJpaRepository productListItemRepository;

  @Autowired
  public ProductListJpaCrudServiceImpl(IProductListsJpaRepository productListRepository,
                                       IProductListItemsJpaRepository productListItemRepository,
                                       IProductListsConverterJpaService converterService,
                                       IProductListsDataTransportJpaService dataTransportService) {
    super(productListRepository,
           converterService,
           dataTransportService);
    this.productListRepository = productListRepository;
    this.productListItemRepository = productListItemRepository;
  }

  @Override
  public void delete(Predicate filters)
      throws EntityNotFoundException {
    long count = productListRepository.count(filters);
    if (count == 0) {
      throw new EntityNotFoundException(ITEM_NOT_FOUND);
    } else {
      for (ProductList list : productListRepository.findAll(filters)) {
        productListItemRepository.deleteByListId(list.getId());
      }

      productListRepository.deleteAll(productListRepository.findAll(filters));
    }
  }

  @Override
  public Optional<ProductList> getExisting(ProductListPojo input) {
    Long id = input.getId();
    String name = input.getName();
    if (id == null && name == null) {
      return Optional.empty();
    } else if (id != null) {
      return productListRepository.findById(id);
    } else {
      return productListRepository.findByName(name);
    }
  }


}
