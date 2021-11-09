package com.universign.universigncs.billing.domain;

import com.universign.universigncs.billing.domain.enumeration.TypeProduct;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Consumption.
 */
@Document(collection = "consumption")
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("organisation_id")
    private String organisationId;

    @Field("organization_name")
    private String organizationName;

    @Field("rate_plan_id")
    private String ratePlanId;

    @Field("name")
    private String name;

    @Field("type")
    private TypeProduct type;

    @Field("month")
    private Integer month;

    @Field("year")
    private Integer year;

    @Field("details")
    private String details;

    @Field("nb_units")
    private Integer nbUnits;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Consumption id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganisationId() {
        return this.organisationId;
    }

    public Consumption organisationId(String organisationId) {
        this.setOrganisationId(organisationId);
        return this;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Consumption organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRatePlanId() {
        return this.ratePlanId;
    }

    public Consumption ratePlanId(String ratePlanId) {
        this.setRatePlanId(ratePlanId);
        return this;
    }

    public void setRatePlanId(String ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public String getName() {
        return this.name;
    }

    public Consumption name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeProduct getType() {
        return this.type;
    }

    public Consumption type(TypeProduct type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeProduct type) {
        this.type = type;
    }

    public Integer getMonth() {
        return this.month;
    }

    public Consumption month(Integer month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return this.year;
    }

    public Consumption year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDetails() {
        return this.details;
    }

    public Consumption details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getNbUnits() {
        return this.nbUnits;
    }

    public Consumption nbUnits(Integer nbUnits) {
        this.setNbUnits(nbUnits);
        return this;
    }

    public void setNbUnits(Integer nbUnits) {
        this.nbUnits = nbUnits;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consumption)) {
            return false;
        }
        return id != null && id.equals(((Consumption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consumption{" +
            "id=" + getId() +
            ", organisationId='" + getOrganisationId() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", ratePlanId='" + getRatePlanId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", details='" + getDetails() + "'" +
            ", nbUnits=" + getNbUnits() +
            "}";
    }
}
