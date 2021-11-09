package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.RatePlan;
import com.universign.universigncs.billing.repository.RatePlanRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RatePlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatePlanResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String DEFAULT_RATE_PLAN_ID = "AAAAAAAAAA";
    private static final String UPDATED_RATE_PLAN_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT_UNIT_PRICE = 1D;
    private static final Double UPDATED_DISCOUNT_UNIT_PRICE = 2D;

    private static final Double DEFAULT_DISCOUNT_BASE = 1D;
    private static final Double UPDATED_DISCOUNT_BASE = 2D;

    private static final Double DEFAULT_PRORATA = 1D;
    private static final Double UPDATED_PRORATA = 2D;

    private static final LocalDate DEFAULT_ACTIVATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RATE_PLAN_CHARGES = "AAAAAAAAAA";
    private static final String UPDATED_RATE_PLAN_CHARGES = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTION_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_FEATURES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rate-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private MockMvc restRatePlanMockMvc;

    private RatePlan ratePlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatePlan createEntity() {
        RatePlan ratePlan = new RatePlan()
            .name(DEFAULT_NAME)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .description(DEFAULT_DESCRIPTION)
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
            .unitsIncludedDuration(DEFAULT_UNITS_INCLUDED_DURATION)
            .ratePlanId(DEFAULT_RATE_PLAN_ID)
            .discountUnitPrice(DEFAULT_DISCOUNT_UNIT_PRICE)
            .discountBase(DEFAULT_DISCOUNT_BASE)
            .prorata(DEFAULT_PRORATA)
            .activationDate(DEFAULT_ACTIVATION_DATE)
            .endDate(DEFAULT_END_DATE)
            .ratePlanCharges(DEFAULT_RATE_PLAN_CHARGES)
            .subscriptionFeatures(DEFAULT_SUBSCRIPTION_FEATURES);
        return ratePlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatePlan createUpdatedEntity() {
        RatePlan ratePlan = new RatePlan()
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
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
            .unitsIncludedDuration(UPDATED_UNITS_INCLUDED_DURATION)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .discountUnitPrice(UPDATED_DISCOUNT_UNIT_PRICE)
            .discountBase(UPDATED_DISCOUNT_BASE)
            .prorata(UPDATED_PRORATA)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .endDate(UPDATED_END_DATE)
            .ratePlanCharges(UPDATED_RATE_PLAN_CHARGES)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES);
        return ratePlan;
    }

    @BeforeEach
    public void initTest() {
        ratePlanRepository.deleteAll();
        ratePlan = createEntity();
    }

    @Test
    void createRatePlan() throws Exception {
        int databaseSizeBeforeCreate = ratePlanRepository.findAll().size();
        // Create the RatePlan
        restRatePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratePlan)))
            .andExpect(status().isCreated());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeCreate + 1);
        RatePlan testRatePlan = ratePlanList.get(ratePlanList.size() - 1);
        assertThat(testRatePlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRatePlan.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testRatePlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRatePlan.getCommercialName()).isEqualTo(DEFAULT_COMMERCIAL_NAME);
        assertThat(testRatePlan.getBase()).isEqualTo(DEFAULT_BASE);
        assertThat(testRatePlan.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testRatePlan.getProductRatePlanCharge()).isEqualTo(DEFAULT_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testRatePlan.getFeatures()).isEqualTo(DEFAULT_FEATURES);
        assertThat(testRatePlan.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testRatePlan.getFixedPrice()).isEqualTo(DEFAULT_FIXED_PRICE);
        assertThat(testRatePlan.getStandardModel()).isEqualTo(DEFAULT_STANDARD_MODEL);
        assertThat(testRatePlan.getUnitsIncluded()).isEqualTo(DEFAULT_UNITS_INCLUDED);
        assertThat(testRatePlan.getUnitsIncludedPrice()).isEqualTo(DEFAULT_UNITS_INCLUDED_PRICE);
        assertThat(testRatePlan.getUnitsIncludedDuration()).isEqualTo(DEFAULT_UNITS_INCLUDED_DURATION);
        assertThat(testRatePlan.getRatePlanId()).isEqualTo(DEFAULT_RATE_PLAN_ID);
        assertThat(testRatePlan.getDiscountUnitPrice()).isEqualTo(DEFAULT_DISCOUNT_UNIT_PRICE);
        assertThat(testRatePlan.getDiscountBase()).isEqualTo(DEFAULT_DISCOUNT_BASE);
        assertThat(testRatePlan.getProrata()).isEqualTo(DEFAULT_PRORATA);
        assertThat(testRatePlan.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testRatePlan.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatePlan.getRatePlanCharges()).isEqualTo(DEFAULT_RATE_PLAN_CHARGES);
        assertThat(testRatePlan.getSubscriptionFeatures()).isEqualTo(DEFAULT_SUBSCRIPTION_FEATURES);
    }

    @Test
    void createRatePlanWithExistingId() throws Exception {
        // Create the RatePlan with an existing ID
        ratePlan.setId("existing_id");

        int databaseSizeBeforeCreate = ratePlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratePlan)))
            .andExpect(status().isBadRequest());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRatePlans() throws Exception {
        // Initialize the database
        ratePlanRepository.save(ratePlan);

        // Get all the ratePlanList
        restRatePlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratePlan.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
            .andExpect(jsonPath("$.[*].unitsIncludedDuration").value(hasItem(DEFAULT_UNITS_INCLUDED_DURATION)))
            .andExpect(jsonPath("$.[*].ratePlanId").value(hasItem(DEFAULT_RATE_PLAN_ID)))
            .andExpect(jsonPath("$.[*].discountUnitPrice").value(hasItem(DEFAULT_DISCOUNT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountBase").value(hasItem(DEFAULT_DISCOUNT_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].prorata").value(hasItem(DEFAULT_PRORATA.doubleValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].ratePlanCharges").value(hasItem(DEFAULT_RATE_PLAN_CHARGES)))
            .andExpect(jsonPath("$.[*].subscriptionFeatures").value(hasItem(DEFAULT_SUBSCRIPTION_FEATURES)));
    }

    @Test
    void getRatePlan() throws Exception {
        // Initialize the database
        ratePlanRepository.save(ratePlan);

        // Get the ratePlan
        restRatePlanMockMvc
            .perform(get(ENTITY_API_URL_ID, ratePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratePlan.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
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
            .andExpect(jsonPath("$.unitsIncludedDuration").value(DEFAULT_UNITS_INCLUDED_DURATION))
            .andExpect(jsonPath("$.ratePlanId").value(DEFAULT_RATE_PLAN_ID))
            .andExpect(jsonPath("$.discountUnitPrice").value(DEFAULT_DISCOUNT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discountBase").value(DEFAULT_DISCOUNT_BASE.doubleValue()))
            .andExpect(jsonPath("$.prorata").value(DEFAULT_PRORATA.doubleValue()))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.ratePlanCharges").value(DEFAULT_RATE_PLAN_CHARGES))
            .andExpect(jsonPath("$.subscriptionFeatures").value(DEFAULT_SUBSCRIPTION_FEATURES));
    }

    @Test
    void getNonExistingRatePlan() throws Exception {
        // Get the ratePlan
        restRatePlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewRatePlan() throws Exception {
        // Initialize the database
        ratePlanRepository.save(ratePlan);

        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();

        // Update the ratePlan
        RatePlan updatedRatePlan = ratePlanRepository.findById(ratePlan.getId()).get();
        updatedRatePlan
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
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
            .unitsIncludedDuration(UPDATED_UNITS_INCLUDED_DURATION)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .discountUnitPrice(UPDATED_DISCOUNT_UNIT_PRICE)
            .discountBase(UPDATED_DISCOUNT_BASE)
            .prorata(UPDATED_PRORATA)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .endDate(UPDATED_END_DATE)
            .ratePlanCharges(UPDATED_RATE_PLAN_CHARGES)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES);

        restRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatePlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatePlan))
            )
            .andExpect(status().isOk());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
        RatePlan testRatePlan = ratePlanList.get(ratePlanList.size() - 1);
        assertThat(testRatePlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatePlan.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testRatePlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRatePlan.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testRatePlan.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testRatePlan.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testRatePlan.getProductRatePlanCharge()).isEqualTo(UPDATED_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testRatePlan.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testRatePlan.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testRatePlan.getFixedPrice()).isEqualTo(UPDATED_FIXED_PRICE);
        assertThat(testRatePlan.getStandardModel()).isEqualTo(UPDATED_STANDARD_MODEL);
        assertThat(testRatePlan.getUnitsIncluded()).isEqualTo(UPDATED_UNITS_INCLUDED);
        assertThat(testRatePlan.getUnitsIncludedPrice()).isEqualTo(UPDATED_UNITS_INCLUDED_PRICE);
        assertThat(testRatePlan.getUnitsIncludedDuration()).isEqualTo(UPDATED_UNITS_INCLUDED_DURATION);
        assertThat(testRatePlan.getRatePlanId()).isEqualTo(UPDATED_RATE_PLAN_ID);
        assertThat(testRatePlan.getDiscountUnitPrice()).isEqualTo(UPDATED_DISCOUNT_UNIT_PRICE);
        assertThat(testRatePlan.getDiscountBase()).isEqualTo(UPDATED_DISCOUNT_BASE);
        assertThat(testRatePlan.getProrata()).isEqualTo(UPDATED_PRORATA);
        assertThat(testRatePlan.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testRatePlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatePlan.getRatePlanCharges()).isEqualTo(UPDATED_RATE_PLAN_CHARGES);
        assertThat(testRatePlan.getSubscriptionFeatures()).isEqualTo(UPDATED_SUBSCRIPTION_FEATURES);
    }

    @Test
    void putNonExistingRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();
        ratePlan.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratePlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();
        ratePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();
        ratePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratePlan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRatePlanWithPatch() throws Exception {
        // Initialize the database
        ratePlanRepository.save(ratePlan);

        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();

        // Update the ratePlan using partial update
        RatePlan partialUpdatedRatePlan = new RatePlan();
        partialUpdatedRatePlan.setId(ratePlan.getId());

        partialUpdatedRatePlan
            .lastUpdate(UPDATED_LAST_UPDATE)
            .base(UPDATED_BASE)
            .product(UPDATED_PRODUCT)
            .productRatePlanCharge(UPDATED_PRODUCT_RATE_PLAN_CHARGE)
            .unitsIncluded(UPDATED_UNITS_INCLUDED)
            .prorata(UPDATED_PRORATA)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES);

        restRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatePlan))
            )
            .andExpect(status().isOk());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
        RatePlan testRatePlan = ratePlanList.get(ratePlanList.size() - 1);
        assertThat(testRatePlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRatePlan.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testRatePlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRatePlan.getCommercialName()).isEqualTo(DEFAULT_COMMERCIAL_NAME);
        assertThat(testRatePlan.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testRatePlan.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testRatePlan.getProductRatePlanCharge()).isEqualTo(UPDATED_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testRatePlan.getFeatures()).isEqualTo(DEFAULT_FEATURES);
        assertThat(testRatePlan.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testRatePlan.getFixedPrice()).isEqualTo(DEFAULT_FIXED_PRICE);
        assertThat(testRatePlan.getStandardModel()).isEqualTo(DEFAULT_STANDARD_MODEL);
        assertThat(testRatePlan.getUnitsIncluded()).isEqualTo(UPDATED_UNITS_INCLUDED);
        assertThat(testRatePlan.getUnitsIncludedPrice()).isEqualTo(DEFAULT_UNITS_INCLUDED_PRICE);
        assertThat(testRatePlan.getUnitsIncludedDuration()).isEqualTo(DEFAULT_UNITS_INCLUDED_DURATION);
        assertThat(testRatePlan.getRatePlanId()).isEqualTo(DEFAULT_RATE_PLAN_ID);
        assertThat(testRatePlan.getDiscountUnitPrice()).isEqualTo(DEFAULT_DISCOUNT_UNIT_PRICE);
        assertThat(testRatePlan.getDiscountBase()).isEqualTo(DEFAULT_DISCOUNT_BASE);
        assertThat(testRatePlan.getProrata()).isEqualTo(UPDATED_PRORATA);
        assertThat(testRatePlan.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testRatePlan.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRatePlan.getRatePlanCharges()).isEqualTo(DEFAULT_RATE_PLAN_CHARGES);
        assertThat(testRatePlan.getSubscriptionFeatures()).isEqualTo(UPDATED_SUBSCRIPTION_FEATURES);
    }

    @Test
    void fullUpdateRatePlanWithPatch() throws Exception {
        // Initialize the database
        ratePlanRepository.save(ratePlan);

        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();

        // Update the ratePlan using partial update
        RatePlan partialUpdatedRatePlan = new RatePlan();
        partialUpdatedRatePlan.setId(ratePlan.getId());

        partialUpdatedRatePlan
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
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
            .unitsIncludedDuration(UPDATED_UNITS_INCLUDED_DURATION)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .discountUnitPrice(UPDATED_DISCOUNT_UNIT_PRICE)
            .discountBase(UPDATED_DISCOUNT_BASE)
            .prorata(UPDATED_PRORATA)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .endDate(UPDATED_END_DATE)
            .ratePlanCharges(UPDATED_RATE_PLAN_CHARGES)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES);

        restRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatePlan))
            )
            .andExpect(status().isOk());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
        RatePlan testRatePlan = ratePlanList.get(ratePlanList.size() - 1);
        assertThat(testRatePlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRatePlan.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testRatePlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRatePlan.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testRatePlan.getBase()).isEqualTo(UPDATED_BASE);
        assertThat(testRatePlan.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testRatePlan.getProductRatePlanCharge()).isEqualTo(UPDATED_PRODUCT_RATE_PLAN_CHARGE);
        assertThat(testRatePlan.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testRatePlan.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testRatePlan.getFixedPrice()).isEqualTo(UPDATED_FIXED_PRICE);
        assertThat(testRatePlan.getStandardModel()).isEqualTo(UPDATED_STANDARD_MODEL);
        assertThat(testRatePlan.getUnitsIncluded()).isEqualTo(UPDATED_UNITS_INCLUDED);
        assertThat(testRatePlan.getUnitsIncludedPrice()).isEqualTo(UPDATED_UNITS_INCLUDED_PRICE);
        assertThat(testRatePlan.getUnitsIncludedDuration()).isEqualTo(UPDATED_UNITS_INCLUDED_DURATION);
        assertThat(testRatePlan.getRatePlanId()).isEqualTo(UPDATED_RATE_PLAN_ID);
        assertThat(testRatePlan.getDiscountUnitPrice()).isEqualTo(UPDATED_DISCOUNT_UNIT_PRICE);
        assertThat(testRatePlan.getDiscountBase()).isEqualTo(UPDATED_DISCOUNT_BASE);
        assertThat(testRatePlan.getProrata()).isEqualTo(UPDATED_PRORATA);
        assertThat(testRatePlan.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testRatePlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRatePlan.getRatePlanCharges()).isEqualTo(UPDATED_RATE_PLAN_CHARGES);
        assertThat(testRatePlan.getSubscriptionFeatures()).isEqualTo(UPDATED_SUBSCRIPTION_FEATURES);
    }

    @Test
    void patchNonExistingRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();
        ratePlan.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();
        ratePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratePlan))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRatePlan() throws Exception {
        int databaseSizeBeforeUpdate = ratePlanRepository.findAll().size();
        ratePlan.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatePlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ratePlan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatePlan in the database
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRatePlan() throws Exception {
        // Initialize the database
        ratePlanRepository.save(ratePlan);

        int databaseSizeBeforeDelete = ratePlanRepository.findAll().size();

        // Delete the ratePlan
        restRatePlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratePlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatePlan> ratePlanList = ratePlanRepository.findAll();
        assertThat(ratePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
