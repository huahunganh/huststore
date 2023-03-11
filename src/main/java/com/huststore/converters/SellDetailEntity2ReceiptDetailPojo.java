

package com.huststore.converters;

import com.huststore.dto.ReceiptDetailPojo;
import com.huststore.jpa.entities.SellDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SellDetailEntity2ReceiptDetailPojo
    implements Converter<SellDetail, ReceiptDetailPojo> {

  @Override
  public ReceiptDetailPojo convert(SellDetail source) {
    ReceiptDetailPojo target = new ReceiptDetailPojo();
    target.setUnits(source.getUnits());
    target.setDescription(source.getDescription());
    return target;
  }
}
