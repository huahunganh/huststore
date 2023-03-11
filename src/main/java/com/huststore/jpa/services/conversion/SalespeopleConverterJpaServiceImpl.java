

package com.huststore.jpa.services.conversion;

import com.huststore.dto.PersonPojo;
import com.huststore.dto.SalespersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.entities.Salesperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalespeopleConverterJpaServiceImpl
  implements ISalespeopleConverterJpaService {

  private final IPeopleConverterJpaService peopleService;

  @Autowired
  public SalespeopleConverterJpaServiceImpl(IPeopleConverterJpaService peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  public SalespersonPojo convertToPojo(Salesperson source) {
    PersonPojo targetPerson = peopleService.convertToPojo(source.getPerson());
    return SalespersonPojo.builder()
      .id(source.getId())
      .person(targetPerson)
      .build();
  }

  @Override
  public Salesperson convertToNewEntity(SalespersonPojo source) throws BadInputException {
    Salesperson target = new Salesperson();
    Person targetPerson = peopleService.convertToNewEntity(source.getPerson());
    target.setPerson(targetPerson);
    return target;
  }

  @Override
  public Salesperson applyChangesToExistingEntity(SalespersonPojo source, Salesperson target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
