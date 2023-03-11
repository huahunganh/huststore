

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class UserPojo {
  @JsonIgnore
  private Long id;
  @NotBlank
  @JsonInclude
  private String name;
  @NotBlank
  private String password;
  private PersonPojo person;
  private String role;
}
