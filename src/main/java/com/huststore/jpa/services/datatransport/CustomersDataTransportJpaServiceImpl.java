

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.CustomerPojo;
import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersDataTransportJpaServiceImpl
  implements ICustomersDataTransportJpaService {

  private final IPeopleDataTransportJpaService peopleService;

  @Autowired
  public CustomersDataTransportJpaServiceImpl(IPeopleDataTransportJpaService peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  public Customer applyChangesToExistingEntity(CustomerPojo source, Customer existing) throws BadInputException {
    Customer target = new Customer(existing);
    Person existingPerson = existing.getPerson();

    PersonPojo sourcePerson = source.getPerson();
    Person person = peopleService.applyChangesToExistingEntity(sourcePerson, existingPerson);
    target.setPerson(person);

    return target;
  }
}
