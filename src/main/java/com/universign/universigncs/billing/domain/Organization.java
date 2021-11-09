package com.universign.universigncs.billing.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Organization.
 */
@Document(collection = "organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("addr")
    private String addr;

    @Field("city")
    private String city;

    @Field("country")
    private String country;

    @Field("name")
    private String name;

    @Field("register_date")
    private LocalDate registerDate;

    @Field("status")
    private Integer status;

    @Field("zip_code")
    private String zipCode;

    @Field("individual")
    private Boolean individual;

    @Field("vat_number")
    private String vatNumber;

    @Field("ip_ranges")
    private String ipRanges;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Organization id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddr() {
        return this.addr;
    }

    public Organization addr(String addr) {
        this.setAddr(addr);
        return this;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return this.city;
    }

    public Organization city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public Organization country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return this.name;
    }

    public Organization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegisterDate() {
        return this.registerDate;
    }

    public Organization registerDate(LocalDate registerDate) {
        this.setRegisterDate(registerDate);
        return this;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Organization status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Organization zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getIndividual() {
        return this.individual;
    }

    public Organization individual(Boolean individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Boolean individual) {
        this.individual = individual;
    }

    public String getVatNumber() {
        return this.vatNumber;
    }

    public Organization vatNumber(String vatNumber) {
        this.setVatNumber(vatNumber);
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getIpRanges() {
        return this.ipRanges;
    }

    public Organization ipRanges(String ipRanges) {
        this.setIpRanges(ipRanges);
        return this;
    }

    public void setIpRanges(String ipRanges) {
        this.ipRanges = ipRanges;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", addr='" + getAddr() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", name='" + getName() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            ", status=" + getStatus() +
            ", zipCode='" + getZipCode() + "'" +
            ", individual='" + getIndividual() + "'" +
            ", vatNumber='" + getVatNumber() + "'" +
            ", ipRanges='" + getIpRanges() + "'" +
            "}";
    }
}
