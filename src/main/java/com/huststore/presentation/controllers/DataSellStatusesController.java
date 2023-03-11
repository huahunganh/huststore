

package com.huststore.presentation.controllers;

import com.huststore.dto.DataPagePojo;
import com.huststore.dto.SellStatusPojo;
import com.huststore.jpa.entities.SellStatus;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.crud.ISellStatusesCrudService;
import com.huststore.presentation.GenericDataController;
import com.huststore.presentation.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/data/sell_statuses")
@PreAuthorize("isAuthenticated()")
public class DataSellStatusesController
  extends GenericDataController<SellStatusPojo, SellStatus> {

  @Autowired
  public DataSellStatusesController(PaginationService paginationService,
                                    ISortSpecJpaService<SellStatus> sortService,
                                    ISellStatusesCrudService crudService,
                                    IPredicateJpaService<SellStatus> predicateService) {
    super(paginationService, sortService, crudService, predicateService);
  }

  @Override
  @GetMapping({"", "/"})
  @PreAuthorize("hasAuthority('sell_statuses:read')")
  public DataPagePojo<SellStatusPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(allRequestParams);
  }
}
