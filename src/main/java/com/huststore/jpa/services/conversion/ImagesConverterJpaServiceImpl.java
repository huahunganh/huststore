

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ImagePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesConverterJpaServiceImpl
  implements IImagesConverterJpaService {

  @Autowired
  public ImagesConverterJpaServiceImpl() {
  }

  @Override
  public ImagePojo convertToPojo(Image source) {
    return ImagePojo.builder()
      .id(source.getId())
      .code(source.getCode())
      .filename(source.getFilename())
      .url(source.getUrl())
      .build();
  }

  @Override
  public Image convertToNewEntity(ImagePojo source) {
    Image target = new Image();
    target.setCode(source.getCode());
    target.setFilename(source.getFilename());
    target.setUrl(source.getUrl());
    return target;
  }

  @Override
  public Image applyChangesToExistingEntity(ImagePojo source, Image target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
