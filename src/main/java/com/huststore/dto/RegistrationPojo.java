

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@JsonInclude
public class RegistrationPojo {
  @NotBlank
  private String name;
  @NotBlank
  private String password;
  @Valid
  private PersonPojo profile;
}
