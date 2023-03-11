

package com.huststore.jpa.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(
  name = "sales",
  indexes = {
    @Index(columnList = "sell_date"),
    @Index(columnList = "sell_transaction_token"),
  })
public class Sell
  implements Serializable {

  private static final long serialVersionUID = 14L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sell_id", nullable = false)
  private Long id;
  @Column(name = "sell_date", nullable = false)
  @CreationTimestamp
  private Instant date;
  @Column(name = "sell_total_items", nullable = false)
  private int totalItems;
  @Column(name = "sell_net_value", nullable = false)
  private int netValue;
  @Column(name = "sell_transport_value", nullable = false)
  private int transportValue;
  @Column(name = "sell_taxes_value", nullable = false)
  private int taxesValue;
  @Column(name = "sell_total_value", nullable = false)
  private int totalValue;
  @Size(min = 64, max = 64)
  @Column(name = "sell_transaction_token")
  private String transactionToken;
  @JoinColumn(name = "customer_id")
  @ManyToOne(optional = false, cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
  private Customer customer;
  @JoinColumn(name = "sell_status_id", updatable = false, nullable = false)
  @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private SellStatus status;
  @JoinColumn(name = "billing_address_id", updatable = false)
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
  private Address billingAddress;
  @JoinColumn(name = "shipper_id", updatable = false)
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private Shipper shipper;
  @JoinColumn(name = "shipping_address_id", updatable = false)
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
  private Address shippingAddress;
  @JoinColumn(name = "salesperson_id")
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
  private Salesperson salesperson;
  @JoinColumn(name = "sell_id", nullable = false)
  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Collection<SellDetail> details;

  public Sell() { }

  public Sell(Sell source) {
    this.id = source.id;
    this.date = source.date;
    this.totalItems = source.totalItems;
    this.netValue = source.netValue;
    this.transportValue = source.transportValue;
    this.taxesValue = source.taxesValue;
    this.totalValue = source.totalValue;
    this.transactionToken = source.transactionToken;
    this.customer = source.customer;
    this.status = source.status;
    this.billingAddress = source.billingAddress;
    this.shipper = source.shipper;
    this.shippingAddress = source.shippingAddress;
    this.salesperson = source.salesperson;
    this.details = source.details;
  }

  public Sell(Customer customer, Collection<SellDetail> details) {
    this.customer = customer;
    this.details = details;
  }

  public Sell(Long id,
              Instant date,
              int totalItems,
              int netValue,
              int transportValue,
              int taxesValue,
              int totalValue,
              String transactionToken,
              Customer customer,
              SellStatus status,
              Address billingAddress,
              Shipper shipper,
              Address shippingAddress,
              Salesperson salesperson,
              Collection<SellDetail> details) {
    this.id = id;
    this.date = date;
    this.totalItems = totalItems;
    this.netValue = netValue;
    this.transportValue = transportValue;
    this.taxesValue = taxesValue;
    this.totalValue = totalValue;
    this.transactionToken = transactionToken;
    this.customer = customer;
    this.billingAddress = billingAddress;
    this.shipper = shipper;
    this.shippingAddress = shippingAddress;
    this.salesperson = salesperson;
    this.details = details;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public int getNetValue() {
    return netValue;
  }

  public void setNetValue(int netValue) {
    this.netValue = netValue;
  }

  public int getTransportValue() {
    return transportValue;
  }

  public void setTransportValue(int transportValue) {
    this.transportValue = transportValue;
  }

  public int getTaxesValue() {
    return taxesValue;
  }

  public void setTaxesValue(int taxesValue) {
    this.taxesValue = taxesValue;
  }

  public int getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(int totalValue) {
    this.totalValue = totalValue;
  }

  public String getTransactionToken() {
    return transactionToken;
  }

  public void setTransactionToken(String transactionToken) {
    this.transactionToken = transactionToken;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public void setStatus(SellStatus status) {
    this.status = status;
  }

  public SellStatus getStatus() {
    return status;
  }


  public Shipper getShipper() {
    return shipper;
  }

  public void setShipper(Shipper shipper) {
    this.shipper = shipper;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public Salesperson getSalesperson() {
    return salesperson;
  }

  public void setSalesperson(Salesperson salesperson) {
    this.salesperson = salesperson;
  }

  public Collection<SellDetail> getDetails() {
    return details;
  }

  public void setDetails(Collection<SellDetail> details) {
    this.details = details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sell sell = (Sell) o;
    return totalItems == sell.totalItems &&
        netValue == sell.netValue &&
        transportValue == sell.transportValue &&
        taxesValue == sell.taxesValue &&
        totalValue == sell.totalValue &&
        Objects.equals(id, sell.id) &&
        Objects.equals(date, sell.date) &&
        Objects.equals(transactionToken, sell.transactionToken) &&
        Objects.equals(customer, sell.customer) &&
        Objects.equals(billingAddress, sell.billingAddress) &&
        Objects.equals(shipper, sell.shipper) &&
        Objects.equals(shippingAddress, sell.shippingAddress) &&
        Objects.equals(salesperson, sell.salesperson) &&
        Objects.equals(details, sell.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, totalItems, netValue, transportValue, taxesValue, totalValue, transactionToken,
        customer, billingAddress, shipper, shippingAddress,
        salesperson, details);
  }

  @Override
  public String toString() {
    return "Sell{" +
        "id=" + id +
        ", date=" + date +
        ", totalItems=" + totalItems +
        ", netValue=" + netValue +
        ", transportValue=" + transportValue +
        ", taxesValue=" + taxesValue +
        ", totalValue=" + totalValue +
        ", transactionToken='" + transactionToken + '\'' +
        ", customer=" + customer +
        ", billingAddress=" + billingAddress +
        ", shipper=" + shipper +
        ", shippingAddress=" + shippingAddress +
        ", salesperson=" + salesperson +
        ", details=" + details +
        '}';
  }
}
