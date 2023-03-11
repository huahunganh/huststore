

package com.huststore.jpa.services;

import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.domain.Sort;

import java.util.Map;

/**
 * An interface to support parsing of Maps into sort order clauses to be used in queries at the persistence layer.
 * @param <E> The target @Entity class
 */
public interface ISortSpecJpaService<E> {

  /**
   * Get base QueryDSL type for building OrderSpecifiers
   */
  EntityPathBase<E> getBasePath();

  /**
   * Reads a Map and generates sort order constraints for a specific entity
   * @param queryParamsMap A map of keys and values
   * @return A Sort order as parsed from the input map. May be null if the input is invalid
   */
  Sort parseMap(Map<String, String> queryParamsMap);
}
