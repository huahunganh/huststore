

package com.huststore.jpa.services.crud;

import com.huststore.dto.ShipperPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Shipper;
import com.huststore.jpa.repositories.IShippersJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IShippersConverterJpaService;
import com.huststore.jpa.services.datatransport.IShippersDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ShippersJpaCrudServiceImpl
  extends GenericCrudJpaService<ShipperPojo, Shipper> implements IShippersCrudService {

  private final IShippersJpaRepository shippersRepository;

  @Autowired
  public ShippersJpaCrudServiceImpl(IShippersJpaRepository repository,
                                    IShippersConverterJpaService converter,
                                    IShippersDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.shippersRepository = repository;
  }

  @Override
  public Optional<Shipper> getExisting(ShipperPojo input) throws BadInputException {
    String name = input.getName();
    if (name == null || name.isBlank()) {
      throw new BadInputException("Billing type has no name");
    } else {
      return shippersRepository.findByName(name);
    }
  }
}
