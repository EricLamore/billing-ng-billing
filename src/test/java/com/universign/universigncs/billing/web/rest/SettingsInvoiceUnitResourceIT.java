package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.SettingsInvoiceUnit;
import com.universign.universigncs.billing.repository.SettingsInvoiceUnitRepository;
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
 * Integration tests for the {@link SettingsInvoiceUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SettingsInvoiceUnitResourceIT {

    private static final String DEFAULT_SELLER = "AAAAAAAAAA";
    private static final String UPDATED_SELLER = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_BUYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_BUYER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_REFERER_COPYS = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_REFERER_COPYS = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_BILLING_ADDRESS = false;
    private static final Boolean UPDATED_USE_BILLING_ADDRESS = true;

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/settings-invoice-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SettingsInvoiceUnitRepository settingsInvoiceUnitRepository;

    @Autowired
    private MockMvc restSettingsInvoiceUnitMockMvc;

    private SettingsInvoiceUnit settingsInvoiceUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingsInvoiceUnit createEntity() {
        SettingsInvoiceUnit settingsInvoiceUnit = new SettingsInvoiceUnit()
            .seller(DEFAULT_SELLER)
            .personBuyerId(DEFAULT_PERSON_BUYER_ID)
            .personRefererCopys(DEFAULT_PERSON_REFERER_COPYS)
            .subscriptionId(DEFAULT_SUBSCRIPTION_ID)
            .useBillingAddress(DEFAULT_USE_BILLING_ADDRESS)
            .paymentMethod(DEFAULT_PAYMENT_METHOD);
        return settingsInvoiceUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingsInvoiceUnit createUpdatedEntity() {
        SettingsInvoiceUnit settingsInvoiceUnit = new SettingsInvoiceUnit()
            .seller(UPDATED_SELLER)
            .personBuyerId(UPDATED_PERSON_BUYER_ID)
            .personRefererCopys(UPDATED_PERSON_REFERER_COPYS)
            .subscriptionId(UPDATED_SUBSCRIPTION_ID)
            .useBillingAddress(UPDATED_USE_BILLING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD);
        return settingsInvoiceUnit;
    }

    @BeforeEach
    public void initTest() {
        settingsInvoiceUnitRepository.deleteAll();
        settingsInvoiceUnit = createEntity();
    }

    @Test
    void createSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeCreate = settingsInvoiceUnitRepository.findAll().size();
        // Create the SettingsInvoiceUnit
        restSettingsInvoiceUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isCreated());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeCreate + 1);
        SettingsInvoiceUnit testSettingsInvoiceUnit = settingsInvoiceUnitList.get(settingsInvoiceUnitList.size() - 1);
        assertThat(testSettingsInvoiceUnit.getSeller()).isEqualTo(DEFAULT_SELLER);
        assertThat(testSettingsInvoiceUnit.getPersonBuyerId()).isEqualTo(DEFAULT_PERSON_BUYER_ID);
        assertThat(testSettingsInvoiceUnit.getPersonRefererCopys()).isEqualTo(DEFAULT_PERSON_REFERER_COPYS);
        assertThat(testSettingsInvoiceUnit.getSubscriptionId()).isEqualTo(DEFAULT_SUBSCRIPTION_ID);
        assertThat(testSettingsInvoiceUnit.getUseBillingAddress()).isEqualTo(DEFAULT_USE_BILLING_ADDRESS);
        assertThat(testSettingsInvoiceUnit.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
    }

    @Test
    void createSettingsInvoiceUnitWithExistingId() throws Exception {
        // Create the SettingsInvoiceUnit with an existing ID
        settingsInvoiceUnit.setId("existing_id");

        int databaseSizeBeforeCreate = settingsInvoiceUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingsInvoiceUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSettingsInvoiceUnits() throws Exception {
        // Initialize the database
        settingsInvoiceUnitRepository.save(settingsInvoiceUnit);

        // Get all the settingsInvoiceUnitList
        restSettingsInvoiceUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingsInvoiceUnit.getId())))
            .andExpect(jsonPath("$.[*].seller").value(hasItem(DEFAULT_SELLER)))
            .andExpect(jsonPath("$.[*].personBuyerId").value(hasItem(DEFAULT_PERSON_BUYER_ID)))
            .andExpect(jsonPath("$.[*].personRefererCopys").value(hasItem(DEFAULT_PERSON_REFERER_COPYS)))
            .andExpect(jsonPath("$.[*].subscriptionId").value(hasItem(DEFAULT_SUBSCRIPTION_ID)))
            .andExpect(jsonPath("$.[*].useBillingAddress").value(hasItem(DEFAULT_USE_BILLING_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)));
    }

    @Test
    void getSettingsInvoiceUnit() throws Exception {
        // Initialize the database
        settingsInvoiceUnitRepository.save(settingsInvoiceUnit);

        // Get the settingsInvoiceUnit
        restSettingsInvoiceUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, settingsInvoiceUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(settingsInvoiceUnit.getId()))
            .andExpect(jsonPath("$.seller").value(DEFAULT_SELLER))
            .andExpect(jsonPath("$.personBuyerId").value(DEFAULT_PERSON_BUYER_ID))
            .andExpect(jsonPath("$.personRefererCopys").value(DEFAULT_PERSON_REFERER_COPYS))
            .andExpect(jsonPath("$.subscriptionId").value(DEFAULT_SUBSCRIPTION_ID))
            .andExpect(jsonPath("$.useBillingAddress").value(DEFAULT_USE_BILLING_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD));
    }

    @Test
    void getNonExistingSettingsInvoiceUnit() throws Exception {
        // Get the settingsInvoiceUnit
        restSettingsInvoiceUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewSettingsInvoiceUnit() throws Exception {
        // Initialize the database
        settingsInvoiceUnitRepository.save(settingsInvoiceUnit);

        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();

        // Update the settingsInvoiceUnit
        SettingsInvoiceUnit updatedSettingsInvoiceUnit = settingsInvoiceUnitRepository.findById(settingsInvoiceUnit.getId()).get();
        updatedSettingsInvoiceUnit
            .seller(UPDATED_SELLER)
            .personBuyerId(UPDATED_PERSON_BUYER_ID)
            .personRefererCopys(UPDATED_PERSON_REFERER_COPYS)
            .subscriptionId(UPDATED_SUBSCRIPTION_ID)
            .useBillingAddress(UPDATED_USE_BILLING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD);

        restSettingsInvoiceUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSettingsInvoiceUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSettingsInvoiceUnit))
            )
            .andExpect(status().isOk());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
        SettingsInvoiceUnit testSettingsInvoiceUnit = settingsInvoiceUnitList.get(settingsInvoiceUnitList.size() - 1);
        assertThat(testSettingsInvoiceUnit.getSeller()).isEqualTo(UPDATED_SELLER);
        assertThat(testSettingsInvoiceUnit.getPersonBuyerId()).isEqualTo(UPDATED_PERSON_BUYER_ID);
        assertThat(testSettingsInvoiceUnit.getPersonRefererCopys()).isEqualTo(UPDATED_PERSON_REFERER_COPYS);
        assertThat(testSettingsInvoiceUnit.getSubscriptionId()).isEqualTo(UPDATED_SUBSCRIPTION_ID);
        assertThat(testSettingsInvoiceUnit.getUseBillingAddress()).isEqualTo(UPDATED_USE_BILLING_ADDRESS);
        assertThat(testSettingsInvoiceUnit.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
    }

    @Test
    void putNonExistingSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();
        settingsInvoiceUnit.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingsInvoiceUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingsInvoiceUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();
        settingsInvoiceUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();
        settingsInvoiceUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceUnitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSettingsInvoiceUnitWithPatch() throws Exception {
        // Initialize the database
        settingsInvoiceUnitRepository.save(settingsInvoiceUnit);

        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();

        // Update the settingsInvoiceUnit using partial update
        SettingsInvoiceUnit partialUpdatedSettingsInvoiceUnit = new SettingsInvoiceUnit();
        partialUpdatedSettingsInvoiceUnit.setId(settingsInvoiceUnit.getId());

        partialUpdatedSettingsInvoiceUnit
            .personRefererCopys(UPDATED_PERSON_REFERER_COPYS)
            .subscriptionId(UPDATED_SUBSCRIPTION_ID)
            .useBillingAddress(UPDATED_USE_BILLING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD);

        restSettingsInvoiceUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingsInvoiceUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingsInvoiceUnit))
            )
            .andExpect(status().isOk());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
        SettingsInvoiceUnit testSettingsInvoiceUnit = settingsInvoiceUnitList.get(settingsInvoiceUnitList.size() - 1);
        assertThat(testSettingsInvoiceUnit.getSeller()).isEqualTo(DEFAULT_SELLER);
        assertThat(testSettingsInvoiceUnit.getPersonBuyerId()).isEqualTo(DEFAULT_PERSON_BUYER_ID);
        assertThat(testSettingsInvoiceUnit.getPersonRefererCopys()).isEqualTo(UPDATED_PERSON_REFERER_COPYS);
        assertThat(testSettingsInvoiceUnit.getSubscriptionId()).isEqualTo(UPDATED_SUBSCRIPTION_ID);
        assertThat(testSettingsInvoiceUnit.getUseBillingAddress()).isEqualTo(UPDATED_USE_BILLING_ADDRESS);
        assertThat(testSettingsInvoiceUnit.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
    }

    @Test
    void fullUpdateSettingsInvoiceUnitWithPatch() throws Exception {
        // Initialize the database
        settingsInvoiceUnitRepository.save(settingsInvoiceUnit);

        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();

        // Update the settingsInvoiceUnit using partial update
        SettingsInvoiceUnit partialUpdatedSettingsInvoiceUnit = new SettingsInvoiceUnit();
        partialUpdatedSettingsInvoiceUnit.setId(settingsInvoiceUnit.getId());

        partialUpdatedSettingsInvoiceUnit
            .seller(UPDATED_SELLER)
            .personBuyerId(UPDATED_PERSON_BUYER_ID)
            .personRefererCopys(UPDATED_PERSON_REFERER_COPYS)
            .subscriptionId(UPDATED_SUBSCRIPTION_ID)
            .useBillingAddress(UPDATED_USE_BILLING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD);

        restSettingsInvoiceUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingsInvoiceUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingsInvoiceUnit))
            )
            .andExpect(status().isOk());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
        SettingsInvoiceUnit testSettingsInvoiceUnit = settingsInvoiceUnitList.get(settingsInvoiceUnitList.size() - 1);
        assertThat(testSettingsInvoiceUnit.getSeller()).isEqualTo(UPDATED_SELLER);
        assertThat(testSettingsInvoiceUnit.getPersonBuyerId()).isEqualTo(UPDATED_PERSON_BUYER_ID);
        assertThat(testSettingsInvoiceUnit.getPersonRefererCopys()).isEqualTo(UPDATED_PERSON_REFERER_COPYS);
        assertThat(testSettingsInvoiceUnit.getSubscriptionId()).isEqualTo(UPDATED_SUBSCRIPTION_ID);
        assertThat(testSettingsInvoiceUnit.getUseBillingAddress()).isEqualTo(UPDATED_USE_BILLING_ADDRESS);
        assertThat(testSettingsInvoiceUnit.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
    }

    @Test
    void patchNonExistingSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();
        settingsInvoiceUnit.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingsInvoiceUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, settingsInvoiceUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();
        settingsInvoiceUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSettingsInvoiceUnit() throws Exception {
        int databaseSizeBeforeUpdate = settingsInvoiceUnitRepository.findAll().size();
        settingsInvoiceUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsInvoiceUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsInvoiceUnit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SettingsInvoiceUnit in the database
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSettingsInvoiceUnit() throws Exception {
        // Initialize the database
        settingsInvoiceUnitRepository.save(settingsInvoiceUnit);

        int databaseSizeBeforeDelete = settingsInvoiceUnitRepository.findAll().size();

        // Delete the settingsInvoiceUnit
        restSettingsInvoiceUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, settingsInvoiceUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SettingsInvoiceUnit> settingsInvoiceUnitList = settingsInvoiceUnitRepository.findAll();
        assertThat(settingsInvoiceUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
