

package com.huststore.jpa.services.crud;

import com.huststore.dto.CustomerPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.repositories.ICustomersJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.ICustomersConverterJpaService;
import com.huststore.jpa.services.datatransport.ICustomersDataTransportJpaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class CustomersJpaCrudServiceImpl
  extends GenericCrudJpaService<CustomerPojo, Customer>
  implements ICustomersCrudService {

  private final ICustomersJpaRepository customersRepository;

  @Autowired
  public CustomersJpaCrudServiceImpl(ICustomersJpaRepository repository,
                                     ICustomersConverterJpaService converter,
                                     ICustomersDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.customersRepository = repository;
  }

  @Override
  public Optional<Customer> getExisting(CustomerPojo input) throws BadInputException {
    String idNumber = input.getPerson().getIdNumber();
    if (StringUtils.isBlank(idNumber)) {
      throw new BadInputException("Customer does not have an ID card");
    } else {
      return customersRepository.findByPersonIdNumber(idNumber);
    }
  }
}
