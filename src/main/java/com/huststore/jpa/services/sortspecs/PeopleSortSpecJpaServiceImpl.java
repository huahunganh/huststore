

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.Person;
import com.huststore.jpa.entities.QPerson;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PeopleSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<Person> {

  @Override
  public QPerson getBasePath() {
    return QPerson.person;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "idNumber",  getBasePath().idNumber.asc(),
            "firstName", getBasePath().firstName.asc(),
            "email",     getBasePath().email.asc(),
            "phone1",    getBasePath().phone1.asc(),
            "phone2",    getBasePath().phone2.asc(),
            "name",      getBasePath().lastName.asc(),
            "lastName",  getBasePath().lastName.asc()
    );
  }

}
