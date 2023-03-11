

package com.huststore.jpa.services.conversion;

import com.huststore.dto.ImagePojo;
import com.huststore.jpa.entities.Image;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IImagesConverterJpaService
  extends ITwoWayConverterJpaService<ImagePojo, Image> {
}
