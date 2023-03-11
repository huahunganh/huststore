

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.Person;
import com.huststore.jpa.entities.QPerson;
import com.huststore.jpa.services.IPredicateJpaService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PeoplePredicateJpaServiceImpl
  implements IPredicateJpaService<Person> {

  private final Logger logger = LoggerFactory.getLogger(PeoplePredicateJpaServiceImpl.class);

  @Override
  public QPerson getBasePath() {
    return QPerson.person;
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
          case "idNumber":
            return getBasePath().idNumber.eq(stringValue);
          case "name":
            predicate.and(getBasePath().firstName.eq(stringValue)
                    .or(getBasePath().lastName.eq(stringValue)));
            break;
          case "firstName":
            predicate.and(getBasePath().firstName.eq(stringValue));
            break;
          case "lastName":
            predicate.and(getBasePath().lastName.eq(stringValue));
            break;
          case "email":
            predicate.and(getBasePath().email.eq(stringValue));
            break;
          case "nameLike":
            predicate.and(getBasePath().firstName.likeIgnoreCase("%" + stringValue + "%")
                    .or(getBasePath().lastName.likeIgnoreCase("%" + stringValue + "%")));
            break;
          case "firstNameLike":
            predicate.and(getBasePath().firstName.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "lastNameLike":
            predicate.and(getBasePath().lastName.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "idNumberLike":
            predicate.and(getBasePath().idNumber.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "emailLike":
            predicate.and(getBasePath().email.likeIgnoreCase("%" + stringValue + "%"));
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
