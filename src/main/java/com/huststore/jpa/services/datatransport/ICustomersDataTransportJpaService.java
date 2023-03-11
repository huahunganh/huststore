

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.CustomerPojo;
import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface ICustomersDataTransportJpaService
  extends IDataTransportJpaService<CustomerPojo, Customer> {
}
