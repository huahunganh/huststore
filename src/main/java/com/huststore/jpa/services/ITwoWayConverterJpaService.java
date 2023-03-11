

package com.huststore.jpa.services;

import com.huststore.exceptions.BadInputException;

/**
 * Type-safe interface for converting Entities to Pojos and viceversa
 * @param <P> The Pojo class
 * @param <E> The Entity class
 */
public interface ITwoWayConverterJpaService<P, E> {

  /**
   * Straightly converts a Pojo to a new @Entity, assuming that the Pojo is already @Valid.
   * It does not include relationships to other entities.
   * This method DOES NOT persist data.
   * @param source The source Pojo.
   * @return A new entity, prepared to be saved to the database.
   * @throws BadInputException If the source object does not include required data or has invalid values
   */
  E convertToNewEntity(P source) throws BadInputException;

  /**
   * Creates a clone @Entity, then updates it with new data from a Pojo class,
   * putting differences in properties one-by-one. It does not include relationships to other entities.
   * This method DOES NOT persist data.
   * @param source The Pojo containing data updates.
   * @param target The target entity.
   * @return An updated instance of the @Entity, prepared to be saved to the database.
   * @throws BadInputException If the object with changes has invalid values
   * @deprecated Please use {@link IDataTransportJpaService}'s method instead
   */
  @Deprecated
  E applyChangesToExistingEntity(P source, E target) throws BadInputException;

   /**
   * Converts an existing @Entity to its complete Pojo equivalent, including all relationships to other entities.
   * @param source The source @Entity.
   * @return The resulting Pojo, or null if the @Entity isn't persistent
   */
  P convertToPojo(E source);
}
