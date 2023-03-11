

package com.huststore.jpa.services.conversion;

import com.huststore.dto.CustomerPojo;
import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface ICustomersConverterJpaService
  extends ITwoWayConverterJpaService<CustomerPojo, Customer> {
}
