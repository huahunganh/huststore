

package com.huststore.jpa.services.crud;

import com.huststore.dto.PersonPojo;
import com.huststore.dto.SalespersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Salesperson;
import com.huststore.jpa.repositories.ISalespeopleJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.ISalespeopleConverterJpaService;
import com.huststore.jpa.services.datatransport.ISalespeopleDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class SalespeopleJpaCrudServiceImpl
  extends GenericCrudJpaService<SalespersonPojo, Salesperson>
  implements ISalespeopleCrudService {

  private final ISalespeopleJpaRepository salespeopleRepository;

  @Autowired
  public SalespeopleJpaCrudServiceImpl(ISalespeopleJpaRepository repository,
                                       ISalespeopleConverterJpaService converter,
                                       ISalespeopleDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.salespeopleRepository = repository;
  }

  @Override
  public Optional<Salesperson> getExisting(SalespersonPojo input) throws BadInputException {
    PersonPojo person = input.getPerson();
    if (person == null) {
      throw new BadInputException("Salesperson does not have profile information");
    } else {
      String idNumber = person.getIdNumber();
      if (idNumber == null) {
        throw new BadInputException("Salesperson does not have an ID card");
      } else {
        return salespeopleRepository.findByPersonIdNumber(idNumber);
      }
    }
  }
}
