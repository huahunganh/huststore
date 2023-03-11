

package com.huststore.jpa.services.conversion;

import com.huststore.dto.SellPojo;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface ISalesConverterJpaService
  extends ITwoWayConverterJpaService<SellPojo, Sell> {
}
