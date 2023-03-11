

package com.huststore.jpa.services.conversion;

import com.huststore.dto.PersonPojo;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IPeopleConverterJpaService
  extends ITwoWayConverterJpaService<PersonPojo, Person> {
}
