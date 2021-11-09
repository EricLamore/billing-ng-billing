package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.ProductRatePlanCharge;
import com.universign.universigncs.billing.repository.ProductRatePlanChargeRepository;
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
 * Integration tests for the {@link ProductRatePlanChargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductRatePlanChargeResourceIT {

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/product-rate-plan-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductRatePlanChargeRepository productRatePlanChargeRepository;

    @Autowired
    private MockMvc restProductRatePlanChargeMockMvc;

    private ProductRatePlanCharge productRatePlanCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductRatePlanCharge createEntity() {
        ProductRatePlanCharge productRatePlanCharge = new ProductRatePlanCharge().step(DEFAULT_STEP).unitPrice(DEFAULT_UNIT_PRICE);
        return productRatePlanCharge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductRatePlanCharge createUpdatedEntity() {
        ProductRatePlanCharge productRatePlanCharge = new ProductRatePlanCharge().step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE);
        return productRatePlanCharge;
    }

    @BeforeEach
    public void initTest() {
        productRatePlanChargeRepository.deleteAll();
        productRatePlanCharge = createEntity();
    }

    @Test
    void createProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeCreate = productRatePlanChargeRepository.findAll().size();
        // Create the ProductRatePlanCharge
        restProductRatePlanChargeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isCreated());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductRatePlanCharge testProductRatePlanCharge = productRatePlanChargeList.get(productRatePlanChargeList.size() - 1);
        assertThat(testProductRatePlanCharge.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testProductRatePlanCharge.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    void createProductRatePlanChargeWithExistingId() throws Exception {
        // Create the ProductRatePlanCharge with an existing ID
        productRatePlanCharge.setId("existing_id");

        int databaseSizeBeforeCreate = productRatePlanChargeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductRatePlanChargeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductRatePlanCharges() throws Exception {
        // Initialize the database
        productRatePlanChargeRepository.save(productRatePlanCharge);

        // Get all the productRatePlanChargeList
        restProductRatePlanChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productRatePlanCharge.getId())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())));
    }

    @Test
    void getProductRatePlanCharge() throws Exception {
        // Initialize the database
        productRatePlanChargeRepository.save(productRatePlanCharge);

        // Get the productRatePlanCharge
        restProductRatePlanChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, productRatePlanCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productRatePlanCharge.getId()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()));
    }

    @Test
    void getNonExistingProductRatePlanCharge() throws Exception {
        // Get the productRatePlanCharge
        restProductRatePlanChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewProductRatePlanCharge() throws Exception {
        // Initialize the database
        productRatePlanChargeRepository.save(productRatePlanCharge);

        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();

        // Update the productRatePlanCharge
        ProductRatePlanCharge updatedProductRatePlanCharge = productRatePlanChargeRepository.findById(productRatePlanCharge.getId()).get();
        updatedProductRatePlanCharge.step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE);

        restProductRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductRatePlanCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductRatePlanCharge))
            )
            .andExpect(status().isOk());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
        ProductRatePlanCharge testProductRatePlanCharge = productRatePlanChargeList.get(productRatePlanChargeList.size() - 1);
        assertThat(testProductRatePlanCharge.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testProductRatePlanCharge.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    void putNonExistingProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();
        productRatePlanCharge.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productRatePlanCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();
        productRatePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();
        productRatePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanChargeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductRatePlanChargeWithPatch() throws Exception {
        // Initialize the database
        productRatePlanChargeRepository.save(productRatePlanCharge);

        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();

        // Update the productRatePlanCharge using partial update
        ProductRatePlanCharge partialUpdatedProductRatePlanCharge = new ProductRatePlanCharge();
        partialUpdatedProductRatePlanCharge.setId(productRatePlanCharge.getId());

        partialUpdatedProductRatePlanCharge.step(UPDATED_STEP);

        restProductRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductRatePlanCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductRatePlanCharge))
            )
            .andExpect(status().isOk());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
        ProductRatePlanCharge testProductRatePlanCharge = productRatePlanChargeList.get(productRatePlanChargeList.size() - 1);
        assertThat(testProductRatePlanCharge.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testProductRatePlanCharge.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    void fullUpdateProductRatePlanChargeWithPatch() throws Exception {
        // Initialize the database
        productRatePlanChargeRepository.save(productRatePlanCharge);

        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();

        // Update the productRatePlanCharge using partial update
        ProductRatePlanCharge partialUpdatedProductRatePlanCharge = new ProductRatePlanCharge();
        partialUpdatedProductRatePlanCharge.setId(productRatePlanCharge.getId());

        partialUpdatedProductRatePlanCharge.step(UPDATED_STEP).unitPrice(UPDATED_UNIT_PRICE);

        restProductRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductRatePlanCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductRatePlanCharge))
            )
            .andExpect(status().isOk());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
        ProductRatePlanCharge testProductRatePlanCharge = productRatePlanChargeList.get(productRatePlanChargeList.size() - 1);
        assertThat(testProductRatePlanCharge.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testProductRatePlanCharge.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    void patchNonExistingProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();
        productRatePlanCharge.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productRatePlanCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();
        productRatePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductRatePlanCharge() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanChargeRepository.findAll().size();
        productRatePlanCharge.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanChargeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlanCharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductRatePlanCharge in the database
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductRatePlanCharge() throws Exception {
        // Initialize the database
        productRatePlanChargeRepository.save(productRatePlanCharge);

        int databaseSizeBeforeDelete = productRatePlanChargeRepository.findAll().size();

        // Delete the productRatePlanCharge
        restProductRatePlanChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, productRatePlanCharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductRatePlanCharge> productRatePlanChargeList = productRatePlanChargeRepository.findAll();
        assertThat(productRatePlanChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
