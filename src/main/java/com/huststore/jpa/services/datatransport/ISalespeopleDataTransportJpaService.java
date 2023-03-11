

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.SalespersonPojo;
import com.huststore.jpa.entities.Salesperson;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface ISalespeopleDataTransportJpaService
  extends IDataTransportJpaService<SalespersonPojo, Salesperson> {
}
