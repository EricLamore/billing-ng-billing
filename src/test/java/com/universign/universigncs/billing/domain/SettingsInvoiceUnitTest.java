package com.universign.universigncs.billing.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.billing.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SettingsInvoiceUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingsInvoiceUnit.class);
        SettingsInvoiceUnit settingsInvoiceUnit1 = new SettingsInvoiceUnit();
        settingsInvoiceUnit1.setId("id1");
        SettingsInvoiceUnit settingsInvoiceUnit2 = new SettingsInvoiceUnit();
        settingsInvoiceUnit2.setId(settingsInvoiceUnit1.getId());
        assertThat(settingsInvoiceUnit1).isEqualTo(settingsInvoiceUnit2);
        settingsInvoiceUnit2.setId("id2");
        assertThat(settingsInvoiceUnit1).isNotEqualTo(settingsInvoiceUnit2);
        settingsInvoiceUnit1.setId(null);
        assertThat(settingsInvoiceUnit1).isNotEqualTo(settingsInvoiceUnit2);
    }
}
