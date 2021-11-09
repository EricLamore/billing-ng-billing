package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriptionFeatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionFeature.class);
        SubscriptionFeature subscriptionFeature1 = new SubscriptionFeature();
        subscriptionFeature1.setId("id1");
        SubscriptionFeature subscriptionFeature2 = new SubscriptionFeature();
        subscriptionFeature2.setId(subscriptionFeature1.getId());
        assertThat(subscriptionFeature1).isEqualTo(subscriptionFeature2);
        subscriptionFeature2.setId("id2");
        assertThat(subscriptionFeature1).isNotEqualTo(subscriptionFeature2);
        subscriptionFeature1.setId(null);
        assertThat(subscriptionFeature1).isNotEqualTo(subscriptionFeature2);
    }
}
