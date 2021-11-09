package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ProductRatePlanCharge.
 */
@Document(collection = "product_rate_plan_charge")
public class ProductRatePlanCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("step")
    private Integer step;

    @Field("unit_price")
    private Double unitPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProductRatePlanCharge id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStep() {
        return this.step;
    }

    public ProductRatePlanCharge step(Integer step) {
        this.setStep(step);
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public ProductRatePlanCharge unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductRatePlanCharge)) {
            return false;
        }
        return id != null && id.equals(((ProductRatePlanCharge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductRatePlanCharge{" +
            "id=" + getId() +
            ", step=" + getStep() +
            ", unitPrice=" + getUnitPrice() +
            "}";
    }
}
