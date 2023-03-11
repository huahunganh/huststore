

package com.huststore.jpa.services.crud;

import com.huststore.dto.PersonPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.repositories.IPeopleJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IPeopleConverterJpaService;
import com.huststore.jpa.services.datatransport.IPeopleDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class PeopleJpaCrudServiceImpl
  extends GenericCrudJpaService<PersonPojo, Person> implements IPeopleCrudService {

  private final IPeopleJpaRepository peopleRepository;

  @Autowired
  public PeopleJpaCrudServiceImpl(IPeopleJpaRepository repository,
                                  IPeopleConverterJpaService converter,
                                  IPeopleDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.peopleRepository = repository;
  }

  @Override
  public Optional<Person> getExisting(PersonPojo input) throws BadInputException {
    String idCard = input.getIdNumber();
    if (idCard == null || idCard.isBlank()) {
      throw new BadInputException("Customer does not have ID card");
    } else {
      return peopleRepository.findByIdNumber(idCard);
    }
  }
}
