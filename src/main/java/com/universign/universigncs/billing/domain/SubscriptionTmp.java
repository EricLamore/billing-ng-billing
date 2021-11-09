package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SubscriptionTmp.
 */
@Document(collection = "subscription_tmp")
public class SubscriptionTmp implements Serializable {

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

    @Field("rate_plans")
    private String ratePlans;

    @Field("subscription_features")
    private String subscriptionFeatures;

    @Field("version")
    private String version;

    @Field("usages")
    private String usages;

    @Field("free_months")
    private Integer freeMonths;

    @Field("invoice_item_dateds")
    private String invoiceItemDateds;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SubscriptionTmp id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SubscriptionTmp name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdate() {
        return this.lastUpdate;
    }

    public SubscriptionTmp lastUpdate(LocalDate lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDescription() {
        return this.description;
    }

    public SubscriptionTmp description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommercialName() {
        return this.commercialName;
    }

    public SubscriptionTmp commercialName(String commercialName) {
        this.setCommercialName(commercialName);
        return this;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public String getRatePlans() {
        return this.ratePlans;
    }

    public SubscriptionTmp ratePlans(String ratePlans) {
        this.setRatePlans(ratePlans);
        return this;
    }

    public void setRatePlans(String ratePlans) {
        this.ratePlans = ratePlans;
    }

    public String getSubscriptionFeatures() {
        return this.subscriptionFeatures;
    }

    public SubscriptionTmp subscriptionFeatures(String subscriptionFeatures) {
        this.setSubscriptionFeatures(subscriptionFeatures);
        return this;
    }

    public void setSubscriptionFeatures(String subscriptionFeatures) {
        this.subscriptionFeatures = subscriptionFeatures;
    }

    public String getVersion() {
        return this.version;
    }

    public SubscriptionTmp version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUsages() {
        return this.usages;
    }

    public SubscriptionTmp usages(String usages) {
        this.setUsages(usages);
        return this;
    }

    public void setUsages(String usages) {
        this.usages = usages;
    }

    public Integer getFreeMonths() {
        return this.freeMonths;
    }

    public SubscriptionTmp freeMonths(Integer freeMonths) {
        this.setFreeMonths(freeMonths);
        return this;
    }

    public void setFreeMonths(Integer freeMonths) {
        this.freeMonths = freeMonths;
    }

    public String getInvoiceItemDateds() {
        return this.invoiceItemDateds;
    }

    public SubscriptionTmp invoiceItemDateds(String invoiceItemDateds) {
        this.setInvoiceItemDateds(invoiceItemDateds);
        return this;
    }

    public void setInvoiceItemDateds(String invoiceItemDateds) {
        this.invoiceItemDateds = invoiceItemDateds;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionTmp)) {
            return false;
        }
        return id != null && id.equals(((SubscriptionTmp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionTmp{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", commercialName='" + getCommercialName() + "'" +
            ", ratePlans='" + getRatePlans() + "'" +
            ", subscriptionFeatures='" + getSubscriptionFeatures() + "'" +
            ", version='" + getVersion() + "'" +
            ", usages='" + getUsages() + "'" +
            ", freeMonths=" + getFreeMonths() +
            ", invoiceItemDateds='" + getInvoiceItemDateds() + "'" +
            "}";
    }
}
