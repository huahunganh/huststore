

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ShipperPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Shipper;
import org.springframework.stereotype.Service;

@Service
public class ShippersDataTransportJpaServiceImpl
  implements IShippersDataTransportJpaService {

  public ShippersDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public Shipper applyChangesToExistingEntity(ShipperPojo source, Shipper existing) throws BadInputException {
    Shipper target = new Shipper(existing);

    String name = source.getName();
    if (name != null && !name.isBlank() && !target.getName().equals(name)) {
      target.setName(name);
    }

    return target;
  }
}
