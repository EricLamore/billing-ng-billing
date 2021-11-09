package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.PaymentMethod;
import com.universign.universigncs.billing.repository.PaymentMethodRepository;
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
 * Integration tests for the {@link PaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentMethodResourceIT {

    private static final String DEFAULT_TYPE_OF_MEAN = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_OF_MEAN = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private MockMvc restPaymentMethodMockMvc;

    private PaymentMethod paymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createEntity() {
        PaymentMethod paymentMethod = new PaymentMethod().typeOfMean(DEFAULT_TYPE_OF_MEAN).accountId(DEFAULT_ACCOUNT_ID).iban(DEFAULT_IBAN);
        return paymentMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createUpdatedEntity() {
        PaymentMethod paymentMethod = new PaymentMethod().typeOfMean(UPDATED_TYPE_OF_MEAN).accountId(UPDATED_ACCOUNT_ID).iban(UPDATED_IBAN);
        return paymentMethod;
    }

    @BeforeEach
    public void initTest() {
        paymentMethodRepository.deleteAll();
        paymentMethod = createEntity();
    }

    @Test
    void createPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().size();
        // Create the PaymentMethod
        restPaymentMethodMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethod)))
            .andExpect(status().isCreated());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getTypeOfMean()).isEqualTo(DEFAULT_TYPE_OF_MEAN);
        assertThat(testPaymentMethod.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testPaymentMethod.getIban()).isEqualTo(DEFAULT_IBAN);
    }

    @Test
    void createPaymentMethodWithExistingId() throws Exception {
        // Create the PaymentMethod with an existing ID
        paymentMethod.setId("existing_id");

        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethod)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodRepository.save(paymentMethod);

        // Get all the paymentMethodList
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethod.getId())))
            .andExpect(jsonPath("$.[*].typeOfMean").value(hasItem(DEFAULT_TYPE_OF_MEAN)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)));
    }

    @Test
    void getPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethodRepository.save(paymentMethod);

        // Get the paymentMethod
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethod.getId()))
            .andExpect(jsonPath("$.typeOfMean").value(DEFAULT_TYPE_OF_MEAN))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN));
    }

    @Test
    void getNonExistingPaymentMethod() throws Exception {
        // Get the paymentMethod
        restPaymentMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethodRepository.save(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.findById(paymentMethod.getId()).get();
        updatedPaymentMethod.typeOfMean(UPDATED_TYPE_OF_MEAN).accountId(UPDATED_ACCOUNT_ID).iban(UPDATED_IBAN);

        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getTypeOfMean()).isEqualTo(UPDATED_TYPE_OF_MEAN);
        assertThat(testPaymentMethod.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testPaymentMethod.getIban()).isEqualTo(UPDATED_IBAN);
    }

    @Test
    void putNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethod)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethodRepository.save(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod.typeOfMean(UPDATED_TYPE_OF_MEAN).accountId(UPDATED_ACCOUNT_ID);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getTypeOfMean()).isEqualTo(UPDATED_TYPE_OF_MEAN);
        assertThat(testPaymentMethod.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testPaymentMethod.getIban()).isEqualTo(DEFAULT_IBAN);
    }

    @Test
    void fullUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethodRepository.save(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod.typeOfMean(UPDATED_TYPE_OF_MEAN).accountId(UPDATED_ACCOUNT_ID).iban(UPDATED_IBAN);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getTypeOfMean()).isEqualTo(UPDATED_TYPE_OF_MEAN);
        assertThat(testPaymentMethod.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testPaymentMethod.getIban()).isEqualTo(UPDATED_IBAN);
    }

    @Test
    void patchNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentMethod))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePaymentMethod() throws Exception {
        // Initialize the database
        paymentMethodRepository.save(paymentMethod);

        int databaseSizeBeforeDelete = paymentMethodRepository.findAll().size();

        // Delete the paymentMethod
        restPaymentMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentMethod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
