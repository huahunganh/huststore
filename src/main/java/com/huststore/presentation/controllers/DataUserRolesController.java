

package com.huststore.presentation.controllers;

import com.huststore.dto.DataPagePojo;
import com.huststore.dto.UserRolePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.UserRole;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.crud.IUserRolesCrudService;
import com.huststore.presentation.GenericDataCrudController;
import com.huststore.presentation.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/data/user_roles")
@PreAuthorize("isAuthenticated()")
public class DataUserRolesController
  extends GenericDataCrudController<UserRolePojo, UserRole> {

  @Autowired
  public DataUserRolesController(PaginationService paginationService,
                                 ISortSpecJpaService<UserRole> sortService,
                                 IUserRolesCrudService crudService,
                                 IPredicateJpaService<UserRole> predicateService) {
    super(paginationService, sortService, crudService, predicateService);
  }

  @Override
  @GetMapping({"", "/"})
  @PreAuthorize("hasAuthority('user_roles:read')")
  public DataPagePojo<UserRolePojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(allRequestParams);
  }

  @Override
  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('user_roles:create')")
  public void create(@Valid @RequestBody UserRolePojo input)
      throws BadInputException, EntityExistsException {
    super.create(input);
  }

  @Override
  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('user_roles:update')")
  public void update(@RequestBody UserRolePojo input, @RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    super.update(input, requestParams);
  }

  @Override
  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('user_roles:delete')")
  public void delete(@RequestParam Map<String, String> requestParams)
      throws EntityNotFoundException {
    super.delete(requestParams);
  }
}
