

package com.huststore.jpa.services.crud;

import com.huststore.dto.ProductCategoryPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductCategory;
import com.huststore.jpa.repositories.IProductsCategoriesJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IProductCategoriesConverterJpaService;
import com.huststore.jpa.services.datatransport.IProductCategoriesDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ProductCategoriesJpaCrudServiceImpl
  extends GenericCrudJpaService<ProductCategoryPojo, ProductCategory> implements IProductCategoriesCrudService {

  private final IProductsCategoriesJpaRepository categoriesRepository;

  @Autowired
  public ProductCategoriesJpaCrudServiceImpl(IProductsCategoriesJpaRepository repository,
                                             IProductCategoriesConverterJpaService converter,
                                             IProductCategoriesDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.categoriesRepository = repository;
  }

  @Override
  public Optional<ProductCategory> getExisting(ProductCategoryPojo input) throws BadInputException {
    String code = input.getCode();
    if (code == null || code.isBlank()) {
      throw new BadInputException("Invalid category code");
    } else {
      return this.categoriesRepository.findByCode(code);
    }
  }

  @Override
  protected final ProductCategory prepareNewEntityFromInputPojo(ProductCategoryPojo inputPojo) throws BadInputException {
    ProductCategory target = super.prepareNewEntityFromInputPojo(inputPojo);
    if (inputPojo.getParent() != null) {
      this.passParentIfMatchingEntityExists(target, inputPojo.getParent());
    }
    return target;
  }

  @Override
  protected final ProductCategoryPojo persistEntityWithUpdatesFromPojo(ProductCategoryPojo changes, ProductCategory existingEntity) throws BadInputException {
    ProductCategory preparedEntity = dataTransportService.applyChangesToExistingEntity(changes, existingEntity);
    this.passParentIfMatchingEntityExists(preparedEntity, changes.getParent());
    if (!existingEntity.equals(preparedEntity)) {
      return changes;
    }
    return this.persist(preparedEntity);
  }

  private void passParentIfMatchingEntityExists(ProductCategory target, ProductCategoryPojo sourceParent) {
    String sourceParentCode = sourceParent.getCode();
    ProductCategory previousExistingParent = target.getParent();
    if (sourceParentCode != null && (previousExistingParent == null || !previousExistingParent.getCode().equals(sourceParentCode))) {
      Optional<ProductCategory> parentMatch = categoriesRepository.findByCode(sourceParentCode);
      if (parentMatch.isPresent()) {
        target.setParent(parentMatch.get());
      }
    }
  }
}
