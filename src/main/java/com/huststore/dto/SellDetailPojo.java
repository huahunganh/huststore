

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude
public class SellDetailPojo {
  @JsonIgnore
  private Long id;
  @Min(1)
  private int units;
  private int unitValue;
  private String description;
  @NotNull
  private ProductPojo product;
}
