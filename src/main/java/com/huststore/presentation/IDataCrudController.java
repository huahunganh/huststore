

package com.huststore.presentation;

import com.huststore.exceptions.BadInputException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Map;

/**
 * Interface for API controllers that handle CRUD requests involving unique identifiers
 * @param <P> The Pojo class
 */
public interface IDataCrudController<P>
  extends IDataController<P> {

  void create(P input) throws BadInputException, EntityExistsException;

  void update(P input, Map<String, String> requestParams) throws BadInputException, EntityNotFoundException;

  void delete(Map<String, String> requestParams) throws EntityNotFoundException;
}
