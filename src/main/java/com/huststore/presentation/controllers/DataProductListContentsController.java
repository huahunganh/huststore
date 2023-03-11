

package com.huststore.presentation.controllers;

import com.huststore.dto.DataPagePojo;
import com.huststore.dto.ProductPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.*;
import com.huststore.jpa.repositories.IProductListItemsJpaRepository;
import com.huststore.jpa.repositories.IProductListsJpaRepository;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.ISortSpecJpaService;
import com.huststore.jpa.services.conversion.IProductListItemsConverterJpaService;
import com.huststore.jpa.services.crud.IProductsCrudService;
import com.huststore.presentation.PaginationService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/data/product_list_contents")
public class DataProductListContentsController {

  private static final String ITEM_NOT_FOUND = "Requested item(s) not found";

  private final PaginationService paginationService;
  private final ISortSpecJpaService<ProductListItem> sortService;
  private final IProductListItemsJpaRepository listItemsRepository;
  private final IProductListsJpaRepository listsRepository;
  private final IPredicateJpaService<ProductListItem> listItemsPredicateService;
  private final IProductsCrudService productCrudService;
  private final IProductListItemsConverterJpaService itemConverterService;

  @Autowired
  public DataProductListContentsController(PaginationService paginationService,
                                           ISortSpecJpaService<ProductListItem> sortService,
                                           IProductListItemsJpaRepository listItemsRepository,
                                           IProductListsJpaRepository listsRepository,
                                           IPredicateJpaService<ProductListItem> listItemsPredicateService,
                                           IProductsCrudService productCrudService,
                                           IProductListItemsConverterJpaService itemConverterService) {
    this.paginationService = paginationService;
    this.sortService = sortService;
    this.listItemsRepository = listItemsRepository;
    this.listsRepository = listsRepository;
    this.listItemsPredicateService = listItemsPredicateService;
    this.productCrudService = productCrudService;
    this.itemConverterService = itemConverterService;
  }

  @GetMapping({"", "/"})
  public DataPagePojo<ProductPojo> readContents(@RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    Optional<ProductList> match = this.fetchProductListByCode(requestParams);
    if (match.isEmpty()) {
      throw new EntityNotFoundException(ITEM_NOT_FOUND);
    }

    int pageIndex = paginationService.determineRequestedPageIndex(requestParams);
    int pageSize = paginationService.determineRequestedPageSize(requestParams);

    Pageable pagination;
    if (requestParams.containsKey("sortBy")) {
      Sort order = sortService.parseMap(requestParams);
      pagination = PageRequest.of(pageIndex, pageSize, order);
    } else {
      pagination = PageRequest.of(pageIndex, pageSize);
    }

    Predicate predicate = listItemsPredicateService.parseMap(requestParams);
    Page<ProductListItem> listItems = listItemsRepository.findAll(predicate, pagination);
    List<ProductPojo> products = new ArrayList<>();
    for (ProductListItem item : listItems) {
      ProductPojo productPojo = itemConverterService.convertToPojo(item);
      products.add(productPojo);
    }
    long totalCount = listItemsRepository.count(QProductListItem.productListItem.list.id.eq(match.get().getId()));

    return new DataPagePojo<>(products, pageIndex, totalCount, pageSize);
  }

  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('product_lists:contents')")
  public void addToContents(@Valid @RequestBody ProductPojo input,
                            @RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    Optional<ProductList> listMatch = this.fetchProductListByCode(requestParams);
    if (listMatch.isEmpty()) {
      throw new EntityNotFoundException(ITEM_NOT_FOUND);
    }

    Optional<Product> productMatch = productCrudService.getExisting(input);
    if (productMatch.isPresent()) {
      ProductListItem listItem = new ProductListItem(listMatch.get(), productMatch.get());
      if (!listItemsRepository.exists(Example.of(listItem))) {
        listItemsRepository.save(listItem);
      }
    }
  }

  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('product_lists:contents')")
  public void updateContents(@RequestBody Collection<ProductPojo> input,
                             @RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    Optional<ProductList> listMatch = this.fetchProductListByCode(requestParams);
    if (listMatch.isEmpty()) {
      throw new EntityNotFoundException(ITEM_NOT_FOUND);
    }

    listItemsRepository.deleteByListId(listMatch.get().getId());
    for (ProductPojo p : input) {
      Optional<Product> productMatch = productCrudService.getExisting(p);
      if (productMatch.isPresent()) {
        ProductListItem listItem = new ProductListItem(listMatch.get(), productMatch.get());
        if (!listItemsRepository.exists(Example.of(listItem))) {
          listItemsRepository.save(listItem);
        }
      }
    }
  }

  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('product_lists:contents')")
  public void deleteFromContents(@RequestParam Map<String, String> requestParams)
      throws BadInputException, EntityNotFoundException {
    Optional<ProductList> listMatch = this.fetchProductListByCode(requestParams);
    if (listMatch.isEmpty()) {
      throw new EntityNotFoundException(ITEM_NOT_FOUND);
    }

    Predicate predicate = listItemsPredicateService.parseMap(requestParams);
    listItemsRepository.deleteAll(listItemsRepository.findAll(predicate));
  }

  private Optional<ProductList> fetchProductListByCode(Map<String, String> requestParams) throws BadInputException {
    String listCode = requestParams.get("listCode");
    if (listCode == null || listCode.isBlank()) {
      throw new BadInputException("listCode query param is required");
    }
    return listsRepository.findOne(QProductList.productList.code.eq(listCode));
  }
}
