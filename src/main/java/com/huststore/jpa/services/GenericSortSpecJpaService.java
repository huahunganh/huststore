

package com.huststore.jpa.services;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;

import java.util.Map;

public abstract class GenericSortSpecJpaService<E>
  implements ISortSpecJpaService<E> {

  private Map<String, OrderSpecifier<?>> orderSpecMap;

  protected abstract Map<String, OrderSpecifier<?>> createOrderSpecMap();

  @Override
  public Sort parseMap(Map<String, String> queryParamsMap) {
    if (!queryParamsMap.containsKey("sortBy")) {
      return Sort.unsorted();
    }
    String propertyName = queryParamsMap.get("sortBy");
    OrderSpecifier<?> orderSpecifier = this.getOrderSpecMap().get(propertyName);
    Sort sortBy = QSort.by(orderSpecifier);
    switch (queryParamsMap.get("order")) {
      case "asc":
        return sortBy.ascending();
      case "desc":
        return sortBy.descending();
      default:
        return sortBy;
    }
  }

  private Map<String, OrderSpecifier<?>> getOrderSpecMap() {
    if (this.orderSpecMap == null) {
      this.orderSpecMap = this.createOrderSpecMap();
    }
    return this.orderSpecMap;
  }
}
