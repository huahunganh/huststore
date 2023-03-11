

package com.huststore.presentation.services;

import com.huststore.dto.ProductPojo;
import com.huststore.dto.ReceiptDetailPojo;
import com.huststore.dto.ReceiptPojo;
import com.huststore.jpa.entities.Product;
import com.huststore.jpa.entities.Sell;
import com.huststore.jpa.entities.SellDetail;
import com.huststore.jpa.repositories.ISalesJpaRepository;
import com.huststore.presentation.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptServiceImpl
  implements IReceiptService {

  private final ISalesJpaRepository salesRepository;
  private final ConversionService conversionService;

  @Autowired
  public ReceiptServiceImpl(ISalesJpaRepository salesRepository, ConversionService conversionService) {
    this.salesRepository = salesRepository;
    this.conversionService = conversionService;
  }

  @Override
  public ReceiptPojo fetchReceiptByTransactionToken(String token)
      throws EntityNotFoundException {
    Optional<Sell> match = salesRepository.findByTransactionToken(token);
    if (match.isEmpty()) {
      throw new EntityNotFoundException("The transaction could not be found, no receipt can be created");
    }
    Sell foundMatch = match.get();

    ReceiptPojo target = conversionService.convert(foundMatch, ReceiptPojo.class);

    if (target != null) {
      List<ReceiptDetailPojo> targetDetails = new ArrayList<>();
      for (SellDetail d : foundMatch.getDetails()) {
        ReceiptDetailPojo targetDetail = conversionService.convert(d, ReceiptDetailPojo.class);
        if (targetDetail != null) {
          Product pd = d.getProduct();
          ProductPojo targetDetailProduct = ProductPojo.builder().name(pd.getName()).barcode(pd.getBarcode()).build();
          targetDetail.setProduct(targetDetailProduct);
          targetDetail.setUnitValue(d.getUnitValue());
          targetDetails.add(targetDetail);
        }
      }
      target.setDetails(targetDetails);
      target.setStatus(foundMatch.getStatus().getName());
    }

    return target;
  }
}
