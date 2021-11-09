package com.universign.universigncs.billing.domain;

import com.universign.universigncs.billing.domain.enumeration.Period;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SettingsInvoice.
 */
@Document(collection = "settings_invoice")
public class SettingsInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("global")
    private String global;

    @Field("subscription")
    private String subscription;

    @Field("tax_per_cent")
    private Double taxPerCent;

    @Field("detail_skipped")
    private Boolean detailSkipped;

    @Field("manual_billing_only")
    private Boolean manualBillingOnly;

    @Field("billing_active")
    private Boolean billingActive;

    @Field("per_organization")
    private Boolean perOrganization;

    @Field("fully_automatic")
    private Boolean fullyAutomatic;

    @Field("period")
    private Period period;

    @Field("locale")
    private String locale;

    @Field("payment_terms")
    private String paymentTerms;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SettingsInvoice id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlobal() {
        return this.global;
    }

    public SettingsInvoice global(String global) {
        this.setGlobal(global);
        return this;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getSubscription() {
        return this.subscription;
    }

    public SettingsInvoice subscription(String subscription) {
        this.setSubscription(subscription);
        return this;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Double getTaxPerCent() {
        return this.taxPerCent;
    }

    public SettingsInvoice taxPerCent(Double taxPerCent) {
        this.setTaxPerCent(taxPerCent);
        return this;
    }

    public void setTaxPerCent(Double taxPerCent) {
        this.taxPerCent = taxPerCent;
    }

    public Boolean getDetailSkipped() {
        return this.detailSkipped;
    }

    public SettingsInvoice detailSkipped(Boolean detailSkipped) {
        this.setDetailSkipped(detailSkipped);
        return this;
    }

    public void setDetailSkipped(Boolean detailSkipped) {
        this.detailSkipped = detailSkipped;
    }

    public Boolean getManualBillingOnly() {
        return this.manualBillingOnly;
    }

    public SettingsInvoice manualBillingOnly(Boolean manualBillingOnly) {
        this.setManualBillingOnly(manualBillingOnly);
        return this;
    }

    public void setManualBillingOnly(Boolean manualBillingOnly) {
        this.manualBillingOnly = manualBillingOnly;
    }

    public Boolean getBillingActive() {
        return this.billingActive;
    }

    public SettingsInvoice billingActive(Boolean billingActive) {
        this.setBillingActive(billingActive);
        return this;
    }

    public void setBillingActive(Boolean billingActive) {
        this.billingActive = billingActive;
    }

    public Boolean getPerOrganization() {
        return this.perOrganization;
    }

    public SettingsInvoice perOrganization(Boolean perOrganization) {
        this.setPerOrganization(perOrganization);
        return this;
    }

    public void setPerOrganization(Boolean perOrganization) {
        this.perOrganization = perOrganization;
    }

    public Boolean getFullyAutomatic() {
        return this.fullyAutomatic;
    }

    public SettingsInvoice fullyAutomatic(Boolean fullyAutomatic) {
        this.setFullyAutomatic(fullyAutomatic);
        return this;
    }

    public void setFullyAutomatic(Boolean fullyAutomatic) {
        this.fullyAutomatic = fullyAutomatic;
    }

    public Period getPeriod() {
        return this.period;
    }

    public SettingsInvoice period(Period period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getLocale() {
        return this.locale;
    }

    public SettingsInvoice locale(String locale) {
        this.setLocale(locale);
        return this;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPaymentTerms() {
        return this.paymentTerms;
    }

    public SettingsInvoice paymentTerms(String paymentTerms) {
        this.setPaymentTerms(paymentTerms);
        return this;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SettingsInvoice)) {
            return false;
        }
        return id != null && id.equals(((SettingsInvoice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SettingsInvoice{" +
            "id=" + getId() +
            ", global='" + getGlobal() + "'" +
            ", subscription='" + getSubscription() + "'" +
            ", taxPerCent=" + getTaxPerCent() +
            ", detailSkipped='" + getDetailSkipped() + "'" +
            ", manualBillingOnly='" + getManualBillingOnly() + "'" +
            ", billingActive='" + getBillingActive() + "'" +
            ", perOrganization='" + getPerOrganization() + "'" +
            ", fullyAutomatic='" + getFullyAutomatic() + "'" +
            ", period='" + getPeriod() + "'" +
            ", locale='" + getLocale() + "'" +
            ", paymentTerms='" + getPaymentTerms() + "'" +
            "}";
    }
}
