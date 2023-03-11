

package com.huststore.converters.toentity;

import com.huststore.dto.ImagePojo;
import com.huststore.jpa.entities.Image;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Image2Entity
    implements Converter<ImagePojo, Image> {

  @Override
  public Image convert(ImagePojo source) {
    Image target = new Image();
    target.setCode(source.getCode());
    target.setFilename(source.getFilename());
    target.setUrl(source.getUrl());
    return target;
  }
}
