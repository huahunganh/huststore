

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Image;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IImagesJpaRepository
    extends IJpaRepository<Image> {

  Optional<Image> findByFilename(String filename);
}
