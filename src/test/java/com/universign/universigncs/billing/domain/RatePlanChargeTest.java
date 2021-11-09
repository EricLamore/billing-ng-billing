package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatePlanChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatePlanCharge.class);
        RatePlanCharge ratePlanCharge1 = new RatePlanCharge();
        ratePlanCharge1.setId("id1");
        RatePlanCharge ratePlanCharge2 = new RatePlanCharge();
        ratePlanCharge2.setId(ratePlanCharge1.getId());
        assertThat(ratePlanCharge1).isEqualTo(ratePlanCharge2);
        ratePlanCharge2.setId("id2");
        assertThat(ratePlanCharge1).isNotEqualTo(ratePlanCharge2);
        ratePlanCharge1.setId(null);
        assertThat(ratePlanCharge1).isNotEqualTo(ratePlanCharge2);
    }
}
