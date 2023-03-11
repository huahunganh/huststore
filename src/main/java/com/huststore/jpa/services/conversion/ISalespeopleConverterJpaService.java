

package com.huststore.jpa.services.conversion;

import com.huststore.dto.SalespersonPojo;
import com.huststore.jpa.entities.Salesperson;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface ISalespeopleConverterJpaService
  extends ITwoWayConverterJpaService<SalespersonPojo, Salesperson> {
}
