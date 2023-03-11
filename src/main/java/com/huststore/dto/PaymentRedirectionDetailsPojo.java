

package com.huststore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Wrapper class for data needed to redirect towards payment page
 */
@Data
@Builder
@JsonInclude
public class PaymentRedirectionDetailsPojo {
  private String url;
  private String token;
}
