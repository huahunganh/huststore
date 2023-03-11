

package com.huststore.presentation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

  @GetMapping({"", "/"})
  public void defaultMapping() {
    /* ping-like method. return success status without message body */
  }
}
