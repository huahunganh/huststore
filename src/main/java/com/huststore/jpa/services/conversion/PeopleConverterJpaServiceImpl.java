

package com.huststore.jpa.services.conversion;

import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleConverterJpaServiceImpl
  implements IPeopleConverterJpaService {

  @Autowired
  public PeopleConverterJpaServiceImpl() {
  }

  @Override
  public PersonPojo convertToPojo(Person source) {
    PersonPojo target = PersonPojo.builder()
      .id(source.getId())
      .idNumber(source.getIdNumber())
      .firstName(source.getFirstName())
      .lastName(source.getLastName())
      .email(source.getEmail())
      .build();
    if (source.getPhone1() != null) {
      target.setPhone1(source.getPhone1());
    }
    if (source.getPhone2() != null) {
      target.setPhone2(source.getPhone2());
    }
    return target;
  }

  @Override
  public Person convertToNewEntity(PersonPojo source) {
    Person target = new Person();
    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());
    target.setIdNumber(source.getIdNumber());
    target.setEmail(source.getEmail());
    if (source.getPhone1() != null) {
      target.setPhone1(source.getPhone1());
    }
    if (source.getPhone2() != null) {
      target.setPhone2(source.getPhone2());
    }
    return target;
  }

  @Override
  public Person applyChangesToExistingEntity(PersonPojo source, Person target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
