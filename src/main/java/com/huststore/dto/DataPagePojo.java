

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple object that represents a page of data for a specific type
 * @param <T> The type of data in the page
 */
@Data
@JsonInclude
public class DataPagePojo<T> {
  private Collection<T> items;
  private int pageIndex;
  private long totalCount;
  private int pageSize;

  public DataPagePojo(int pageIndex, int pageSize) {
    this.items = new ArrayList<>();
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
  }

  public DataPagePojo(Collection<T> items, int pageIndex, long totalCount, int pageSize) {
    this.items = items;
    this.pageIndex = pageIndex;
    this.totalCount = totalCount;
    this.pageSize = pageSize;
  }
}
