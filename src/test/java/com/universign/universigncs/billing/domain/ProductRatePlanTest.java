package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductRatePlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductRatePlan.class);
        ProductRatePlan productRatePlan1 = new ProductRatePlan();
        productRatePlan1.setId("id1");
        ProductRatePlan productRatePlan2 = new ProductRatePlan();
        productRatePlan2.setId(productRatePlan1.getId());
        assertThat(productRatePlan1).isEqualTo(productRatePlan2);
        productRatePlan2.setId("id2");
        assertThat(productRatePlan1).isNotEqualTo(productRatePlan2);
        productRatePlan1.setId(null);
        assertThat(productRatePlan1).isNotEqualTo(productRatePlan2);
    }
}
