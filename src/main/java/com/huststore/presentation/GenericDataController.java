

package com.huststore.presentation;

import com.huststore.dto.DataPagePojo;
import com.huststore.jpa.services.ICrudJpaService;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * RestController that implements IDataController with a GenericJpaService
 * @param <P> The Pojo class
 * @param <E> The Entity class
 */
public abstract class GenericDataController<P, E>
  implements IDataController<P> {

  protected PaginationService paginationService;
  protected ISortSpecJpaService<E> sortService;
  protected final ICrudJpaService<P, E> crudService;
  protected final IPredicateJpaService<E> predicateService;

  public GenericDataController(PaginationService paginationService,
                               ISortSpecJpaService<E> sortService,
                               ICrudJpaService<P, E> crudService,
                               IPredicateJpaService<E> predicateService) {
    this.paginationService = paginationService;
    this.sortService = sortService;
    this.crudService = crudService;
    this.predicateService = predicateService;
  }

  /**
   * Retrieve a page of items with a fixed size and offset index.
   * An optional Map (like query string parameters) can be provided for filtering criteria
   * @param requestParams May contain filtering conditions and/or page size & page index parameters.
   * @return A paged collection of Pojos.
   */
  @Override
  public DataPagePojo<P> readMany(@NotNull Map<String, String> requestParams) {

    int pageIndex = paginationService.determineRequestedPageIndex(requestParams);
    int pageSize = paginationService.determineRequestedPageSize(requestParams);

    Sort order = null;
    if (requestParams != null && !requestParams.isEmpty()) {
      order = sortService.parseMap(requestParams);
    }

    Predicate filters = null;
    if (requestParams != null && !requestParams.isEmpty()) {
      filters = predicateService.parseMap(requestParams);
    }

    return crudService.readMany(pageIndex, pageSize, order, filters);
  }
}
