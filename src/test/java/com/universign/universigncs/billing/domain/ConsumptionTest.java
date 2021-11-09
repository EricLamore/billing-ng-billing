package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsumptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consumption.class);
        Consumption consumption1 = new Consumption();
        consumption1.setId("id1");
        Consumption consumption2 = new Consumption();
        consumption2.setId(consumption1.getId());
        assertThat(consumption1).isEqualTo(consumption2);
        consumption2.setId("id2");
        assertThat(consumption1).isNotEqualTo(consumption2);
        consumption1.setId(null);
        assertThat(consumption1).isNotEqualTo(consumption2);
    }
}
