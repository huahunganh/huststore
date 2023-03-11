

package com.huststore.jpa.services.conversion;

import com.huststore.dto.SellStatusPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.SellStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellStatusesConverterJpaServiceImpl
  implements ISellStatusesConverterJpaService {

  @Autowired
  public SellStatusesConverterJpaServiceImpl() {
  }

  @Override
  public SellStatusPojo convertToPojo(SellStatus source) {
    return SellStatusPojo.builder()
      .code(source.getCode())
      .name(source.getName())
      .build();
  }

  @Override
  public SellStatus convertToNewEntity(SellStatusPojo source) {
    SellStatus target = new SellStatus();
    target.setCode(source.getCode());
    target.setName(source.getName());
    return target;
  }

  @Override
  public SellStatus applyChangesToExistingEntity(SellStatusPojo source, SellStatus target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
