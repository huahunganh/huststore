

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.PersonPojo;
import com.huststore.dto.SalespersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.entities.Salesperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalespeopleDataTransportJpaServiceImpl
  implements ISalespeopleDataTransportJpaService {

  private final IPeopleDataTransportJpaService peopleService;

  @Autowired
  public SalespeopleDataTransportJpaServiceImpl(IPeopleDataTransportJpaService peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  public Salesperson applyChangesToExistingEntity(SalespersonPojo source, Salesperson existing) throws BadInputException {
    Salesperson target = new Salesperson(existing);
    Person existingPerson = existing.getPerson();

    PersonPojo sourcePerson = source.getPerson();
    if (sourcePerson == null) {
      throw new BadInputException("Salesperson must have a person profile");
    }
    Person person = peopleService.applyChangesToExistingEntity(sourcePerson, existingPerson);
    target.setPerson(person);

    return target;
  }
}
