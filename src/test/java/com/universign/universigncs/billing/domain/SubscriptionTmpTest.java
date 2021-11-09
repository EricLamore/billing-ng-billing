package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriptionTmpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionTmp.class);
        SubscriptionTmp subscriptionTmp1 = new SubscriptionTmp();
        subscriptionTmp1.setId("id1");
        SubscriptionTmp subscriptionTmp2 = new SubscriptionTmp();
        subscriptionTmp2.setId(subscriptionTmp1.getId());
        assertThat(subscriptionTmp1).isEqualTo(subscriptionTmp2);
        subscriptionTmp2.setId("id2");
        assertThat(subscriptionTmp1).isNotEqualTo(subscriptionTmp2);
        subscriptionTmp1.setId(null);
        assertThat(subscriptionTmp1).isNotEqualTo(subscriptionTmp2);
    }
}
