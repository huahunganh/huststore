

package com.huststore.presentation.controllers;

import com.huststore.dto.CompanyDetailsPojo;
import com.huststore.presentation.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/about")
public class PublicAboutController {

  private final ICompanyService companyService;

  @Autowired
  public PublicAboutController(ICompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping({"", "/"})
  public CompanyDetailsPojo readCompanyDetails() {
    CompanyDetailsPojo companyDetailsPojo = companyService.readDetails();
    return companyDetailsPojo;
  }
}
