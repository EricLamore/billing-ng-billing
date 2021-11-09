package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RatePlan.
 */
@Document(collection = "rate_plan")
public class RatePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("last_update")
    private LocalDate lastUpdate;

    @Field("description")
    private String description;

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

    @Field("rate_plan_id")
    private String ratePlanId;

    @Field("discount_unit_price")
    private Double discountUnitPrice;

    @Field("discount_base")
    private Double discountBase;

    @Field("prorata")
    private Double prorata;

    @Field("activation_date")
    private LocalDate activationDate;

    @Field("end_date")
    private LocalDate endDate;

    @Field("rate_plan_charges")
    private String ratePlanCharges;

    @Field("subscription_features")
    private String subscriptionFeatures;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public RatePlan id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RatePlan name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdate() {
        return this.lastUpdate;
    }

    public RatePlan lastUpdate(LocalDate lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDescription() {
        return this.description;
    }

    public RatePlan description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommercialName() {
        return this.commercialName;
    }

    public RatePlan commercialName(String commercialName) {
        this.setCommercialName(commercialName);
        return this;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public Double getBase() {
        return this.base;
    }

    public RatePlan base(Double base) {
        this.setBase(base);
        return this;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public String getProduct() {
        return this.product;
    }

    public RatePlan product(String product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductRatePlanCharge() {
        return this.productRatePlanCharge;
    }

    public RatePlan productRatePlanCharge(String productRatePlanCharge) {
        this.setProductRatePlanCharge(productRatePlanCharge);
        return this;
    }

    public void setProductRatePlanCharge(String productRatePlanCharge) {
        this.productRatePlanCharge = productRatePlanCharge;
    }

    public String getFeatures() {
        return this.features;
    }

    public RatePlan features(String features) {
        this.setFeatures(features);
        return this;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getVersion() {
        return this.version;
    }

    public RatePlan version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getFixedPrice() {
        return this.fixedPrice;
    }

    public RatePlan fixedPrice(Boolean fixedPrice) {
        this.setFixedPrice(fixedPrice);
        return this;
    }

    public void setFixedPrice(Boolean fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Boolean getStandardModel() {
        return this.standardModel;
    }

    public RatePlan standardModel(Boolean standardModel) {
        this.setStandardModel(standardModel);
        return this;
    }

    public void setStandardModel(Boolean standardModel) {
        this.standardModel = standardModel;
    }

    public Integer getUnitsIncluded() {
        return this.unitsIncluded;
    }

    public RatePlan unitsIncluded(Integer unitsIncluded) {
        this.setUnitsIncluded(unitsIncluded);
        return this;
    }

    public void setUnitsIncluded(Integer unitsIncluded) {
        this.unitsIncluded = unitsIncluded;
    }

    public Double getUnitsIncludedPrice() {
        return this.unitsIncludedPrice;
    }

    public RatePlan unitsIncludedPrice(Double unitsIncludedPrice) {
        this.setUnitsIncludedPrice(unitsIncludedPrice);
        return this;
    }

    public void setUnitsIncludedPrice(Double unitsIncludedPrice) {
        this.unitsIncludedPrice = unitsIncludedPrice;
    }

    public Integer getUnitsIncludedDuration() {
        return this.unitsIncludedDuration;
    }

    public RatePlan unitsIncludedDuration(Integer unitsIncludedDuration) {
        this.setUnitsIncludedDuration(unitsIncludedDuration);
        return this;
    }

    public void setUnitsIncludedDuration(Integer unitsIncludedDuration) {
        this.unitsIncludedDuration = unitsIncludedDuration;
    }

    public String getRatePlanId() {
        return this.ratePlanId;
    }

    public RatePlan ratePlanId(String ratePlanId) {
        this.setRatePlanId(ratePlanId);
        return this;
    }

    public void setRatePlanId(String ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public Double getDiscountUnitPrice() {
        return this.discountUnitPrice;
    }

    public RatePlan discountUnitPrice(Double discountUnitPrice) {
        this.setDiscountUnitPrice(discountUnitPrice);
        return this;
    }

    public void setDiscountUnitPrice(Double discountUnitPrice) {
        this.discountUnitPrice = discountUnitPrice;
    }

    public Double getDiscountBase() {
        return this.discountBase;
    }

    public RatePlan discountBase(Double discountBase) {
        this.setDiscountBase(discountBase);
        return this;
    }

    public void setDiscountBase(Double discountBase) {
        this.discountBase = discountBase;
    }

    public Double getProrata() {
        return this.prorata;
    }

    public RatePlan prorata(Double prorata) {
        this.setProrata(prorata);
        return this;
    }

    public void setProrata(Double prorata) {
        this.prorata = prorata;
    }

    public LocalDate getActivationDate() {
        return this.activationDate;
    }

    public RatePlan activationDate(LocalDate activationDate) {
        this.setActivationDate(activationDate);
        return this;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public RatePlan endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRatePlanCharges() {
        return this.ratePlanCharges;
    }

    public RatePlan ratePlanCharges(String ratePlanCharges) {
        this.setRatePlanCharges(ratePlanCharges);
        return this;
    }

    public void setRatePlanCharges(String ratePlanCharges) {
        this.ratePlanCharges = ratePlanCharges;
    }

    public String getSubscriptionFeatures() {
        return this.subscriptionFeatures;
    }

    public RatePlan subscriptionFeatures(String subscriptionFeatures) {
        this.setSubscriptionFeatures(subscriptionFeatures);
        return this;
    }

    public void setSubscriptionFeatures(String subscriptionFeatures) {
        this.subscriptionFeatures = subscriptionFeatures;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatePlan)) {
            return false;
        }
        return id != null && id.equals(((RatePlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatePlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", description='" + getDescription() + "'" +
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
            ", ratePlanId='" + getRatePlanId() + "'" +
            ", discountUnitPrice=" + getDiscountUnitPrice() +
            ", discountBase=" + getDiscountBase() +
            ", prorata=" + getProrata() +
            ", activationDate='" + getActivationDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", ratePlanCharges='" + getRatePlanCharges() + "'" +
            ", subscriptionFeatures='" + getSubscriptionFeatures() + "'" +
            "}";
    }
}
