package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RefundTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Refund.class);
        Refund refund1 = new Refund();
        refund1.setId("id1");
        Refund refund2 = new Refund();
        refund2.setId(refund1.getId());
        assertThat(refund1).isEqualTo(refund2);
        refund2.setId("id2");
        assertThat(refund1).isNotEqualTo(refund2);
        refund1.setId(null);
        assertThat(refund1).isNotEqualTo(refund2);
    }
}
