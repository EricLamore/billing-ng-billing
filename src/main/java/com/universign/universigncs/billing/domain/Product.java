package com.universign.universigncs.billing.domain;

import com.universign.universigncs.billing.domain.enumeration.TypeProduct;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Product.
 */
@Document(collection = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("last_update")
    private LocalDate lastUpdate;

    @Field("description")
    private String description;

    @Field("certificate_types")
    private String certificateTypes;

    @Field("commercial_name")
    private String commercialName;

    @Field("product_type")
    private TypeProduct productType;

    @Field("settings")
    private String settings;

    @Field("matrix")
    private Double matrix;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Product id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdate() {
        return this.lastUpdate;
    }

    public Product lastUpdate(LocalDate lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificateTypes() {
        return this.certificateTypes;
    }

    public Product certificateTypes(String certificateTypes) {
        this.setCertificateTypes(certificateTypes);
        return this;
    }

    public void setCertificateTypes(String certificateTypes) {
        this.certificateTypes = certificateTypes;
    }

    public String getCommercialName() {
        return this.commercialName;
    }

    public Product commercialName(String commercialName) {
        this.setCommercialName(commercialName);
        return this;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public TypeProduct getProductType() {
        return this.productType;
    }

    public Product productType(TypeProduct productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(TypeProduct productType) {
        this.productType = productType;
    }

    public String getSettings() {
        return this.settings;
    }

    public Product settings(String settings) {
        this.setSettings(settings);
        return this;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public Double getMatrix() {
        return this.matrix;
    }

    public Product matrix(Double matrix) {
        this.setMatrix(matrix);
        return this;
    }

    public void setMatrix(Double matrix) {
        this.matrix = matrix;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", certificateTypes='" + getCertificateTypes() + "'" +
            ", commercialName='" + getCommercialName() + "'" +
            ", productType='" + getProductType() + "'" +
            ", settings='" + getSettings() + "'" +
            ", matrix=" + getMatrix() +
            "}";
    }
}
