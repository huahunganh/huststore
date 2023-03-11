

package com.huststore.jpa.services.crud;

import com.huststore.dto.SellStatusPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.SellStatus;
import com.huststore.jpa.repositories.ISellStatusesJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.ISellStatusesConverterJpaService;
import com.huststore.jpa.services.datatransport.ISellStatusesDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class SellStatusesJpaCrudServiceImpl
  extends GenericCrudJpaService<SellStatusPojo, SellStatus>
  implements ISellStatusesCrudService {

  private final ISellStatusesJpaRepository statusesRepository;

  @Autowired
  public SellStatusesJpaCrudServiceImpl(ISellStatusesJpaRepository repository,
                                        ISellStatusesConverterJpaService converter,
                                        ISellStatusesDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.statusesRepository = repository;
  }

  @Override
  public Optional<SellStatus> getExisting(SellStatusPojo input) throws BadInputException {
    String name = input.getName();
    if (name == null || name.isBlank()) {
      throw new BadInputException("Invalid status name");
    } else {
      return statusesRepository.findByName(name);
    }
  }
}
