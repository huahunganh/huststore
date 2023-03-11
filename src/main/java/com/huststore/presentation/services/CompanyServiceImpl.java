

package com.huststore.presentation.services;

import com.huststore.dto.CompanyDetailsPojo;
import com.huststore.jpa.entities.Param;
import com.huststore.jpa.repositories.IParamsJpaRepository;
import com.huststore.presentation.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl
    implements ICompanyService {

  private final IParamsJpaRepository paramsRepository;

  @Autowired
  public CompanyServiceImpl(IParamsJpaRepository paramsRepository) {
    this.paramsRepository = paramsRepository;
  }

  @Override
  public CompanyDetailsPojo readDetails() {
    Iterable<Param> it = paramsRepository.findParamsByCategory("company");
    CompanyDetailsPojo target = new CompanyDetailsPojo();
    for (Param p : it) {
      String v = p.getValue();
      switch (p.getName()) {
        case "name":
          target.setName(v);
          break;
        case "description":
          target.setDescription(v);
          break;
        case "bannerImageURL":
          target.setBannerImageURL(v);
          break;
        case "logoImageURL":
          target.setLogoImageURL(v);
          break;
        default:
          break;
      }
    }
    return target;
  }

}
