package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SettingsInvoiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingsInvoice.class);
        SettingsInvoice settingsInvoice1 = new SettingsInvoice();
        settingsInvoice1.setId("id1");
        SettingsInvoice settingsInvoice2 = new SettingsInvoice();
        settingsInvoice2.setId(settingsInvoice1.getId());
        assertThat(settingsInvoice1).isEqualTo(settingsInvoice2);
        settingsInvoice2.setId("id2");
        assertThat(settingsInvoice1).isNotEqualTo(settingsInvoice2);
        settingsInvoice1.setId(null);
        assertThat(settingsInvoice1).isNotEqualTo(settingsInvoice2);
    }
}
