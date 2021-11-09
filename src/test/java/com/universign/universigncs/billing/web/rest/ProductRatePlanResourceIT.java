package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.ProductRatePlan;
import com.universign.universigncs.billing.repository.ProductRatePlanRepository;
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
 * Integration tests for the {@link ProductRatePlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductRatePlanResourceIT {

    private static final String DEFAULT_COMMERCIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMERCIAL_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_BASE = 1D;
    private static final Double UPDATED_BASE = 2D;

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_RATE_PLAN_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_RATE_PLAN_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_FEATURES = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FIXED_PRICE = false;
    private static final Boolean UPDATED_FIXED_PRICE = true;

    private static final Boolean DEFAULT_STANDARD_MODEL = false;
    private static final Boolean UPDATED_STANDARD_MODEL = true;

    private static final Integer DEFAULT_UNITS_INCLUDED = 1;
    private static final Integer UPDATED_UNITS_INCLUDED = 2;

    private static final Double DEFAULT_UNITS_INCLUDED_PRICE = 1D;
    private static final Double UPDATED_UNITS_INCLUDED_PRICE = 2D;

    private static final Integer DEFAULT_UNITS_INCLUDED_DURATION = 1;
    private static final Integer UPDATED_UNITS_INCLUDED_DURATION = 2;

    private static final String ENTITY_API_URL = "/api/product-rate-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductRatePlanRepository productRatePlanRepository;

    @Autowired
    private MockMvc restProductRatePlanMockMvc;

    private ProductRatePlan productRatePlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductRatePlan createEntity() {
        ProductRatePlan productRatePlan = new ProductRatePlan()
            .commercialName(DEFAULT_COMMERCIAL_NAME)
            .base(DEFAULT_BASE)
            .product(DEFAULT_PRODUCT)
            .productRatePlanCharge(DEFAULT_PRODUCT_RATE_PLAN_CHARGE)
            .features(DEFAULT_FEATURES)
            .version(DEFAULT_VERSION)
            .fixedPrice(DEFAULT_FIXED_PRICE)
            .standardModel(DEFAULT_STANDARD_MODEL)
            .unitsIncluded(DEFAULT_UNITS_INCLUDED)
            .unitsIncludedPrice(DEFAULT_UNITS_INCLUDED_PRICE)
            .unitsIncludedDuration(DEFAULT_UNITS_INCLUDED_DURATION);
        return productRatePlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductRatePlan createUpdatedEntity() {
        ProductRatePlan productRatePlan = new ProductRatePlan()
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .base(UPDATED_BASE)
            .product(UPDATED_PRODUCT)
            .productRatePlanCharge(UPDATED_PRODUCT_RATE_PLAN_CHARGE)
            .features(UPDATED_FEATURES)
            .version(UPDATED_VERSION)
            .fixedPrice(UPDATED_FIXED_PRICE)
            .standardModel(UPDATED_STANDARD_MODEL)
            .unitsIncluded(UPDATED_UNITS_INCLUDED)
            .unitsIncludedPrice(UPDATED_UNITS_INCLUDED_PRICE)
            .unitsIncludedDuration(UPDATED_UNITS_INCLUDED_DURATION);
        return productRatePlan;
    }

    @BeforeEach
    public void initTest() {
        productRatePlanRepository.deleteAll();
        productRatePlan = createEntity();
    }

    @Test
    void createProductRatePlan() throws Exception {
        int databaseSizeBeforeCreate = productRatePlanRepository.findAll().size();
        // Create the ProductRatePlan
        restProductRatePlanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isCreated());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeCreate + 1);
        ProductRatePlan testProductRatePlan = productRatePlanList.get(productRatePlanList.size() - 1);
        assertThat(testProductRatePlan.getCommercialName()).isEqualTo(DEFAULT_COMMERCIAL_NAME);
        assertThat(testProductRatePlan.getBase()).isEqualTo(DEFAULT_BASE);
        assertThat(testProductRatePlan.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testProductRatePlan.getProductRatePlanCharge()).isEqualTo(DEFAULT_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testProductRatePlan.getFeatures()).isEqualTo(DEFAULT_FEATURES);
        assertThat(testProductRatePlan.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProductRatePlan.getFixedPrice()).isEqualTo(DEFAULT_FIXED_PRICE);
        assertThat(testProductRatePlan.getStandardModel()).isEqualTo(DEFAULT_STANDARD_MODEL);
        assertThat(testProductRatePlan.getUnitsIncluded()).isEqualTo(DEFAULT_UNITS_INCLUDED);
        assertThat(testProductRatePlan.getUnitsIncludedPrice()).isEqualTo(DEFAULT_UNITS_INCLUDED_PRICE);
        assertThat(testProductRatePlan.getUnitsIncludedDuration()).isEqualTo(DEFAULT_UNITS_INCLUDED_DURATION);
    }

    @Test
    void createProductRatePlanWithExistingId() throws Exception {
        // Create the ProductRatePlan with an existing ID
        productRatePlan.setId("existing_id");

        int databaseSizeBeforeCreate = productRatePlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductRatePlanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductRatePlans() throws Exception {
        // Initialize the database
        productRatePlanRepository.save(productRatePlan);

        // Get all the productRatePlanList
        restProductRatePlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productRatePlan.getId())))
            .andExpect(jsonPath("$.[*].commercialName").value(hasItem(DEFAULT_COMMERCIAL_NAME)))
            .andExpect(jsonPath("$.[*].base").value(hasItem(DEFAULT_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT)))
            .andExpect(jsonPath("$.[*].productRatePlanCharge").value(hasItem(DEFAULT_PRODUCT_RATE_PLAN_CHARGE)))
            .andExpect(jsonPath("$.[*].features").value(hasItem(DEFAULT_FEATURES)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].fixedPrice").value(hasItem(DEFAULT_FIXED_PRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].standardModel").value(hasItem(DEFAULT_STANDARD_MODEL.booleanValue())))
            .andExpect(jsonPath("$.[*].unitsIncluded").value(hasItem(DEFAULT_UNITS_INCLUDED)))
            .andExpect(jsonPath("$.[*].unitsIncludedPrice").value(hasItem(DEFAULT_UNITS_INCLUDED_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitsIncludedDuration").value(hasItem(DEFAULT_UNITS_INCLUDED_DURATION)));
    }

    @Test
    void getProductRatePlan() throws Exception {
        // Initialize the database
        productRatePlanRepository.save(productRatePlan);

        // Get the productRatePlan
        restProductRatePlanMockMvc
            .perform(get(ENTITY_API_URL_ID, productRatePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productRatePlan.getId()))
            .andExpect(jsonPath("$.commercialName").value(DEFAULT_COMMERCIAL_NAME))
            .andExpect(jsonPath("$.base").value(DEFAULT_BASE.doubleValue()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT))
            .andExpect(jsonPath("$.productRatePlanCharge").value(DEFAULT_PRODUCT_RATE_PLAN_CHARGE))
            .andExpect(jsonPath("$.features").value(DEFAULT_FEATURES))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.fixedPrice").value(DEFAULT_FIXED_PRICE.booleanValue()))
            .andExpect(jsonPath("$.standardModel").value(DEFAULT_STANDARD_MODEL.booleanValue()))
            .andExpect(jsonPath("$.unitsIncluded").value(DEFAULT_UNITS_INCLUDED))
            .andExpect(jsonPath("$.unitsIncludedPrice").value(DEFAULT_UNITS_INCLUDED_PRICE.doubleValue()))
            .andExpect(jsonPath("$.unitsIncludedDuration").value(DEFAULT_UNITS_INCLUDED_DURATION));
    }

    @Test
    void getNonExistingProductRatePlan() throws Exception {
        // Get the productRatePlan
        restProductRatePlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewProductRatePlan() throws Exception {
        // Initialize the database
        productRatePlanRepository.save(productRatePlan);

        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();

        // Update the productRatePlan
        ProductRatePlan updatedProductRatePlan = productRatePlanRepository.findById(productRatePlan.getId()).get();
        updatedProductRatePlan
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .base(UPDATED_BASE)
            .product(UPDATED_PRODUCT)
            .productRatePlanCharge(UPDATED_PRODUCT_RATE_PLAN_CHARGE)
            .features(UPDATED_FEATURES)
            .version(UPDATED_VERSION)
            .fixedPrice(UPDATED_FIXED_PRICE)
            .standardModel(UPDATED_STANDARD_MODEL)
            .unitsIncluded(UPDATED_UNITS_INCLUDED)
            .unitsIncludedPrice(UPDATED_UNITS_INCLUDED_PRICE)
            .unitsIncludedDuration(UPDATED_UNITS_INCLUDED_DURATION);

        restProductRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductRatePlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductRatePlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
        ProductRatePlan testProductRatePlan = productRatePlanList.get(productRatePlanList.size() - 1);
        assertThat(testProductRatePlan.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testProductRatePlan.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testProductRatePlan.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testProductRatePlan.getProductRatePlanCharge()).isEqualTo(UPDATED_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testProductRatePlan.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testProductRatePlan.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProductRatePlan.getFixedPrice()).isEqualTo(UPDATED_FIXED_PRICE);
        assertThat(testProductRatePlan.getStandardModel()).isEqualTo(UPDATED_STANDARD_MODEL);
        assertThat(testProductRatePlan.getUnitsIncluded()).isEqualTo(UPDATED_UNITS_INCLUDED);
        assertThat(testProductRatePlan.getUnitsIncludedPrice()).isEqualTo(UPDATED_UNITS_INCLUDED_PRICE);
        assertThat(testProductRatePlan.getUnitsIncludedDuration()).isEqualTo(UPDATED_UNITS_INCLUDED_DURATION);
    }

    @Test
    void putNonExistingProductRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();
        productRatePlan.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productRatePlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();
        productRatePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();
        productRatePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductRatePlanWithPatch() throws Exception {
        // Initialize the database
        productRatePlanRepository.save(productRatePlan);

        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();

        // Update the productRatePlan using partial update
        ProductRatePlan partialUpdatedProductRatePlan = new ProductRatePlan();
        partialUpdatedProductRatePlan.setId(productRatePlan.getId());

        partialUpdatedProductRatePlan.commercialName(UPDATED_COMMERCIAL_NAME).base(UPDATED_BASE).features(UPDATED_FEATURES);

        restProductRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductRatePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductRatePlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
        ProductRatePlan testProductRatePlan = productRatePlanList.get(productRatePlanList.size() - 1);
        assertThat(testProductRatePlan.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testProductRatePlan.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testProductRatePlan.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testProductRatePlan.getProductRatePlanCharge()).isEqualTo(DEFAULT_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testProductRatePlan.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testProductRatePlan.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProductRatePlan.getFixedPrice()).isEqualTo(DEFAULT_FIXED_PRICE);
        assertThat(testProductRatePlan.getStandardModel()).isEqualTo(DEFAULT_STANDARD_MODEL);
        assertThat(testProductRatePlan.getUnitsIncluded()).isEqualTo(DEFAULT_UNITS_INCLUDED);
        assertThat(testProductRatePlan.getUnitsIncludedPrice()).isEqualTo(DEFAULT_UNITS_INCLUDED_PRICE);
        assertThat(testProductRatePlan.getUnitsIncludedDuration()).isEqualTo(DEFAULT_UNITS_INCLUDED_DURATION);
    }

    @Test
    void fullUpdateProductRatePlanWithPatch() throws Exception {
        // Initialize the database
        productRatePlanRepository.save(productRatePlan);

        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();

        // Update the productRatePlan using partial update
        ProductRatePlan partialUpdatedProductRatePlan = new ProductRatePlan();
        partialUpdatedProductRatePlan.setId(productRatePlan.getId());

        partialUpdatedProductRatePlan
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .base(UPDATED_BASE)
            .product(UPDATED_PRODUCT)
            .productRatePlanCharge(UPDATED_PRODUCT_RATE_PLAN_CHARGE)
            .features(UPDATED_FEATURES)
            .version(UPDATED_VERSION)
            .fixedPrice(UPDATED_FIXED_PRICE)
            .standardModel(UPDATED_STANDARD_MODEL)
            .unitsIncluded(UPDATED_UNITS_INCLUDED)
            .unitsIncludedPrice(UPDATED_UNITS_INCLUDED_PRICE)
            .unitsIncludedDuration(UPDATED_UNITS_INCLUDED_DURATION);

        restProductRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductRatePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductRatePlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
        ProductRatePlan testProductRatePlan = productRatePlanList.get(productRatePlanList.size() - 1);
        assertThat(testProductRatePlan.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testProductRatePlan.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testProductRatePlan.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testProductRatePlan.getProductRatePlanCharge()).isEqualTo(UPDATED_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testProductRatePlan.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testProductRatePlan.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProductRatePlan.getFixedPrice()).isEqualTo(UPDATED_FIXED_PRICE);
        assertThat(testProductRatePlan.getStandardModel()).isEqualTo(UPDATED_STANDARD_MODEL);
        assertThat(testProductRatePlan.getUnitsIncluded()).isEqualTo(UPDATED_UNITS_INCLUDED);
        assertThat(testProductRatePlan.getUnitsIncludedPrice()).isEqualTo(UPDATED_UNITS_INCLUDED_PRICE);
        assertThat(testProductRatePlan.getUnitsIncludedDuration()).isEqualTo(UPDATED_UNITS_INCLUDED_DURATION);
    }

    @Test
    void patchNonExistingProductRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();
        productRatePlan.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productRatePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();
        productRatePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = productRatePlanRepository.findAll().size();
        productRatePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productRatePlan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductRatePlan in the database
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductRatePlan() throws Exception {
        // Initialize the database
        productRatePlanRepository.save(productRatePlan);

        int databaseSizeBeforeDelete = productRatePlanRepository.findAll().size();

        // Delete the productRatePlan
        restProductRatePlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, productRatePlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductRatePlan> productRatePlanList = productRatePlanRepository.findAll();
        assertThat(productRatePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
