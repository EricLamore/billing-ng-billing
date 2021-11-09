package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductRatePlanChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductRatePlanCharge.class);
        ProductRatePlanCharge productRatePlanCharge1 = new ProductRatePlanCharge();
        productRatePlanCharge1.setId("id1");
        ProductRatePlanCharge productRatePlanCharge2 = new ProductRatePlanCharge();
        productRatePlanCharge2.setId(productRatePlanCharge1.getId());
        assertThat(productRatePlanCharge1).isEqualTo(productRatePlanCharge2);
        productRatePlanCharge2.setId("id2");
        assertThat(productRatePlanCharge1).isNotEqualTo(productRatePlanCharge2);
        productRatePlanCharge1.setId(null);
        assertThat(productRatePlanCharge1).isNotEqualTo(productRatePlanCharge2);
    }
}
