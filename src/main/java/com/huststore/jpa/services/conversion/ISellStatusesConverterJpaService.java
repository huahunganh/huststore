

package com.huststore.jpa.services.conversion;

import com.huststore.dto.SellStatusPojo;
import com.huststore.jpa.entities.SellStatus;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface ISellStatusesConverterJpaService
  extends ITwoWayConverterJpaService<SellStatusPojo, SellStatus> {
}
