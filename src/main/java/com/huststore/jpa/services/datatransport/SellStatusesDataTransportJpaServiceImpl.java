

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.SellStatusPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.SellStatus;
import org.springframework.stereotype.Service;

@Service
public class SellStatusesDataTransportJpaServiceImpl
  implements ISellStatusesDataTransportJpaService {

  public SellStatusesDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public SellStatus applyChangesToExistingEntity(SellStatusPojo source, SellStatus existing) throws BadInputException {
    SellStatus target = new SellStatus(existing);

    Integer code = source.getCode();
    if (code != null && !target.getCode().equals(code))  {
      target.setCode(code);
    }

    String name = source.getName();
    if (name != null && !name.isBlank() && !target.getName().equals(name)) {
      target.setName(name);
    }

    return target;
  }
}
