

package com.huststore.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sell_details")
public class SellDetail
  implements Serializable {

  private static final long serialVersionUID = 15L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sell_detail_id", nullable = false)
  private Long id;
  @Column(name = "sell_detail_units", nullable = false)
  private int units;
  @Column(name = "sell_detail_unit_value", nullable = false)
  private Integer unitValue;
  @Column(name = "sell_detail_description", nullable = false)
  @Size(max = 260)
  private String description;
  @JoinColumn(name = "product_id", referencedColumnName = "product_id", updatable = false,
      foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @ManyToOne(optional = false)
  private Product product;
  @JoinColumn(name = "sell_id", referencedColumnName = "sell_id", insertable = false, updatable = false,
      foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Sell sell;

  public SellDetail() { }

  public SellDetail(SellDetail source) {
    this.id = source.id;
    this.units = source.units;
    this.unitValue = source.unitValue;
    this.description = source.description;
    this.product = source.product;
    this.sell = source.sell;
  }

  public SellDetail(int units, Product product) {
    this.units = units;
    this.product = product;
    this.unitValue = product.getPrice();
    this.description = String.format("%o x %s [%s] | Unit Val: %d",
                                     units, product.getName(), product.getBarcode(), product.getPrice());
  }

  public SellDetail(Long id, int units, Integer unitValue, Product product) {
    this.id = id;
    this.units = units;
    this.unitValue = unitValue;
    this.product = product;
    this.description = String.format("%o x %s [%s] | Unit Val: %d",
                                     units, product.getName(), product.getBarcode(), product.getPrice());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getUnits() {
    return units;
  }

  public void setUnits(int units) {
    this.units = units;
  }

  public Integer getUnitValue() {
    return unitValue;
  }

  public void setUnitValue(Integer unitValue) {
    this.unitValue = unitValue;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Sell getSell() {
    return sell;
  }

  public void setSell(Sell sell) {
    this.sell = sell;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SellDetail that = (SellDetail) o;
    return units == that.units &&
        Objects.equals(id, that.id) &&
        Objects.equals(unitValue, that.unitValue) &&
        Objects.equals(product, that.product) &&
        Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, units, unitValue, product, description);
  }

  @Override
  public String toString() {
    return "SellDetail{" +
        "id=" + id +
        ", units=" + units +
        ", unitValue=" + unitValue +
        ", product=" + product +
        ", description=" + description +
        '}';
  }
}
