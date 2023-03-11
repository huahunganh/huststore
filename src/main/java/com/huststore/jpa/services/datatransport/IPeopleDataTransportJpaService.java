

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.PersonPojo;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IPeopleDataTransportJpaService
  extends IDataTransportJpaService<PersonPojo, Person> {
}
