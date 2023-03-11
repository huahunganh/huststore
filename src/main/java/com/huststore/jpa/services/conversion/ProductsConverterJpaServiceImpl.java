

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ImagePojo;
import com.huststore.dto.ProductCategoryPojo;
import com.huststore.dto.ProductPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Product;
import com.huststore.jpa.entities.ProductCategory;
import com.huststore.jpa.entities.ProductImage;
import com.huststore.jpa.repositories.IProductImagesJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class ProductsConverterJpaServiceImpl
  implements IProductsConverterJpaService {

  private final IProductImagesJpaRepository productImagesRepository;
  private final IImagesConverterJpaService imagesConverterService;
  private final IProductCategoriesConverterJpaService productCategoriesConverterService;

  @Autowired
  public ProductsConverterJpaServiceImpl(IProductImagesJpaRepository productImagesRepository,
                                         IImagesConverterJpaService imagesConverterService,
                                         IProductCategoriesConverterJpaService productCategoriesConverterService) {
    this.productImagesRepository = productImagesRepository;
    this.imagesConverterService = imagesConverterService;
    this.productCategoriesConverterService = productCategoriesConverterService;
  }

  // TODO this method can be expensive
  @Override
  public ProductPojo convertToPojo(Product source) {
    ProductPojo target = ProductPojo.builder()
      .id(source.getId())
      .name(source.getName())
      .barcode(source.getBarcode())
      .price(source.getPrice())
      .currentStock(source.getStockCurrent())
      .criticalStock(source.getStockCritical())
      .build();
    Set<ImagePojo> images = new HashSet<>();
    for (ProductImage pi : productImagesRepository.deepFindProductImagesByProductId(source.getId())) {
      ImagePojo targetImage = imagesConverterService.convertToPojo(pi.getImage());
      if (targetImage != null) {
        images.add(targetImage);
      }
    }
    target.setImages(images);

    ProductCategory category = source.getProductCategory();
    if (category != null) {
      ProductCategoryPojo categoryPojo = productCategoriesConverterService.convertToPojo(category);
      target.setCategory(categoryPojo);
    }
    return target;
  }

  @Override
  public Product convertToNewEntity(ProductPojo source) {
    Product target = new Product();
    target.setName(source.getName());
    target.setBarcode(source.getBarcode());
    target.setPrice(source.getPrice());
    if (source.getCurrentStock() != null) {
      target.setStockCurrent(source.getCurrentStock());
    }
    if (source.getCriticalStock() != null) {
      target.setStockCritical(source.getCriticalStock());
    }
    return target;
  }

  @Override
  public Product applyChangesToExistingEntity(ProductPojo source, Product target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
