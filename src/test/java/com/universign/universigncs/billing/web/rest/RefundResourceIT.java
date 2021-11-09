package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.Refund;
import com.universign.universigncs.billing.repository.RefundRepository;
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
 * Integration tests for the {@link RefundResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefundResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final String ENTITY_API_URL = "/api/refunds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private MockMvc restRefundMockMvc;

    private Refund refund;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createEntity() {
        Refund refund = new Refund().reference(DEFAULT_REFERENCE).amount(DEFAULT_AMOUNT);
        return refund;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createUpdatedEntity() {
        Refund refund = new Refund().reference(UPDATED_REFERENCE).amount(UPDATED_AMOUNT);
        return refund;
    }

    @BeforeEach
    public void initTest() {
        refundRepository.deleteAll();
        refund = createEntity();
    }

    @Test
    void createRefund() throws Exception {
        int databaseSizeBeforeCreate = refundRepository.findAll().size();
        // Create the Refund
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isCreated());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate + 1);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testRefund.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    void createRefundWithExistingId() throws Exception {
        // Create the Refund with an existing ID
        refund.setId("existing_id");

        int databaseSizeBeforeCreate = refundRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRefunds() throws Exception {
        // Initialize the database
        refundRepository.save(refund);

        // Get all the refundList
        restRefundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refund.getId())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }

    @Test
    void getRefund() throws Exception {
        // Initialize the database
        refundRepository.save(refund);

        // Get the refund
        restRefundMockMvc
            .perform(get(ENTITY_API_URL_ID, refund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refund.getId()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    void getNonExistingRefund() throws Exception {
        // Get the refund
        restRefundMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewRefund() throws Exception {
        // Initialize the database
        refundRepository.save(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund
        Refund updatedRefund = refundRepository.findById(refund.getId()).get();
        updatedRefund.reference(UPDATED_REFERENCE).amount(UPDATED_AMOUNT);

        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRefund.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefund.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    void putNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refund.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        refundRepository.save(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund.reference(UPDATED_REFERENCE).amount(UPDATED_AMOUNT);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefund.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    void fullUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        refundRepository.save(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund.reference(UPDATED_REFERENCE).amount(UPDATED_AMOUNT);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefund.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    void patchNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRefund() throws Exception {
        // Initialize the database
        refundRepository.save(refund);

        int databaseSizeBeforeDelete = refundRepository.findAll().size();

        // Delete the refund
        restRefundMockMvc
            .perform(delete(ENTITY_API_URL_ID, refund.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
