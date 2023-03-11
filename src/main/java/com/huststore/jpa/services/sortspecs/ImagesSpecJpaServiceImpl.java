

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.Image;
import com.huststore.jpa.entities.QImage;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImagesSpecJpaServiceImpl
  extends GenericSortSpecJpaService<Image> {

  @Override
  public QImage getBasePath() {
    return QImage.image;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "code",     getBasePath().code.asc(),
            "filename", getBasePath().filename.asc(),
            "url",      getBasePath().url.asc()
    );
  }
}
