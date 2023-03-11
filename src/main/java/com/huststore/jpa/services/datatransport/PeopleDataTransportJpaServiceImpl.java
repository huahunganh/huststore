

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PeopleDataTransportJpaServiceImpl
  implements IPeopleDataTransportJpaService {

  public PeopleDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public Person applyChangesToExistingEntity(PersonPojo source, Person existing) throws BadInputException {
    Person target = new Person(existing);

    String firstName = source.getFirstName();
    if (firstName != null && !firstName.isBlank() && !target.getFirstName().equals(firstName)) {
      target.setFirstName(firstName);
    }

    String lastName = source.getLastName();
    if (lastName != null && !lastName.isBlank() && !target.getLastName().equals(lastName)) {
      target.setLastName(lastName);
    }

    String email = source.getEmail();
    if (email != null && !email.isBlank() && !target.getEmail().equals(email)) {
      target.setEmail(email);
    }

    // phones may be empty, but not null
    String phone1 = source.getPhone1();
    if (!StringUtils.equals(target.getPhone1(),phone1)) {
        target.setPhone1(phone1);
      }

    String phone2 = source.getPhone2();
    if (phone2 != null) {
      if (!target.getPhone2().equals(phone2)) {
        target.setPhone2(phone2);
      }
    }

    return target;
  }
}
