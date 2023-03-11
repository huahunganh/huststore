

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.QShipper;
import com.huststore.jpa.entities.Shipper;
import com.huststore.jpa.services.IPredicateJpaService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippersPredicateJpaServiceImpl
  implements IPredicateJpaService<Shipper> {

  private final Logger logger = LoggerFactory.getLogger(ShippersPredicateJpaServiceImpl.class);

  @Override
  public QShipper getBasePath() {
    return QShipper.shipper;
  }

  @Override
  public Predicate parseMap(Map<String, String> queryParamsMap) {
    BooleanBuilder predicate = new BooleanBuilder();
    for (Map.Entry<String, String> entry : queryParamsMap.entrySet()) {
      String paramName = entry.getKey();
      String stringValue = entry.getValue();
      try {
        switch (paramName) {
          case "id":
            return getBasePath().id.eq(Long.valueOf(stringValue));
          case "name":
            return getBasePath().name.eq(stringValue);
          case "nameLike":
            predicate.and(getBasePath().name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          default:
            break;
        }
      } catch (NumberFormatException exc) {
        logger.info("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue);
      }
    }

    return predicate;
  }
}
