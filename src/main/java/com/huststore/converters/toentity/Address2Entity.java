

package com.huststore.converters.toentity;

import com.huststore.dto.AddressPojo;
import com.huststore.jpa.entities.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Address2Entity
    implements Converter<AddressPojo, Address> {

  @Override
  public Address convert(AddressPojo source) {
    Address target = new Address();
    target.setFirstLine(source.getFirstLine());
    target.setCity(source.getCity());
    target.setMunicipality(source.getMunicipality());
    if (source.getPostalCode() != null) {
      target.setPostalCode(source.getPostalCode());
    }
    if (source.getNotes()!= null) {
      target.setNotes(source.getNotes());
    }
    return target;
  }
}
