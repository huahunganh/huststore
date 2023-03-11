

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ShipperPojo;
import com.huststore.jpa.entities.Shipper;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IShippersConverterJpaService
  extends ITwoWayConverterJpaService<ShipperPojo, Shipper> {
}
