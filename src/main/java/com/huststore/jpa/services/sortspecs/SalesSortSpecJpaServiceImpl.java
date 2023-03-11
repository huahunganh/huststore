

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.QSell;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SalesSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<Sell> {

  @Override
  public QSell getBasePath() {
    return QSell.sell;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "buyOrder",       getBasePath().id.asc(),
            "date",           getBasePath().date.asc(),
            "status",         getBasePath().status.code.asc(),
            "customer",       getBasePath().customer.person.lastName.asc(),
            "shipper",        getBasePath().shipper.name.asc(),
            "totalValue",     getBasePath().totalValue.asc(),
            "netValue",       getBasePath().netValue.asc(),
            "totalItems",     getBasePath().totalItems.asc(),
            "transportValue", getBasePath().transportValue.asc()
    );
  }
}
