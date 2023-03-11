

package com.huststore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Positive;

@Configuration
@ConfigurationProperties(prefix = "huststore.operation")
public class OperationProperties {
  private Integer itemsPerPage;
  private Integer maxAllowedPageSize;
  @Positive
  private int maxCategoryFetchingRecursionDepth;

  public Integer getMaxAllowedPageSize() {
    return maxAllowedPageSize;
  }

  public void setMaxAllowedPageSize(Integer maxAllowedPageSize) {
    this.maxAllowedPageSize = maxAllowedPageSize;
  }

  public Integer getItemsPerPage() {
    return itemsPerPage;
  }

  public void setItemsPerPage(Integer itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
  }

  public int getMaxCategoryFetchingRecursionDepth() {
    return maxCategoryFetchingRecursionDepth;
  }

  public void setMaxCategoryFetchingRecursionDepth(int maxCategoryFetchingRecursionDepth) {
    this.maxCategoryFetchingRecursionDepth = maxCategoryFetchingRecursionDepth;
  }
}
