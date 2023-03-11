

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@JsonInclude
public class ShipperPojo {
  @JsonIgnore
  private Long id;
  @NotBlank
  private String name;
}
