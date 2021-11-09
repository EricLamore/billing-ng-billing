package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SettingsInvoiceUnit.
 */
@Document(collection = "settings_invoice_unit")
public class SettingsInvoiceUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seller")
    private String seller;

    @Field("person_buyer_id")
    private String personBuyerId;

    @Field("person_referer_copys")
    private String personRefererCopys;

    @Field("subscription_id")
    private String subscriptionId;

    @Field("use_billing_address")
    private Boolean useBillingAddress;

    @Field("payment_method")
    private String paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SettingsInvoiceUnit id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller() {
        return this.seller;
    }

    public SettingsInvoiceUnit seller(String seller) {
        this.setSeller(seller);
        return this;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPersonBuyerId() {
        return this.personBuyerId;
    }

    public SettingsInvoiceUnit personBuyerId(String personBuyerId) {
        this.setPersonBuyerId(personBuyerId);
        return this;
    }

    public void setPersonBuyerId(String personBuyerId) {
        this.personBuyerId = personBuyerId;
    }

    public String getPersonRefererCopys() {
        return this.personRefererCopys;
    }

    public SettingsInvoiceUnit personRefererCopys(String personRefererCopys) {
        this.setPersonRefererCopys(personRefererCopys);
        return this;
    }

    public void setPersonRefererCopys(String personRefererCopys) {
        this.personRefererCopys = personRefererCopys;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    public SettingsInvoiceUnit subscriptionId(String subscriptionId) {
        this.setSubscriptionId(subscriptionId);
        return this;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Boolean getUseBillingAddress() {
        return this.useBillingAddress;
    }

    public SettingsInvoiceUnit useBillingAddress(Boolean useBillingAddress) {
        this.setUseBillingAddress(useBillingAddress);
        return this;
    }

    public void setUseBillingAddress(Boolean useBillingAddress) {
        this.useBillingAddress = useBillingAddress;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public SettingsInvoiceUnit paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SettingsInvoiceUnit)) {
            return false;
        }
        return id != null && id.equals(((SettingsInvoiceUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SettingsInvoiceUnit{" +
            "id=" + getId() +
            ", seller='" + getSeller() + "'" +
            ", personBuyerId='" + getPersonBuyerId() + "'" +
            ", personRefererCopys='" + getPersonRefererCopys() + "'" +
            ", subscriptionId='" + getSubscriptionId() + "'" +
            ", useBillingAddress='" + getUseBillingAddress() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }
}
