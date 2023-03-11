

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ImagePojo;
import com.huststore.jpa.entities.Image;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IImagesDataTransportJpaService
  extends IDataTransportJpaService<ImagePojo, Image> {
}
