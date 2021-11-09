package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetting.class);
        ProductSetting productSetting1 = new ProductSetting();
        productSetting1.setId("id1");
        ProductSetting productSetting2 = new ProductSetting();
        productSetting2.setId(productSetting1.getId());
        assertThat(productSetting1).isEqualTo(productSetting2);
        productSetting2.setId("id2");
        assertThat(productSetting1).isNotEqualTo(productSetting2);
        productSetting1.setId(null);
        assertThat(productSetting1).isNotEqualTo(productSetting2);
    }
}
