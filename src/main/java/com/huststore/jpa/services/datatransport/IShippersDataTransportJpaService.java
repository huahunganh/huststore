

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ShipperPojo;
import com.huststore.jpa.entities.Shipper;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IShippersDataTransportJpaService
  extends IDataTransportJpaService<ShipperPojo, Shipper> {
}
