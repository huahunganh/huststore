

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@JsonInclude
public class SellStatusPojo {
  @NotBlank
  private Integer code;
  @JsonInclude(NON_EMPTY)
  @NotBlank
  private String name;
}
