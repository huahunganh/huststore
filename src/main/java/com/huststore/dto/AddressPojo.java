

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@JsonInclude(NON_EMPTY)
public class AddressPojo {
  @NotBlank
  private String firstLine;
  private String secondLine;
  @NotBlank
  private String municipality;
  @NotBlank
  private String city;
  private String postalCode;
  private String notes;
}
