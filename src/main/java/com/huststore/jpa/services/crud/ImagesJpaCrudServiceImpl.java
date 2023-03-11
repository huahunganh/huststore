

package com.huststore.jpa.services.crud;

import com.huststore.dto.ImagePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Image;
import com.huststore.jpa.repositories.IImagesJpaRepository;
import com.huststore.jpa.services.GenericCrudJpaService;
import com.huststore.jpa.services.conversion.IImagesConverterJpaService;
import com.huststore.jpa.services.datatransport.IImagesDataTransportJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ImagesJpaCrudServiceImpl
  extends GenericCrudJpaService<ImagePojo, Image>
  implements IImagesCrudService {

  private final IImagesJpaRepository imagesRepository;

  @Autowired
  public ImagesJpaCrudServiceImpl(IImagesJpaRepository repository,
                                  IImagesConverterJpaService converter,
                                  IImagesDataTransportJpaService dataTransportService) {
    super(repository,
          converter,
          dataTransportService);
    this.imagesRepository = repository;
  }

  @Override
  public Optional<Image> getExisting(ImagePojo input) throws BadInputException {
    String name = input.getFilename();
    if (name == null || name.isBlank()) {
      throw new BadInputException("Invalid filename");
    } else {
      return imagesRepository.findByFilename(name);
    }
  }
}
