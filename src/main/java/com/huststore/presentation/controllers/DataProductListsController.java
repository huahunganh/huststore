

package com.huststore.presentation.controllers;

import com.huststore.dto.DataPagePojo;
import com.huststore.dto.ProductListPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.ProductList;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.crud.IProductListCrudService;
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
@RequestMapping("/data/product_lists")
public class DataProductListsController
  extends GenericDataCrudController<ProductListPojo, ProductList> {

  @Autowired
  public DataProductListsController(PaginationService paginationService,
                                    ISortSpecJpaService<ProductList> sortService,
                                    IProductListCrudService crudService,
                                    IPredicateJpaService<ProductList> predicateService) {
    super(paginationService, sortService, crudService, predicateService);
  }

  @Override
  @GetMapping({"", "/"})
  public DataPagePojo<ProductListPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(allRequestParams);
  }

  @Override
  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('product_lists:create')")
  public void create(@Valid @RequestBody ProductListPojo input)
      throws BadInputException, EntityExistsException {
    super.create(input);
  }

  @Override
  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('product_lists:update')")
  public void update(@RequestBody ProductListPojo input, @RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    super.update(input, requestParams);
  }

  @Override
  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('product_lists:delete')")
  public void delete(@RequestParam Map<String, String> requestParams)
      throws EntityNotFoundException {
    super.delete(requestParams);
  }
}
