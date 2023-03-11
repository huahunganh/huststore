

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.QShipper;
import com.huststore.jpa.entities.Shipper;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippersSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<Shipper> {

  @Override
  public QShipper getBasePath() {
    return QShipper.shipper;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name",  QShipper.shipper.name.asc()
    );
  }

}
