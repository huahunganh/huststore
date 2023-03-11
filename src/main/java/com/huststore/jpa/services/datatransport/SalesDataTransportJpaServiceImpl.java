package com.huststore.jpa.services.datatransport;

import com.huststore.dto.AddressPojo;
import com.huststore.dto.CustomerPojo;
import com.huststore.dto.SellPojo;
import com.huststore.dto.ShipperPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.*;
import com.huststore.jpa.repositories.IAddressesJpaRepository;
import com.huststore.jpa.repositories.ICustomersJpaRepository;
import com.huststore.jpa.repositories.ISellStatusesJpaRepository;
import com.huststore.jpa.repositories.IShippersJpaRepository;
import com.huststore.jpa.services.conversion.ICustomersConverterJpaService;
import com.huststore.jpa.services.crud.ICustomersCrudService;
import com.huststore.jpa.services.helpers.RegexMatcherAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class SalesDataTransportJpaServiceImpl
  implements ISalesDataTransportJpaService {

  private static final String IS_NOT_VALID = "is not valid";
  private final ISellStatusesJpaRepository statusesRepository;
  private final IShippersJpaRepository shippersRepository;
  private final IAddressesJpaRepository addressesRepository;
  private final ICustomersConverterJpaService customersConverter;
  private final ICustomersCrudService customersService;
  private final ICustomersJpaRepository customersRepository;
  private final ConversionService conversion;
  private final Validator validator;
  private final RegexMatcherAdapter regexMatcherAdapter;

  @Autowired
  public SalesDataTransportJpaServiceImpl(ConversionService conversion,
                                          ISellStatusesJpaRepository statusesRepository,
                                          IAddressesJpaRepository addressesRepository,
                                          IShippersJpaRepository shippersRepository,
                                          ICustomersConverterJpaService customersConverter,
                                          ICustomersCrudService customersService,
                                          ICustomersJpaRepository customersRepository,
                                          Validator validator,
                                          RegexMatcherAdapter regexMatcherAdapter) {
    this.conversion = conversion;
    this.statusesRepository = statusesRepository;
    this.addressesRepository = addressesRepository;
    this.shippersRepository = shippersRepository;
    this.customersConverter = customersConverter;
    this.customersService = customersService;
    this.customersRepository = customersRepository;
    this.validator = validator;
    this.regexMatcherAdapter = regexMatcherAdapter;
  }

  @Transactional
  @Override
  public Sell applyChangesToExistingEntity(SellPojo source, Sell existing) throws BadInputException {
    Sell target = new Sell(existing);

    if (source.getDate() != null) {
      target.setDate(source.getDate());
    }
    if (source.getStatus() != null) {
      this.applyStatus(source, target);
    }
    if (source.getCustomer() != null && source.getCustomer().getPerson() != null) {
      this.applyCustomer(source, target);
    }
    if (source.getBillingAddress() != null) {
      this.applyBillingAddress(source, target);
    }
    if (source.getShippingAddress() != null) {
      this.applyShippingAddress(source, target);
    }

    if (source.getShipper() != null) {
      this.applyShipper(source, target);
    }

    return target;
  }

  private void applyStatus(SellPojo source, Sell target) throws BadInputException {
    String statusName = source.getStatus();
    if (StringUtils.isBlank(statusName)) {
      statusName = "Pending";
    }

    Optional<SellStatus> existingStatus = statusesRepository.findByName(statusName);
    if (existingStatus.isEmpty()) {
      throw new BadInputException("Status '" + statusName + "' " + IS_NOT_VALID);
    } else {
      target.setStatus(existingStatus.get());
    }
  }


  private void applyCustomer(SellPojo source, Sell target) throws BadInputException {
    CustomerPojo sourceCustomer = source.getCustomer();
    if (StringUtils.isBlank(sourceCustomer.getPerson().getIdNumber())) {
      throw new BadInputException("Customer must possess valid personal information");
    } else {
      Optional<Customer> existing = customersService.getExisting(sourceCustomer);
      if (existing.isPresent()) {
        target.setCustomer(existing.get());
      } else {
        Customer targetCustomer = customersConverter.convertToNewEntity(sourceCustomer);
        targetCustomer = customersRepository.saveAndFlush(targetCustomer);
        target.setCustomer(targetCustomer);
      }
    }
  }

  private void applyBillingAddress(SellPojo source, Sell target) throws BadInputException {
    AddressPojo billingAddress = source.getBillingAddress();
    if (billingAddress != null) {
      try {
        Address targetAddress = this.fetchOrConvertAddress(billingAddress);
        target.setBillingAddress(targetAddress);
      } catch (BadInputException ex) {
        throw new BadInputException("The provided billing address " + IS_NOT_VALID);
      }
    }
  }

  private void applyShippingAddress(SellPojo source, Sell target) throws BadInputException {
    AddressPojo shippingAddress = source.getShippingAddress();
    if (shippingAddress != null) {
      try {
        Address targetAddress = this.fetchOrConvertAddress(shippingAddress);
        target.setShippingAddress(targetAddress);
      } catch (BadInputException ex) {
        throw new BadInputException("The provided shipping address " + IS_NOT_VALID);
      }
    }
  }

  private void applyShipper(SellPojo source, Sell target) throws BadInputException {
    ShipperPojo sourceShipper = source.getShipper();
    if (sourceShipper != null) {
      Set<ConstraintViolation<ShipperPojo>> validations = validator.validate(sourceShipper);
      if (!validations.isEmpty()) {
        throw new BadInputException("Invalid shipper");
      } else {
        Optional<Shipper> byName = shippersRepository.findByName(sourceShipper.getName());
        if (byName.isEmpty()) {
          throw new BadInputException("The specified shipper does not exist");
        } else {
          target.setShipper(byName.get());
        }
      }
    }
  }

  private Address fetchOrConvertAddress(AddressPojo source) throws BadInputException {
    Set<ConstraintViolation<AddressPojo>> validations = validator.validate(source);
    if (!validations.isEmpty()) {
      throw new BadInputException("Invalid address");
    } else {
      Optional<Address> matchingAddress = addressesRepository.findByFields(
        source.getCity(),
        source.getMunicipality(),
        source.getFirstLine(),
        source.getSecondLine(),
        source.getPostalCode(),
        source.getNotes());
      return matchingAddress.orElseGet(() -> conversion.convert(source, Address.class));
    }
  }

}
