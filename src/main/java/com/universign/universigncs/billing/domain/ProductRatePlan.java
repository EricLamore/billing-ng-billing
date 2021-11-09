package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ProductRatePlan.
 */
@Document(collection = "product_rate_plan")
public class ProductRatePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("commercial_name")
    private String commercialName;

    @Field("base")
    private Double base;

    @Field("product")
    private String product;

    @Field("product_rate_plan_charge")
    private String productRatePlanCharge;

    @Field("features")
    private String features;

    @Field("version")
    private String version;

    @Field("fixed_price")
    private Boolean fixedPrice;

    @Field("standard_model")
    private Boolean standardModel;

    @Field("units_included")
    private Integer unitsIncluded;

    @Field("units_included_price")
    private Double unitsIncludedPrice;

    @Field("units_included_duration")
    private Integer unitsIncludedDuration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProductRatePlan id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommercialName() {
        return this.commercialName;
    }

    public ProductRatePlan commercialName(String commercialName) {
        this.setCommercialName(commercialName);
        return this;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public Double getBase() {
        return this.base;
    }

    public ProductRatePlan base(Double base) {
        this.setBase(base);
        return this;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public String getProduct() {
        return this.product;
    }

    public ProductRatePlan product(String product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductRatePlanCharge() {
        return this.productRatePlanCharge;
    }

    public ProductRatePlan productRatePlanCharge(String productRatePlanCharge) {
        this.setProductRatePlanCharge(productRatePlanCharge);
        return this;
    }

    public void setProductRatePlanCharge(String productRatePlanCharge) {
        this.productRatePlanCharge = productRatePlanCharge;
    }

    public String getFeatures() {
        return this.features;
    }

    public ProductRatePlan features(String features) {
        this.setFeatures(features);
        return this;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getVersion() {
        return this.version;
    }

    public ProductRatePlan version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getFixedPrice() {
        return this.fixedPrice;
    }

    public ProductRatePlan fixedPrice(Boolean fixedPrice) {
        this.setFixedPrice(fixedPrice);
        return this;
    }

    public void setFixedPrice(Boolean fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Boolean getStandardModel() {
        return this.standardModel;
    }

    public ProductRatePlan standardModel(Boolean standardModel) {
        this.setStandardModel(standardModel);
        return this;
    }

    public void setStandardModel(Boolean standardModel) {
        this.standardModel = standardModel;
    }

    public Integer getUnitsIncluded() {
        return this.unitsIncluded;
    }

    public ProductRatePlan unitsIncluded(Integer unitsIncluded) {
        this.setUnitsIncluded(unitsIncluded);
        return this;
    }

    public void setUnitsIncluded(Integer unitsIncluded) {
        this.unitsIncluded = unitsIncluded;
    }

    public Double getUnitsIncludedPrice() {
        return this.unitsIncludedPrice;
    }

    public ProductRatePlan unitsIncludedPrice(Double unitsIncludedPrice) {
        this.setUnitsIncludedPrice(unitsIncludedPrice);
        return this;
    }

    public void setUnitsIncludedPrice(Double unitsIncludedPrice) {
        this.unitsIncludedPrice = unitsIncludedPrice;
    }

    public Integer getUnitsIncludedDuration() {
        return this.unitsIncludedDuration;
    }

    public ProductRatePlan unitsIncludedDuration(Integer unitsIncludedDuration) {
        this.setUnitsIncludedDuration(unitsIncludedDuration);
        return this;
    }

    public void setUnitsIncludedDuration(Integer unitsIncludedDuration) {
        this.unitsIncludedDuration = unitsIncludedDuration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductRatePlan)) {
            return false;
        }
        return id != null && id.equals(((ProductRatePlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductRatePlan{" +
            "id=" + getId() +
            ", commercialName='" + getCommercialName() + "'" +
            ", base=" + getBase() +
            ", product='" + getProduct() + "'" +
            ", productRatePlanCharge='" + getProductRatePlanCharge() + "'" +
            ", features='" + getFeatures() + "'" +
            ", version='" + getVersion() + "'" +
            ", fixedPrice='" + getFixedPrice() + "'" +
            ", standardModel='" + getStandardModel() + "'" +
            ", unitsIncluded=" + getUnitsIncluded() +
            ", unitsIncludedPrice=" + getUnitsIncludedPrice() +
            ", unitsIncludedDuration=" + getUnitsIncludedDuration() +
            "}";
    }
}
