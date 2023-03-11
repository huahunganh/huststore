

package com.huststore.jpa.services.conversion;

import com.huststore.dto.CustomerPojo;
import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersConverterJpaServiceImpl
  implements ICustomersConverterJpaService {

  private final IPeopleConverterJpaService peopleService;

  @Autowired
  public CustomersConverterJpaServiceImpl(IPeopleConverterJpaService peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  public CustomerPojo convertToPojo(Customer source) {
    PersonPojo targetPerson = peopleService.convertToPojo(source.getPerson());
    return CustomerPojo.builder()
      .id(source.getId())
      .person(targetPerson)
      .build();
  }

  @Override
  public Customer convertToNewEntity(CustomerPojo source) throws BadInputException {
    Customer target = new Customer();
    Person targetPerson = peopleService.convertToNewEntity(source.getPerson());
    target.setPerson(targetPerson);
    return target;
  }

  @Override
  public Customer applyChangesToExistingEntity(CustomerPojo source, Customer target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
