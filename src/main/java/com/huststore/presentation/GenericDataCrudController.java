

package com.huststore.presentation;

import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.services.ICrudJpaService;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.querydsl.core.types.Predicate;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Map;

public class GenericDataCrudController<P, E>
  extends GenericDataController<P, E>
  implements IDataCrudController<P> {

  protected GenericDataCrudController(PaginationService paginationService,
                                      ISortSpecJpaService<E> sortService,
                                      ICrudJpaService<P, E> crudService,
                                      IPredicateJpaService<E> predicateService) {
    super(paginationService, sortService, crudService, predicateService);
  }

  @Override
  public void create(P input)
      throws BadInputException, EntityExistsException {
    crudService.create(input);
  }

  @Override
  public void update(P input, Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    if (!requestParams.isEmpty()) {
      Predicate predicate = predicateService.parseMap(requestParams);
      crudService.update(input, predicate);
    } else {
      crudService.update(input);
    }
  }

  @Override
  public void delete(Map<String, String> requestParams)
      throws EntityNotFoundException {
    Predicate predicate = predicateService.parseMap(requestParams);
    crudService.delete(predicate);
  }
}
