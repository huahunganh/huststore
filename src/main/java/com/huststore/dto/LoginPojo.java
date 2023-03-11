

package com.huststore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginPojo {
  @NotBlank
  private String name;
  @NotBlank
  private String password;
}
