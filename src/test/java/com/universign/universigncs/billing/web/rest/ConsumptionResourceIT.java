package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.Consumption;
import com.universign.universigncs.billing.domain.enumeration.TypeProduct;
import com.universign.universigncs.billing.repository.ConsumptionRepository;
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
 * Integration tests for the {@link ConsumptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsumptionResourceIT {

    private static final String DEFAULT_ORGANISATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RATE_PLAN_ID = "AAAAAAAAAA";
    private static final String UPDATED_RATE_PLAN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TypeProduct DEFAULT_TYPE = TypeProduct.SIGNATURE;
    private static final TypeProduct UPDATED_TYPE = TypeProduct.SERVER_STAMP;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_UNITS = 1;
    private static final Integer UPDATED_NB_UNITS = 2;

    private static final String ENTITY_API_URL = "/api/consumptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private MockMvc restConsumptionMockMvc;

    private Consumption consumption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consumption createEntity() {
        Consumption consumption = new Consumption()
            .organisationId(DEFAULT_ORGANISATION_ID)
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .ratePlanId(DEFAULT_RATE_PLAN_ID)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .details(DEFAULT_DETAILS)
            .nbUnits(DEFAULT_NB_UNITS);
        return consumption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consumption createUpdatedEntity() {
        Consumption consumption = new Consumption()
            .organisationId(UPDATED_ORGANISATION_ID)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .details(UPDATED_DETAILS)
            .nbUnits(UPDATED_NB_UNITS);
        return consumption;
    }

    @BeforeEach
    public void initTest() {
        consumptionRepository.deleteAll();
        consumption = createEntity();
    }

    @Test
    void createConsumption() throws Exception {
        int databaseSizeBeforeCreate = consumptionRepository.findAll().size();
        // Create the Consumption
        restConsumptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isCreated());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeCreate + 1);
        Consumption testConsumption = consumptionList.get(consumptionList.size() - 1);
        assertThat(testConsumption.getOrganisationId()).isEqualTo(DEFAULT_ORGANISATION_ID);
        assertThat(testConsumption.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testConsumption.getRatePlanId()).isEqualTo(DEFAULT_RATE_PLAN_ID);
        assertThat(testConsumption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConsumption.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConsumption.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testConsumption.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testConsumption.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testConsumption.getNbUnits()).isEqualTo(DEFAULT_NB_UNITS);
    }

    @Test
    void createConsumptionWithExistingId() throws Exception {
        // Create the Consumption with an existing ID
        consumption.setId("existing_id");

        int databaseSizeBeforeCreate = consumptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllConsumptions() throws Exception {
        // Initialize the database
        consumptionRepository.save(consumption);

        // Get all the consumptionList
        restConsumptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumption.getId())))
            .andExpect(jsonPath("$.[*].organisationId").value(hasItem(DEFAULT_ORGANISATION_ID)))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].ratePlanId").value(hasItem(DEFAULT_RATE_PLAN_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].nbUnits").value(hasItem(DEFAULT_NB_UNITS)));
    }

    @Test
    void getConsumption() throws Exception {
        // Initialize the database
        consumptionRepository.save(consumption);

        // Get the consumption
        restConsumptionMockMvc
            .perform(get(ENTITY_API_URL_ID, consumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consumption.getId()))
            .andExpect(jsonPath("$.organisationId").value(DEFAULT_ORGANISATION_ID))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.ratePlanId").value(DEFAULT_RATE_PLAN_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.nbUnits").value(DEFAULT_NB_UNITS));
    }

    @Test
    void getNonExistingConsumption() throws Exception {
        // Get the consumption
        restConsumptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewConsumption() throws Exception {
        // Initialize the database
        consumptionRepository.save(consumption);

        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();

        // Update the consumption
        Consumption updatedConsumption = consumptionRepository.findById(consumption.getId()).get();
        updatedConsumption
            .organisationId(UPDATED_ORGANISATION_ID)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .details(UPDATED_DETAILS)
            .nbUnits(UPDATED_NB_UNITS);

        restConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConsumption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConsumption))
            )
            .andExpect(status().isOk());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
        Consumption testConsumption = consumptionList.get(consumptionList.size() - 1);
        assertThat(testConsumption.getOrganisationId()).isEqualTo(UPDATED_ORGANISATION_ID);
        assertThat(testConsumption.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testConsumption.getRatePlanId()).isEqualTo(UPDATED_RATE_PLAN_ID);
        assertThat(testConsumption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConsumption.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConsumption.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testConsumption.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testConsumption.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testConsumption.getNbUnits()).isEqualTo(UPDATED_NB_UNITS);
    }

    @Test
    void putNonExistingConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();
        consumption.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();
        consumption.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();
        consumption.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConsumptionWithPatch() throws Exception {
        // Initialize the database
        consumptionRepository.save(consumption);

        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();

        // Update the consumption using partial update
        Consumption partialUpdatedConsumption = new Consumption();
        partialUpdatedConsumption.setId(consumption.getId());

        partialUpdatedConsumption
            .organisationId(UPDATED_ORGANISATION_ID)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .type(UPDATED_TYPE)
            .month(UPDATED_MONTH);

        restConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsumption))
            )
            .andExpect(status().isOk());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
        Consumption testConsumption = consumptionList.get(consumptionList.size() - 1);
        assertThat(testConsumption.getOrganisationId()).isEqualTo(UPDATED_ORGANISATION_ID);
        assertThat(testConsumption.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testConsumption.getRatePlanId()).isEqualTo(UPDATED_RATE_PLAN_ID);
        assertThat(testConsumption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConsumption.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConsumption.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testConsumption.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testConsumption.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testConsumption.getNbUnits()).isEqualTo(DEFAULT_NB_UNITS);
    }

    @Test
    void fullUpdateConsumptionWithPatch() throws Exception {
        // Initialize the database
        consumptionRepository.save(consumption);

        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();

        // Update the consumption using partial update
        Consumption partialUpdatedConsumption = new Consumption();
        partialUpdatedConsumption.setId(consumption.getId());

        partialUpdatedConsumption
            .organisationId(UPDATED_ORGANISATION_ID)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .ratePlanId(UPDATED_RATE_PLAN_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .details(UPDATED_DETAILS)
            .nbUnits(UPDATED_NB_UNITS);

        restConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsumption))
            )
            .andExpect(status().isOk());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
        Consumption testConsumption = consumptionList.get(consumptionList.size() - 1);
        assertThat(testConsumption.getOrganisationId()).isEqualTo(UPDATED_ORGANISATION_ID);
        assertThat(testConsumption.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testConsumption.getRatePlanId()).isEqualTo(UPDATED_RATE_PLAN_ID);
        assertThat(testConsumption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConsumption.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConsumption.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testConsumption.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testConsumption.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testConsumption.getNbUnits()).isEqualTo(UPDATED_NB_UNITS);
    }

    @Test
    void patchNonExistingConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();
        consumption.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consumption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();
        consumption.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();
        consumption.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(consumption))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConsumption() throws Exception {
        // Initialize the database
        consumptionRepository.save(consumption);

        int databaseSizeBeforeDelete = consumptionRepository.findAll().size();

        // Delete the consumption
        restConsumptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, consumption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
