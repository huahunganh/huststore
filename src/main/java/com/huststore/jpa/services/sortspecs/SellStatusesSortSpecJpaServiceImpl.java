

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.QSellStatus;
import com.huststore.jpa.entities.SellStatus;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SellStatusesSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<SellStatus> {

  @Override
  public QSellStatus getBasePath() {
    return QSellStatus.sellStatus;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name",  getBasePath().name.asc()
    );
  }
}
