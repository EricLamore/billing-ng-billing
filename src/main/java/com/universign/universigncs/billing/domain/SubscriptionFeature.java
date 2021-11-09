package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SubscriptionFeature.
 */
@Document(collection = "subscription_feature")
public class SubscriptionFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("last_update")
    private LocalDate lastUpdate;

    @Field("description")
    private String description;

    @Field("is_visible")
    private Boolean isVisible;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SubscriptionFeature id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SubscriptionFeature name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdate() {
        return this.lastUpdate;
    }

    public SubscriptionFeature lastUpdate(LocalDate lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDescription() {
        return this.description;
    }

    public SubscriptionFeature description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsVisible() {
        return this.isVisible;
    }

    public SubscriptionFeature isVisible(Boolean isVisible) {
        this.setIsVisible(isVisible);
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionFeature)) {
            return false;
        }
        return id != null && id.equals(((SubscriptionFeature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionFeature{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", isVisible='" + getIsVisible() + "'" +
            "}";
    }
}
