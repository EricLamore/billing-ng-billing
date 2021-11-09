package com.universign.universigncs.billing.domain.enumeration;

/**
 * The TypeProduct enumeration.
 */
public enum TypeProduct {
    SIGNATURE("simple"),
    SERVER_STAMP("server"),
    TIMESTAMPING("timestamp"),
    TRANSACTIONS("transactions"),
    GENERIC("generic"),
    CPM,
    PRESTA,
    OED_SETUP,
    OED_RUN,
    FORFAIT;

    private String value;

    TypeProduct() {}

    TypeProduct(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
