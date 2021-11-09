package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.RatePlanCharge;
import com.universign.universigncs.billing.repository.RatePlanChargeRepository;
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
 * Integration tests for the {@link RatePlanChargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatePlanChargeResourceIT {

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/rate-plan-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RatePlanChargeRepository ratePlanChargeRepository;

    @Autowired
    private MockMvc restRatePlanChargeMockMvc;

    private RatePlanCharge ratePlanCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatePlanCharge createEntity() {
        RatePlanCharge ratePlanCharge = new RatePlanCharge().step(DEFAULT_STEP).unitPrice(DEFAULT_UNIT_PRICE).discount(DEFAULT_DISCOUNT);
        return ratePlanCharge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatePlanCharge createUpdatedEntity() {
        RatePlanCharge ratePlanCharge = new RatePlanCharge().step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE).discount(UPDATED_DISCOUNT);
        return ratePlanCharge;
    }

    @BeforeEach
    public void initTest() {
        ratePlanChargeRepository.deleteAll();
        ratePlanCharge = createEntity();
    }

    @Test
    void createRatePlanCharge() throws Exception {
        int databaseSizeBeforeCreate = ratePlanChargeRepository.findAll().size();
        // Create the RatePlanCharge
        restRatePlanChargeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isCreated());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeCreate + 1);
        RatePlanCharge testRatePlanCharge = ratePlanChargeList.get(ratePlanChargeList.size() - 1);
        assertThat(testRatePlanCharge.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testRatePlanCharge.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testRatePlanCharge.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    void createRatePlanChargeWithExistingId() throws Exception {
        // Create the RatePlanCharge with an existing ID
        ratePlanCharge.setId("existing_id");

        int databaseSizeBeforeCreate = ratePlanChargeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatePlanChargeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRatePlanCharges() throws Exception {
        // Initialize the database
        ratePlanChargeRepository.save(ratePlanCharge);

        // Get all the ratePlanChargeList
        restRatePlanChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratePlanCharge.getId())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }

    @Test
    void getRatePlanCharge() throws Exception {
        // Initialize the database
        ratePlanChargeRepository.save(ratePlanCharge);

        // Get the ratePlanCharge
        restRatePlanChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, ratePlanCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratePlanCharge.getId()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    void getNonExistingRatePlanCharge() throws Exception {
        // Get the ratePlanCharge
        restRatePlanChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewRatePlanCharge() throws Exception {
        // Initialize the database
        ratePlanChargeRepository.save(ratePlanCharge);

        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();

        // Update the ratePlanCharge
        RatePlanCharge updatedRatePlanCharge = ratePlanChargeRepository.findById(ratePlanCharge.getId()).get();
        updatedRatePlanCharge.step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE).discount(UPDATED_DISCOUNT);

        restRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatePlanCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatePlanCharge))
            )
            .andExpect(status().isOk());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
        RatePlanCharge testRatePlanCharge = ratePlanChargeList.get(ratePlanChargeList.size() - 1);
        assertThat(testRatePlanCharge.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testRatePlanCharge.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testRatePlanCharge.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    void putNonExistingRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();
        ratePlanCharge.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratePlanCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();
        ratePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();
        ratePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanChargeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratePlanCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRatePlanChargeWithPatch() throws Exception {
        // Initialize the database
        ratePlanChargeRepository.save(ratePlanCharge);

        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();

        // Update the ratePlanCharge using partial update
        RatePlanCharge partialUpdatedRatePlanCharge = new RatePlanCharge();
        partialUpdatedRatePlanCharge.setId(ratePlanCharge.getId());

        partialUpdatedRatePlanCharge.step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE).discount(UPDATED_DISCOUNT);

        restRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatePlanCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatePlanCharge))
            )
            .andExpect(status().isOk());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
        RatePlanCharge testRatePlanCharge = ratePlanChargeList.get(ratePlanChargeList.size() - 1);
        assertThat(testRatePlanCharge.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testRatePlanCharge.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testRatePlanCharge.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    void fullUpdateRatePlanChargeWithPatch() throws Exception {
        // Initialize the database
        ratePlanChargeRepository.save(ratePlanCharge);

        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();

        // Update the ratePlanCharge using partial update
        RatePlanCharge partialUpdatedRatePlanCharge = new RatePlanCharge();
        partialUpdatedRatePlanCharge.setId(ratePlanCharge.getId());

        partialUpdatedRatePlanCharge.step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE).discount(UPDATED_DISCOUNT);

        restRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatePlanCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatePlanCharge))
            )
            .andExpect(status().isOk());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
        RatePlanCharge testRatePlanCharge = ratePlanChargeList.get(ratePlanChargeList.size() - 1);
        assertThat(testRatePlanCharge.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testRatePlanCharge.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testRatePlanCharge.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    void patchNonExistingRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();
        ratePlanCharge.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratePlanCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();
        ratePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanChargeRepository.findAll().size();
        ratePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ratePlanCharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatePlanCharge in the database
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRatePlanCharge() throws Exception {
        // Initialize the database
        ratePlanChargeRepository.save(ratePlanCharge);

        int databaseSizeBeforeDelete = ratePlanChargeRepository.findAll().size();

        // Delete the ratePlanCharge
        restRatePlanChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratePlanCharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatePlanCharge> ratePlanChargeList = ratePlanChargeRepository.findAll();
        assertThat(ratePlanChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
