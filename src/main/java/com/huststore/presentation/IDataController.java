

package com.huststore.presentation;

import com.huststore.dto.DataPagePojo;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Interface for API controllers that handle reading of data stored in a persistence context and
 * return it converted to the parameterized class, presumably a Pojo
 * @param <P> The Pojo class
 */
public interface IDataController<P> {

  /**
   * Get a paged collection of Pojos.
   * @param requestParams A map of key/value String pairs, that may be parsed as sorting, pagination and filtering
   *                      conditions.
   * @return An instance of DataPagePojo, with the items and page information.
   */
  DataPagePojo<P> readMany(@NotNull Map<String, String> requestParams);
}
