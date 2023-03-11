

package com.huststore.converters.topojo;

import com.huststore.dto.AddressPojo;
import com.huststore.jpa.entities.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Address2Pojo
    implements Converter<Address, AddressPojo> {

  @Override
  public AddressPojo convert(Address source) {
    AddressPojo target = AddressPojo.builder()
      .city(source.getCity())
      .municipality(source.getMunicipality())
      .firstLine(source.getFirstLine())
      .build();
    if (source.getSecondLine() != null && !source.getSecondLine().isBlank()) {
      target.setSecondLine(source.getSecondLine());
    }
    if (source.getPostalCode() != null && !source.getPostalCode().isBlank()) {
      target.setPostalCode(source.getPostalCode());
    }
    if (source.getNotes() != null && !source.getNotes().isBlank()) {
      target.setNotes(source.getNotes());
    }
    return target;
  }
}
