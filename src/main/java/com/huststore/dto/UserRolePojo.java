

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude
public class UserRolePojo {
  @JsonIgnore
  private Long id;
  @NotNull
  private String name;
}
