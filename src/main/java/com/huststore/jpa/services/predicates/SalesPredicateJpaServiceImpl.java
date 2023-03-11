

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.QSell;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.services.IPredicateJpaService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Service
public class SalesPredicateJpaServiceImpl
  implements IPredicateJpaService<Sell> {

  private final Logger logger = LoggerFactory.getLogger(SalesPredicateJpaServiceImpl.class);

  @Override
  public QSell getBasePath() {
    return QSell.sell;
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
          case "buyOrder":
            return getBasePath().id.eq(Long.valueOf(stringValue));
          case "date":
            predicate.and(getBasePath().date.eq(Instant.parse(stringValue)));
            break;
          case "statusName":
            predicate.and(getBasePath().status.name.eq(stringValue));
            break;
          case "token":
            predicate.and(getBasePath().transactionToken.eq(stringValue));
            break;
          default:
            break;
        }
      } catch (NumberFormatException exc) {
        logger.info("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue);
      } catch (DateTimeParseException exc) {
        logger.warn("Param '{}' couldn't be parsed as date (value: '{}')", paramName, stringValue);
      }
    }

    return predicate;
  }
}
