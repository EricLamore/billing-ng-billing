package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.SettingsInvoice;
import com.universign.universigncs.billing.domain.enumeration.Period;
import com.universign.universigncs.billing.repository.SettingsInvoiceRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SettingsInvoiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SettingsInvoiceResourceIT {

    private static final String DEFAULT_GLOBAL = "AAAAAAAAAA";
    private static final String UPDATED_GLOBAL = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_TAX_PER_CENT = 1D;
    private static final Double UPDATED_TAX_PER_CENT = 2D;

    private static final Boolean DEFAULT_DETAIL_SKIPPED = false;
    private static final Boolean UPDATED_DETAIL_SKIPPED = true;

    private static final Boolean DEFAULT_MANUAL_BILLING_ONLY = false;
    private static final Boolean UPDATED_MANUAL_BILLING_ONLY = true;

    private static final Boolean DEFAULT_BILLING_ACTIVE = false;
    private static final Boolean UPDATED_BILLING_ACTIVE = true;

    private static final Boolean DEFAULT_PER_ORGANIZATION = false;
    private static final Boolean UPDATED_PER_ORGANIZATION = true;

    private static final Boolean DEFAULT_FULLY_AUTOMATIC = false;
    private static final Boolean UPDATED_FULLY_AUTOMATIC = true;

    private static final Period DEFAULT_PERIOD = Period.MONTHLY;
    private static final Period UPDATED_PERIOD = Period.QUARTERLY;

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERMS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/settings-invoices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SettingsInvoiceRepository settingsInvoiceRepository;

    @Autowired
    private MockMvc restSettingsInvoiceMockMvc;

    private SettingsInvoice settingsInvoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingsInvoice createEntity() {
        SettingsInvoice settingsInvoice = new SettingsInvoice()
            .global(DEFAULT_GLOBAL)
            .subscription(DEFAULT_SUBSCRIPTION)
            .taxPerCent(DEFAULT_TAX_PER_CENT)
            .detailSkipped(DEFAULT_DETAIL_SKIPPED)
            .manualBillingOnly(DEFAULT_MANUAL_BILLING_ONLY)
            .billingActive(DEFAULT_BILLING_ACTIVE)
            .perOrganization(DEFAULT_PER_ORGANIZATION)
            .fullyAutomatic(DEFAULT_FULLY_AUTOMATIC)
            .period(DEFAULT_PERIOD)
            .locale(DEFAULT_LOCALE)
            .paymentTerms(DEFAULT_PAYMENT_TERMS);
        return settingsInvoice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingsInvoice createUpdatedEntity() {
        SettingsInvoice settingsInvoice = new SettingsInvoice()
            .global(UPDATED_GLOBAL)
            .subscription(UPDATED_SUBSCRIPTION)
            .taxPerCent(UPDATED_TAX_PER_CENT)
            .detailSkipped(UPDATED_DETAIL_SKIPPED)
            .manualBillingOnly(UPDATED_MANUAL_BILLING_ONLY)
            .billingActive(UPDATED_BILLING_ACTIVE)
            .perOrganization(UPDATED_PER_ORGANIZATION)
            .fullyAutomatic(UPDATED_FULLY_AUTOMATIC)
            .period(UPDATED_PERIOD)
            .locale(UPDATED_LOCALE)
            .paymentTerms(UPDATED_PAYMENT_TERMS);
        return settingsInvoice;
    }

    @BeforeEach
    public void initTest() {
        settingsInvoiceRepository.deleteAll();
        settingsInvoice = createEntity();
    }

    @Test
    void createSettingsInvoice() throws Exception {
        int databaseSizeBeforeCreate = settingsInvoiceRepository.findAll().size();
        // Create the SettingsInvoice
        restSettingsInvoiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isCreated());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        SettingsInvoice testSettingsInvoice = settingsInvoiceList.get(settingsInvoiceList.size() - 1);
        assertThat(testSettingsInvoice.getGlobal()).isEqualTo(DEFAULT_GLOBAL);
        assertThat(testSettingsInvoice.getSubscription()).isEqualTo(DEFAULT_SUBSCRIPTION);
        assertThat(testSettingsInvoice.getTaxPerCent()).isEqualTo(DEFAULT_TAX_PER_CENT);
        assertThat(testSettingsInvoice.getDetailSkipped()).isEqualTo(DEFAULT_DETAIL_SKIPPED);
        assertThat(testSettingsInvoice.getManualBillingOnly()).isEqualTo(DEFAULT_MANUAL_BILLING_ONLY);
        assertThat(testSettingsInvoice.getBillingActive()).isEqualTo(DEFAULT_BILLING_ACTIVE);
        assertThat(testSettingsInvoice.getPerOrganization()).isEqualTo(DEFAULT_PER_ORGANIZATION);
        assertThat(testSettingsInvoice.getFullyAutomatic()).isEqualTo(DEFAULT_FULLY_AUTOMATIC);
        assertThat(testSettingsInvoice.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testSettingsInvoice.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testSettingsInvoice.getPaymentTerms()).isEqualTo(DEFAULT_PAYMENT_TERMS);
    }

    @Test
    void createSettingsInvoiceWithExistingId() throws Exception {
        // Create the SettingsInvoice with an existing ID
        settingsInvoice.setId("existing_id");

        int databaseSizeBeforeCreate = settingsInvoiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingsInvoiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSettingsInvoices() throws Exception {
        // Initialize the database
        settingsInvoiceRepository.save(settingsInvoice);

        // Get all the settingsInvoiceList
        restSettingsInvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingsInvoice.getId())))
            .andExpect(jsonPath("$.[*].global").value(hasItem(DEFAULT_GLOBAL)))
            .andExpect(jsonPath("$.[*].subscription").value(hasItem(DEFAULT_SUBSCRIPTION)))
            .andExpect(jsonPath("$.[*].taxPerCent").value(hasItem(DEFAULT_TAX_PER_CENT.doubleValue())))
            .andExpect(jsonPath("$.[*].detailSkipped").value(hasItem(DEFAULT_DETAIL_SKIPPED.booleanValue())))
            .andExpect(jsonPath("$.[*].manualBillingOnly").value(hasItem(DEFAULT_MANUAL_BILLING_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].billingActive").value(hasItem(DEFAULT_BILLING_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].perOrganization").value(hasItem(DEFAULT_PER_ORGANIZATION.booleanValue())))
            .andExpect(jsonPath("$.[*].fullyAutomatic").value(hasItem(DEFAULT_FULLY_AUTOMATIC.booleanValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].paymentTerms").value(hasItem(DEFAULT_PAYMENT_TERMS)));
    }

    @Test
    void getSettingsInvoice() throws Exception {
        // Initialize the database
        settingsInvoiceRepository.save(settingsInvoice);

        // Get the settingsInvoice
        restSettingsInvoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, settingsInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(settingsInvoice.getId()))
            .andExpect(jsonPath("$.global").value(DEFAULT_GLOBAL))
            .andExpect(jsonPath("$.subscription").value(DEFAULT_SUBSCRIPTION))
            .andExpect(jsonPath("$.taxPerCent").value(DEFAULT_TAX_PER_CENT.doubleValue()))
            .andExpect(jsonPath("$.detailSkipped").value(DEFAULT_DETAIL_SKIPPED.booleanValue()))
            .andExpect(jsonPath("$.manualBillingOnly").value(DEFAULT_MANUAL_BILLING_ONLY.booleanValue()))
            .andExpect(jsonPath("$.billingActive").value(DEFAULT_BILLING_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.perOrganization").value(DEFAULT_PER_ORGANIZATION.booleanValue()))
            .andExpect(jsonPath("$.fullyAutomatic").value(DEFAULT_FULLY_AUTOMATIC.booleanValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.toString()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.paymentTerms").value(DEFAULT_PAYMENT_TERMS));
    }

    @Test
    void getNonExistingSettingsInvoice() throws Exception {
        // Get the settingsInvoice
        restSettingsInvoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewSettingsInvoice() throws Exception {
        // Initialize the database
        settingsInvoiceRepository.save(settingsInvoice);

        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();

        // Update the settingsInvoice
        SettingsInvoice updatedSettingsInvoice = settingsInvoiceRepository.findById(settingsInvoice.getId()).get();
        updatedSettingsInvoice
            .global(UPDATED_GLOBAL)
            .subscription(UPDATED_SUBSCRIPTION)
            .taxPerCent(UPDATED_TAX_PER_CENT)
            .detailSkipped(UPDATED_DETAIL_SKIPPED)
            .manualBillingOnly(UPDATED_MANUAL_BILLING_ONLY)
            .billingActive(UPDATED_BILLING_ACTIVE)
            .perOrganization(UPDATED_PER_ORGANIZATION)
            .fullyAutomatic(UPDATED_FULLY_AUTOMATIC)
            .period(UPDATED_PERIOD)
            .locale(UPDATED_LOCALE)
            .paymentTerms(UPDATED_PAYMENT_TERMS);

        restSettingsInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSettingsInvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSettingsInvoice))
            )
            .andExpect(status().isOk());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
        SettingsInvoice testSettingsInvoice = settingsInvoiceList.get(settingsInvoiceList.size() - 1);
        assertThat(testSettingsInvoice.getGlobal()).isEqualTo(UPDATED_GLOBAL);
        assertThat(testSettingsInvoice.getSubscription()).isEqualTo(UPDATED_SUBSCRIPTION);
        assertThat(testSettingsInvoice.getTaxPerCent()).isEqualTo(UPDATED_TAX_PER_CENT);
        assertThat(testSettingsInvoice.getDetailSkipped()).isEqualTo(UPDATED_DETAIL_SKIPPED);
        assertThat(testSettingsInvoice.getManualBillingOnly()).isEqualTo(UPDATED_MANUAL_BILLING_ONLY);
        assertThat(testSettingsInvoice.getBillingActive()).isEqualTo(UPDATED_BILLING_ACTIVE);
        assertThat(testSettingsInvoice.getPerOrganization()).isEqualTo(UPDATED_PER_ORGANIZATION);
        assertThat(testSettingsInvoice.getFullyAutomatic()).isEqualTo(UPDATED_FULLY_AUTOMATIC);
        assertThat(testSettingsInvoice.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testSettingsInvoice.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testSettingsInvoice.getPaymentTerms()).isEqualTo(UPDATED_PAYMENT_TERMS);
    }

    @Test
    void putNonExistingSettingsInvoice() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();
        settingsInvoice.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingsInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingsInvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSettingsInvoice() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();
        settingsInvoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSettingsInvoice() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();
        settingsInvoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSettingsInvoiceWithPatch() throws Exception {
        // Initialize the database
        settingsInvoiceRepository.save(settingsInvoice);

        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();

        // Update the settingsInvoice using partial update
        SettingsInvoice partialUpdatedSettingsInvoice = new SettingsInvoice();
        partialUpdatedSettingsInvoice.setId(settingsInvoice.getId());

        partialUpdatedSettingsInvoice
            .subscription(UPDATED_SUBSCRIPTION)
            .manualBillingOnly(UPDATED_MANUAL_BILLING_ONLY)
            .billingActive(UPDATED_BILLING_ACTIVE)
            .paymentTerms(UPDATED_PAYMENT_TERMS);

        restSettingsInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingsInvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingsInvoice))
            )
            .andExpect(status().isOk());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
        SettingsInvoice testSettingsInvoice = settingsInvoiceList.get(settingsInvoiceList.size() - 1);
        assertThat(testSettingsInvoice.getGlobal()).isEqualTo(DEFAULT_GLOBAL);
        assertThat(testSettingsInvoice.getSubscription()).isEqualTo(UPDATED_SUBSCRIPTION);
        assertThat(testSettingsInvoice.getTaxPerCent()).isEqualTo(DEFAULT_TAX_PER_CENT);
        assertThat(testSettingsInvoice.getDetailSkipped()).isEqualTo(DEFAULT_DETAIL_SKIPPED);
        assertThat(testSettingsInvoice.getManualBillingOnly()).isEqualTo(UPDATED_MANUAL_BILLING_ONLY);
        assertThat(testSettingsInvoice.getBillingActive()).isEqualTo(UPDATED_BILLING_ACTIVE);
        assertThat(testSettingsInvoice.getPerOrganization()).isEqualTo(DEFAULT_PER_ORGANIZATION);
        assertThat(testSettingsInvoice.getFullyAutomatic()).isEqualTo(DEFAULT_FULLY_AUTOMATIC);
        assertThat(testSettingsInvoice.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testSettingsInvoice.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testSettingsInvoice.getPaymentTerms()).isEqualTo(UPDATED_PAYMENT_TERMS);
    }

    @Test
    void fullUpdateSettingsInvoiceWithPatch() throws Exception {
        // Initialize the database
        settingsInvoiceRepository.save(settingsInvoice);

        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();

        // Update the settingsInvoice using partial update
        SettingsInvoice partialUpdatedSettingsInvoice = new SettingsInvoice();
        partialUpdatedSettingsInvoice.setId(settingsInvoice.getId());

        partialUpdatedSettingsInvoice
            .global(UPDATED_GLOBAL)
            .subscription(UPDATED_SUBSCRIPTION)
            .taxPerCent(UPDATED_TAX_PER_CENT)
            .detailSkipped(UPDATED_DETAIL_SKIPPED)
            .manualBillingOnly(UPDATED_MANUAL_BILLING_ONLY)
            .billingActive(UPDATED_BILLING_ACTIVE)
            .perOrganization(UPDATED_PER_ORGANIZATION)
            .fullyAutomatic(UPDATED_FULLY_AUTOMATIC)
            .period(UPDATED_PERIOD)
            .locale(UPDATED_LOCALE)
            .paymentTerms(UPDATED_PAYMENT_TERMS);

        restSettingsInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingsInvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingsInvoice))
            )
            .andExpect(status().isOk());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
        SettingsInvoice testSettingsInvoice = settingsInvoiceList.get(settingsInvoiceList.size() - 1);
        assertThat(testSettingsInvoice.getGlobal()).isEqualTo(UPDATED_GLOBAL);
        assertThat(testSettingsInvoice.getSubscription()).isEqualTo(UPDATED_SUBSCRIPTION);
        assertThat(testSettingsInvoice.getTaxPerCent()).isEqualTo(UPDATED_TAX_PER_CENT);
        assertThat(testSettingsInvoice.getDetailSkipped()).isEqualTo(UPDATED_DETAIL_SKIPPED);
        assertThat(testSettingsInvoice.getManualBillingOnly()).isEqualTo(UPDATED_MANUAL_BILLING_ONLY);
        assertThat(testSettingsInvoice.getBillingActive()).isEqualTo(UPDATED_BILLING_ACTIVE);
        assertThat(testSettingsInvoice.getPerOrganization()).isEqualTo(UPDATED_PER_ORGANIZATION);
        assertThat(testSettingsInvoice.getFullyAutomatic()).isEqualTo(UPDATED_FULLY_AUTOMATIC);
        assertThat(testSettingsInvoice.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testSettingsInvoice.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testSettingsInvoice.getPaymentTerms()).isEqualTo(UPDATED_PAYMENT_TERMS);
    }

    @Test
    void patchNonExistingSettingsInvoice() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();
        settingsInvoice.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingsInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, settingsInvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSettingsInvoice() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();
        settingsInvoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSettingsInvoice() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceRepository.findAll().size();
        settingsInvoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SettingsInvoice in the database
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSettingsInvoice() throws Exception {
        // Initialize the database
        settingsInvoiceRepository.save(settingsInvoice);

        int databaseSizeBeforeDelete = settingsInvoiceRepository.findAll().size();

        // Delete the settingsInvoice
        restSettingsInvoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, settingsInvoice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SettingsInvoice> settingsInvoiceList = settingsInvoiceRepository.findAll();
        assertThat(settingsInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
