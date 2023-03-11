

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.entities.QCustomer;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomersSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<Customer> {

  @Override
  public QCustomer getBasePath() {
    return QCustomer.customer;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "idNumber",  getBasePath().person.idNumber.asc(),
            "firstName", getBasePath().person.firstName.asc(),
            "email",     getBasePath().person.email.asc(),
            "phone1",    getBasePath().person.phone1.asc(),
            "phone2",    getBasePath().person.phone2.asc(),
            "name",      getBasePath().person.lastName.asc(),
            "lastName",  getBasePath().person.lastName.asc()
    );
  }
}
