

package com.huststore.jpa.repositories;

import com.huststore.jpa.IJpaRepository;
import com.huststore.jpa.entities.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionsJpaRepository
    extends IJpaRepository<Permission> {

}
