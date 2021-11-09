package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Customer.
 */
@Document(collection = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("last_update")
    private LocalDate lastUpdate;

    @Field("description")
    private String description;

    @Field("tax_no")
    private String taxNo;

    @Field("third_party_accounting_code")
    private String thirdPartyAccountingCode;

    @Field("siret")
    private String siret;

    @Field("owner_id")
    private String ownerId;

    @Field("is_particular")
    private Boolean isParticular;

    @Field("person_referers")
    private String personReferers;

    @Field("means_payments")
    private String meansPayments;

    @Field("subscriptions")
    private String subscriptions;

    @Field("usages")
    private String usages;

    @Field("settings_invoice")
    private String settingsInvoice;

    @Field("partner")
    private Boolean partner;

    @Field("partner_id")
    private String partnerId;

    @Field("customer_id")
    private String customerId;

    @Field("customer_name")
    private String customerName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Customer id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Customer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdate() {
        return this.lastUpdate;
    }

    public Customer lastUpdate(LocalDate lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDescription() {
        return this.description;
    }

    public Customer description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxNo() {
        return this.taxNo;
    }

    public Customer taxNo(String taxNo) {
        this.setTaxNo(taxNo);
        return this;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getThirdPartyAccountingCode() {
        return this.thirdPartyAccountingCode;
    }

    public Customer thirdPartyAccountingCode(String thirdPartyAccountingCode) {
        this.setThirdPartyAccountingCode(thirdPartyAccountingCode);
        return this;
    }

    public void setThirdPartyAccountingCode(String thirdPartyAccountingCode) {
        this.thirdPartyAccountingCode = thirdPartyAccountingCode;
    }

    public String getSiret() {
        return this.siret;
    }

    public Customer siret(String siret) {
        this.setSiret(siret);
        return this;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public Customer ownerId(String ownerId) {
        this.setOwnerId(ownerId);
        return this;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getIsParticular() {
        return this.isParticular;
    }

    public Customer isParticular(Boolean isParticular) {
        this.setIsParticular(isParticular);
        return this;
    }

    public void setIsParticular(Boolean isParticular) {
        this.isParticular = isParticular;
    }

    public String getPersonReferers() {
        return this.personReferers;
    }

    public Customer personReferers(String personReferers) {
        this.setPersonReferers(personReferers);
        return this;
    }

    public void setPersonReferers(String personReferers) {
        this.personReferers = personReferers;
    }

    public String getMeansPayments() {
        return this.meansPayments;
    }

    public Customer meansPayments(String meansPayments) {
        this.setMeansPayments(meansPayments);
        return this;
    }

    public void setMeansPayments(String meansPayments) {
        this.meansPayments = meansPayments;
    }

    public String getSubscriptions() {
        return this.subscriptions;
    }

    public Customer subscriptions(String subscriptions) {
        this.setSubscriptions(subscriptions);
        return this;
    }

    public void setSubscriptions(String subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getUsages() {
        return this.usages;
    }

    public Customer usages(String usages) {
        this.setUsages(usages);
        return this;
    }

    public void setUsages(String usages) {
        this.usages = usages;
    }

    public String getSettingsInvoice() {
        return this.settingsInvoice;
    }

    public Customer settingsInvoice(String settingsInvoice) {
        this.setSettingsInvoice(settingsInvoice);
        return this;
    }

    public void setSettingsInvoice(String settingsInvoice) {
        this.settingsInvoice = settingsInvoice;
    }

    public Boolean getPartner() {
        return this.partner;
    }

    public Customer partner(Boolean partner) {
        this.setPartner(partner);
        return this;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public String getPartnerId() {
        return this.partnerId;
    }

    public Customer partnerId(String partnerId) {
        this.setPartnerId(partnerId);
        return this;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Customer customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Customer customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", taxNo='" + getTaxNo() + "'" +
            ", thirdPartyAccountingCode='" + getThirdPartyAccountingCode() + "'" +
            ", siret='" + getSiret() + "'" +
            ", ownerId='" + getOwnerId() + "'" +
            ", isParticular='" + getIsParticular() + "'" +
            ", personReferers='" + getPersonReferers() + "'" +
            ", meansPayments='" + getMeansPayments() + "'" +
            ", subscriptions='" + getSubscriptions() + "'" +
            ", usages='" + getUsages() + "'" +
            ", settingsInvoice='" + getSettingsInvoice() + "'" +
            ", partner='" + getPartner() + "'" +
            ", partnerId='" + getPartnerId() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            "}";
    }
}
