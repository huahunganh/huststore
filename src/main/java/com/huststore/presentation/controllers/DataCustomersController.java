

package com.huststore.presentation.controllers;

import com.huststore.dto.CustomerPojo;
import com.huststore.dto.DataPagePojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Customer;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.crud.ICustomersCrudService;
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
@RequestMapping("/data/customers")
@PreAuthorize("isAuthenticated()")
public class DataCustomersController
  extends GenericDataCrudController<CustomerPojo, Customer> {

  @Autowired
  public DataCustomersController(PaginationService paginationService,
                                 ISortSpecJpaService<Customer> sortService,
                                 ICustomersCrudService crudService,
                                 IPredicateJpaService<Customer> predicateService) {
    super(paginationService, sortService, crudService, predicateService);
    this.sortService = sortService;
  }

  @Override
  @GetMapping({"", "/"})
  @PreAuthorize("hasAuthority('customers:read')")
  public DataPagePojo<CustomerPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(allRequestParams);
  }

  @Override
  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('customers:create')")
  public void create(@Valid @RequestBody CustomerPojo input)
      throws BadInputException, EntityExistsException {
    super.create(input);
  }

  @Override
  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('customers:update')")
  public void update(@RequestBody CustomerPojo input, @RequestParam Map<String, String> requestParams)
      throws EntityNotFoundException, BadInputException {
    super.update(input, requestParams);
  }

  @Override
  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('customers:delete')")
  public void delete(Map<String, String> requestParams)
      throws EntityNotFoundException {
    super.delete(requestParams);
  }
}
