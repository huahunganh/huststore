

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ShipperPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Shipper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippersConverterJpaServiceImpl
  implements IShippersConverterJpaService {

  @Autowired
  public ShippersConverterJpaServiceImpl() {
  }

  @Override
  public ShipperPojo convertToPojo(Shipper source) {
    return ShipperPojo.builder()
      .id(source.getId())
      .name(source.getName())
      .build();
  }

  @Override
  public Shipper convertToNewEntity(ShipperPojo source) {
    Shipper target = new Shipper();
    target.setName(source.getName());
    return target;
  }

  @Override
  public Shipper applyChangesToExistingEntity(ShipperPojo source, Shipper target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
