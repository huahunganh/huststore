

package com.huststore.presentation.controllers;

import com.huststore.dto.DataPagePojo;
import com.huststore.dto.ShipperPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Shipper;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.crud.IShippersCrudService;
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
@RequestMapping("/data/shippers")
public class DataShippersController
  extends GenericDataCrudController<ShipperPojo, Shipper> {

  @Autowired
  public DataShippersController(PaginationService paginationService,
                                ISortSpecJpaService<Shipper> sortService,
                                IShippersCrudService crudService,
                                IPredicateJpaService<Shipper> predicateService) {
    super(paginationService, sortService, crudService, predicateService);
  }

  @Override
  @GetMapping({"", "/"})
  public DataPagePojo<ShipperPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(allRequestParams);
  }

  @Override
  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('shippers:create')")
  public void create(@Valid @RequestBody ShipperPojo input)
      throws BadInputException, EntityExistsException {
    super.create(input);
  }

  @Override
  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('shippers:update')")
  public void update(@RequestBody ShipperPojo input, @RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    super.update(input, requestParams);
  }

  @Override
  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('shippers:delete')")
  public void delete(@RequestParam Map<String, String> requestParams)
      throws EntityNotFoundException {
    super.delete(requestParams);
  }
}
