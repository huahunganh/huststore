

package com.huststore.presentation;

import com.huststore.config.OperationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service that reads pagination params from String-to-String <i>Map</i>s that represent HTTP query params.
 */
@Service
public class PaginationService {

  protected final OperationProperties operationProperties;

  @Autowired
  public PaginationService(OperationProperties operationProperties) {
    this.operationProperties = operationProperties;
  }

  public int determineRequestedPageIndex(Map<String, String> requestParams)
      throws NumberFormatException {
    if (requestParams == null || !requestParams.containsKey("pageIndex")) {
      return 0;
    }
    return Integer.parseInt(requestParams.get("pageIndex"));
  }

  public int determineRequestedPageSize(Map<String, String> requestParams)
      throws NumberFormatException {
    if (requestParams == null || !requestParams.containsKey("pageSize")) {
      return operationProperties.getItemsPerPage();
    }
    int pageSize = Integer.parseInt(requestParams.get("pageSize"));
    Integer maxAllowedPageSize = operationProperties.getMaxAllowedPageSize();
    return (pageSize < maxAllowedPageSize) ?
            pageSize :
            maxAllowedPageSize;
  }
}
