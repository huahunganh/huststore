

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ProductPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductsDataTransportJpaServiceImpl
  implements IProductsDataTransportJpaService {

  public ProductsDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public Product applyChangesToExistingEntity(ProductPojo source, Product existing) throws BadInputException {
    Product target = new Product(existing);

    String barcode = source.getBarcode();
    if (barcode != null && !barcode.isBlank() && !target.getBarcode().equals(barcode)) {
      target.setBarcode(barcode);
    }

    String name = source.getName();
    if (name != null && !name.isBlank() && !target.getName().equals(name)) {
      target.setName(name);
    }

    Integer price = source.getPrice();
    target.setPrice(price);

    String description = source.getDescription();
    if (description != null) {
      target.setDescription(description);
    }

    Integer currentStock = source.getCurrentStock();
    if (currentStock != null) {
      target.setStockCurrent(currentStock);
    }

    return target;
  }
}
