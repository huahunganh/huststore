

package com.huststore.jpa.services;

import com.huststore.exceptions.BadInputException;

/**
 * Type-safe interface for passing data from Pojos to Entities in order to
 * prepare these for submission to the persistence layer.
 * @param <P> The Pojo class
 * @param <E> The Entity class
 */
public interface IDataTransportJpaService<P, E> {

  /**
   * Creates a clone Entity, then updates it with new data from a Pojo,
   * setting differences in properties one-by-one, and returns it.
   * It does not include relationships to other entities.
   * @param source The Pojo containing data updates.
   * @param target The target entity.
   * @return An updated instance of the @Entity, prepared to be saved to the database.
   * @throws BadInputException If the object with changes has invalid values
   */
  E applyChangesToExistingEntity(P source, E target) throws BadInputException;
}
