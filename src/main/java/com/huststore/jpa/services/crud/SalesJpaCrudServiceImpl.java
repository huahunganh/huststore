

package com.huststore.jpa.services.crud;

import com.huststore.dto.ProductPojo;
import com.huststore.dto.SellDetailPojo;
import com.huststore.dto.SellPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Product;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.entities.SellDetail;
import com.huststore.jpa.repositories.IProductsJpaRepository;
import com.huststore.jpa.repositories.ISalesJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IProductsConverterJpaService;
import com.huststore.jpa.services.conversion.ISalesConverterJpaService;
import com.huststore.jpa.services.datatransport.ISalesDataTransportJpaService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SalesJpaCrudServiceImpl
  extends GenericCrudJpaService<SellPojo, Sell>
  implements ISalesCrudService {

  private final ISalesJpaRepository salesRepository;
  private final IProductsJpaRepository productsRepository;
  private final IProductsConverterJpaService productConverter;
  private static final double TAX_PERCENT = 0.19; // TODO refactor into a "tax service" of sorts
  private static final boolean CAN_EDIT_AFTER_PROCESS = true; // TODO refactor as part of application properties

  @Autowired
  public SalesJpaCrudServiceImpl(ISalesJpaRepository repository,
                                 IProductsJpaRepository productsRepository,
                                 ISalesConverterJpaService converter,
                                 ISalesDataTransportJpaService dataTransportService,
                                 IProductsConverterJpaService productConverter) {
    super(repository,
          converter,
          dataTransportService);
    this.salesRepository = repository;
    this.productsRepository = productsRepository;
    this.productConverter = productConverter;
  }

  @Override
  public Optional<Sell> getExisting(SellPojo input) {
    Long buyOrder = input.getBuyOrder();
    if (buyOrder == null) {
      return Optional.empty();
    } else {
      return this.salesRepository.findById(buyOrder);
    }
  }
  @Override
  public SellPojo readOne(Predicate conditions)
      throws EntityNotFoundException {
    Optional<Sell> matchingSell = salesRepository.findOne(conditions);
    if (matchingSell.isPresent()) {
      Sell found = matchingSell.get();
      SellPojo foundPojo = converter.convertToPojo(found);
      List<SellDetailPojo> detailPojos = this.convertDetailsToPojos(found.getDetails());
      foundPojo.setDetails(detailPojos);
      return foundPojo;
    } else {
      throw new EntityNotFoundException("No sell matches the filtering conditions");
    }
  }

  @Override
  protected SellPojo persistEntityWithUpdatesFromPojo(SellPojo changes, Sell existingEntity)
      throws BadInputException {
    Integer statusCode = existingEntity.getStatus().getCode();
    if ((statusCode >= 3 || statusCode < 0) && !CAN_EDIT_AFTER_PROCESS) {
      throw new BadInputException("The requested transaction cannot be modified");
    }
    Sell updatedEntity = dataTransportService.applyChangesToExistingEntity(changes, existingEntity);
    if (updatedEntity.equals(existingEntity)) {
      return changes;
    }
    return this.persist(updatedEntity);
  }

  @Override
  public Sell prepareNewEntityFromInputPojo(SellPojo inputPojo) throws BadInputException {
    Sell target = converter.convertToNewEntity(inputPojo);
    List<SellDetail> detailEntities = this.convertDetailsToEntities(inputPojo.getDetails());
    target.setDetails(detailEntities);
    this.updateTotals(target);
    return target;
  }

  private List<SellDetail> convertDetailsToEntities(Collection<SellDetailPojo> sourceDetails) throws BadInputException {
    List<SellDetail> details = new ArrayList<>();
    for (SellDetailPojo d : sourceDetails) {
      String barcode = d.getProduct().getBarcode();
      if (barcode == null || barcode.isBlank()) {
        throw new BadInputException("Product barcode must be valid");
      }
      Optional<Product> productByBarcode = productsRepository.findByBarcode(barcode);
      if (productByBarcode.isEmpty()) {
        throw new BadInputException("Unexisting product in sell details");
      }
      Product product = productByBarcode.get();
      SellDetail targetDetail = new SellDetail(d.getUnits(), product);
      targetDetail.setUnitValue(product.getPrice());
      details.add(targetDetail);
    }
    return details;
  }

  private List<SellDetailPojo> convertDetailsToPojos(Collection<SellDetail> details) {
    List<SellDetailPojo> sellDetails = new ArrayList<>();
    for (SellDetail sourceSellDetail : details) {
      ProductPojo product = productConverter.convertToPojo(sourceSellDetail.getProduct());
      SellDetailPojo targetSellDetail = SellDetailPojo.builder()
        .id(sourceSellDetail.getId())
        .unitValue(sourceSellDetail.getUnitValue())
        .units(sourceSellDetail.getUnits())
        .product(product)
        .description(sourceSellDetail.getDescription())
        .build();
      sellDetails.add(targetSellDetail);
    }
    return sellDetails;
  }

  private void updateTotals(Sell input) {
    int netValue = 0, taxesValue = 0, totalUnits = 0;
    for (SellDetail sd : input.getDetails()) {
      int unitValue = sd.getUnitValue();
      double unitTaxValue = unitValue * TAX_PERCENT;
      double unitNetValue = unitValue - unitTaxValue;
      taxesValue += (unitTaxValue * sd.getUnits());
      netValue += (unitNetValue * sd.getUnits());
      totalUnits += sd.getUnits();
    }
    input.setTaxesValue(taxesValue);
    input.setNetValue(netValue);
    input.setTotalValue(taxesValue + netValue);
    input.setTotalItems(totalUnits);
  }
}
