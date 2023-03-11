

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.QUser;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.services.IPredicateJpaService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsersJpaPredicateServiceImpl
  implements IPredicateJpaService<User> {

  private final Logger logger = LoggerFactory.getLogger(UsersJpaPredicateServiceImpl.class);

  @Override
  public QUser getBasePath() {
    return QUser.user;
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
          case "email":
            predicate.and(getBasePath().person.email.eq(stringValue));
            break;
          case "nameLike":
            predicate.and(getBasePath().name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "emailLike":
            predicate.and(getBasePath().person.email.likeIgnoreCase("%" + stringValue + "%"));
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
