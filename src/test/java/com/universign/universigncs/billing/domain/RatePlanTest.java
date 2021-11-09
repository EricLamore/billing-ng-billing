package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatePlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatePlan.class);
        RatePlan ratePlan1 = new RatePlan();
        ratePlan1.setId("id1");
        RatePlan ratePlan2 = new RatePlan();
        ratePlan2.setId(ratePlan1.getId());
        assertThat(ratePlan1).isEqualTo(ratePlan2);
        ratePlan2.setId("id2");
        assertThat(ratePlan1).isNotEqualTo(ratePlan2);
        ratePlan1.setId(null);
        assertThat(ratePlan1).isNotEqualTo(ratePlan2);
    }
}
