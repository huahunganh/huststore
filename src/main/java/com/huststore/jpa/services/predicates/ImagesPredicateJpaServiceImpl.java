

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.Image;
import com.huststore.jpa.entities.QImage;
import com.huststore.jpa.services.IPredicateJpaService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImagesPredicateJpaServiceImpl
  implements IPredicateJpaService<Image> {

  private final Logger logger = LoggerFactory.getLogger(ImagesPredicateJpaServiceImpl.class);

  @Override
  public QImage getBasePath() {
    return QImage.image;
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
          case "code":
            return getBasePath().code.eq(stringValue);
          case "filename":
            return getBasePath().filename.eq(stringValue);
          case "codeLike":
            predicate.and(getBasePath().code.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "filenameLike":
            predicate.and(getBasePath().filename.likeIgnoreCase("%" + stringValue + "%"));
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
