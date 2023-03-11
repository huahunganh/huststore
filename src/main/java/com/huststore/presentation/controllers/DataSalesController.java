

package com.huststore.presentation.controllers;

import com.huststore.dto.DataPagePojo;
import com.huststore.dto.SellPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.integration.IMailingIntegrationService;
import com.huststore.integration.exceptions.MailingServiceException;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.crud.ISalesCrudService;
import com.huststore.presentation.GenericDataCrudController;
import com.huststore.presentation.ISalesProcessService;
import com.huststore.presentation.PaginationService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data/sales")
@PreAuthorize("isAuthenticated()")
public class DataSalesController
  extends GenericDataCrudController<SellPojo, Sell> {

  private final ISalesProcessService processService;
  private final IMailingIntegrationService mailingIntegrationService;

  @Autowired
  public DataSalesController(PaginationService paginationService,
                             ISortSpecJpaService<Sell> sortService,
                             ISalesCrudService crudService,
                             IPredicateJpaService<Sell> predicateService,
                             ISalesProcessService processService,
                             @Autowired(required = false) IMailingIntegrationService mailingIntegrationService) {
    super(paginationService, sortService, crudService, predicateService);
    this.processService = processService;
    this.mailingIntegrationService = mailingIntegrationService;
  }

  @Override
  @GetMapping({"", "/"})
  @PreAuthorize("hasAuthority('sales:read')")
  public DataPagePojo<SellPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    if (allRequestParams.containsKey("buyOrder")) {
      Predicate predicate = predicateService.parseMap(allRequestParams);
      SellPojo sellPojo = crudService.readOne(predicate);
      return new DataPagePojo<>(List.of(sellPojo), 0, 1, 1);
    }
    if (!allRequestParams.containsKey("sortBy") && !allRequestParams.containsKey("order")) {
      allRequestParams.put("sortBy", "buyOrder");
      allRequestParams.put("order", "desc");
    }
    return super.readMany(allRequestParams);
  }

  @Override
  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('sales:create')")
  public void create(@Valid @RequestBody SellPojo input)
      throws BadInputException, EntityExistsException {
    super.create(input);
  }

  @Override
  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('sales:update')")
  public void update(@RequestBody SellPojo input, @RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    super.update(input, requestParams);
  }

  @Override
  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('sales:delete')")
  public void delete(@RequestParam Map<String, String> requestParams)
      throws EntityNotFoundException {
    super.delete(requestParams);
  }

  @PostMapping({"/confirmation", "/confirmation/"})
  @PreAuthorize("hasAuthority('sales:update')")
  public void confirmSell(@RequestBody SellPojo sell)
      throws BadInputException, MailingServiceException {
    SellPojo updatedSell = processService.markAsConfirmed(sell);
    if (this.mailingIntegrationService != null) {
      mailingIntegrationService.notifyOrderStatusToClient(updatedSell);
      mailingIntegrationService.notifyOrderStatusToOwners(updatedSell);
    }
  }

  @PostMapping({"/rejection", "/rejection/"})
  @PreAuthorize("hasAuthority('sales:update')")
  public void rejectSell(@RequestBody SellPojo sell)
      throws BadInputException, MailingServiceException {
    SellPojo updatedSell = processService.markAsRejected(sell);
    if (this.mailingIntegrationService != null) {
      mailingIntegrationService.notifyOrderStatusToClient(updatedSell);
    }
  }

  @PostMapping({"/completion", "/completion/"})
  @PreAuthorize("hasAuthority('sales:update')")
  public void completeSell(@RequestBody SellPojo sell)
      throws BadInputException, MailingServiceException {
    SellPojo updatedSell = processService.markAsCompleted(sell);
    if (this.mailingIntegrationService != null) {
      mailingIntegrationService.notifyOrderStatusToClient(updatedSell);
    }
  }
}
