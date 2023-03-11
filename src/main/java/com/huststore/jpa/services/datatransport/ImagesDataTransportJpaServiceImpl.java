

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.ImagePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Image;
import org.springframework.stereotype.Service;

@Service
public class ImagesDataTransportJpaServiceImpl
  implements IImagesDataTransportJpaService {

  public ImagesDataTransportJpaServiceImpl(
  ) {
  }

  @Override
  public Image applyChangesToExistingEntity(ImagePojo source, Image existing) throws BadInputException {
    Image target = new Image(existing);

    String code = source.getCode();
    if (code != null && !code.isBlank() && !target.getCode().equals(code)) {
      target.setCode(code);
    }

    String filename = source.getFilename();
    if (filename != null && !filename.isBlank() && !target.getFilename().equals(filename)) {
      target.setFilename(filename);
    }

    String url = source.getUrl();
    if (url != null && !url.isBlank() && !target.getUrl().equals(url)) {
      target.setUrl(url);
    }

    return target;
  }
}
