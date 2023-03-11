

package com.huststore.jpa.services.conversion;

import com.huststore.dto.AddressPojo;
import com.huststore.dto.CustomerPojo;
import com.huststore.dto.SalespersonPojo;
import com.huststore.dto.SellPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Sell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SalesConverterJpaServiceImpl
  implements ISalesConverterJpaService {
  private final ICustomersConverterJpaService customersConverter;
  private final ISalespeopleConverterJpaService salespeopleConverter;
  private final ConversionService conversionService;

  @Autowired
  public SalesConverterJpaServiceImpl(ICustomersConverterJpaService customersConverter,
                                      ISalespeopleConverterJpaService salespeopleConverter,
                                      ConversionService conversionService) {
    this.customersConverter = customersConverter;
    this.salespeopleConverter = salespeopleConverter;
    this.conversionService = conversionService;
  }

  @Override
  public SellPojo convertToPojo(Sell source) {
    SellPojo target = SellPojo.builder()
      .buyOrder(source.getId())
      .date(source.getDate())
      .netValue(source.getNetValue())
      .taxValue(source.getTaxesValue())
      .totalValue(source.getTotalValue())
      .totalItems(source.getTotalItems())
      .transportValue(source.getTransportValue())
      .token(source.getTransactionToken())
      .build();

    target.setStatus(source.getStatus().getName());

    if (source.getBillingAddress() != null) {
      AddressPojo billingAddress = conversionService.convert(source.getBillingAddress(), AddressPojo.class);
      target.setBillingAddress(billingAddress);
    }

    if (source.getShippingAddress() != null) {
      AddressPojo shippingAddress = conversionService.convert(source.getShippingAddress(), AddressPojo.class);
      target.setShippingAddress(shippingAddress);
    }

    CustomerPojo customer = customersConverter.convertToPojo(source.getCustomer());
    target.setCustomer(customer);

    if (source.getSalesperson() != null) {
      SalespersonPojo salesperson = salespeopleConverter.convertToPojo(source.getSalesperson());
      target.setSalesperson(salesperson);
    }
    return target;
  }

  @Transactional
  @Override
  public Sell convertToNewEntity(SellPojo source) throws BadInputException {
    Sell target = new Sell();

    if (source.getDate() != null) {
      target.setDate(source.getDate());
    }

    return target;
  }

  @Override
  public Sell applyChangesToExistingEntity(SellPojo source, Sell target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
