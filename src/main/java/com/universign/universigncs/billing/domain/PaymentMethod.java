package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PaymentMethod.
 */
@Document(collection = "payment_method")
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type_of_mean")
    private String typeOfMean;

    @Field("account_id")
    private String accountId;

    @Field("iban")
    private String iban;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PaymentMethod id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeOfMean() {
        return this.typeOfMean;
    }

    public PaymentMethod typeOfMean(String typeOfMean) {
        this.setTypeOfMean(typeOfMean);
        return this;
    }

    public void setTypeOfMean(String typeOfMean) {
        this.typeOfMean = typeOfMean;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public PaymentMethod accountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIban() {
        return this.iban;
    }

    public PaymentMethod iban(String iban) {
        this.setIban(iban);
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethod)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethod) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", typeOfMean='" + getTypeOfMean() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", iban='" + getIban() + "'" +
            "}";
    }
}
