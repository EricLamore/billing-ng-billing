package com.universign.universigncs.billing.domain;

import com.universign.universigncs.billing.domain.enumeration.ItemType;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A InvoiceItem.
 */
@Document(collection = "invoice_item")
public class InvoiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("item_type")
    private ItemType itemType;

    @Field("min_step")
    private Integer minStep;

    @Field("max_step")
    private Integer maxStep;

    @Field("quantity")
    private Double quantity;

    @Field("unit_price")
    private Double unitPrice;

    @Field("discount")
    private Double discount;

    @Field("price")
    private Double price;

    @Field("product")
    private String product;

    @Field("global_quantity")
    private Integer globalQuantity;

    @Field("until_date")
    private LocalDate untilDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public InvoiceItem id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public InvoiceItem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public InvoiceItem itemType(ItemType itemType) {
        this.setItemType(itemType);
        return this;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Integer getMinStep() {
        return this.minStep;
    }

    public InvoiceItem minStep(Integer minStep) {
        this.setMinStep(minStep);
        return this;
    }

    public void setMinStep(Integer minStep) {
        this.minStep = minStep;
    }

    public Integer getMaxStep() {
        return this.maxStep;
    }

    public InvoiceItem maxStep(Integer maxStep) {
        this.setMaxStep(maxStep);
        return this;
    }

    public void setMaxStep(Integer maxStep) {
        this.maxStep = maxStep;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public InvoiceItem quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public InvoiceItem unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public InvoiceItem discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return this.price;
    }

    public InvoiceItem price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProduct() {
        return this.product;
    }

    public InvoiceItem product(String product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getGlobalQuantity() {
        return this.globalQuantity;
    }

    public InvoiceItem globalQuantity(Integer globalQuantity) {
        this.setGlobalQuantity(globalQuantity);
        return this;
    }

    public void setGlobalQuantity(Integer globalQuantity) {
        this.globalQuantity = globalQuantity;
    }

    public LocalDate getUntilDate() {
        return this.untilDate;
    }

    public InvoiceItem untilDate(LocalDate untilDate) {
        this.setUntilDate(untilDate);
        return this;
    }

    public void setUntilDate(LocalDate untilDate) {
        this.untilDate = untilDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceItem)) {
            return false;
        }
        return id != null && id.equals(((InvoiceItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", minStep=" + getMinStep() +
            ", maxStep=" + getMaxStep() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", discount=" + getDiscount() +
            ", price=" + getPrice() +
            ", product='" + getProduct() + "'" +
            ", globalQuantity=" + getGlobalQuantity() +
            ", untilDate='" + getUntilDate() + "'" +
            "}";
    }
}
