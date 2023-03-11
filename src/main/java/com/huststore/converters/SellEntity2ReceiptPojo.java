

package com.huststore.converters;

import com.huststore.dto.ReceiptPojo;
import com.huststore.jpa.entities.Sell;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SellEntity2ReceiptPojo
    implements Converter<Sell, ReceiptPojo> {

  @Override
  public ReceiptPojo convert(Sell source) {
    ReceiptPojo target = new ReceiptPojo();
    target.setBuyOrder(source.getId());
    target.setDate(source.getDate());
    target.setTransportValue(source.getTransportValue());
    target.setTaxValue(source.getTaxesValue());
    target.setTotalItems(source.getTotalItems());
    target.setTotalValue(source.getTotalValue());
    target.setToken(source.getTransactionToken());
    return target;
  }
}
