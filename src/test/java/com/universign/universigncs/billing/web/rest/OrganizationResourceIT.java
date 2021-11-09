package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.Organization;
import com.universign.universigncs.billing.repository.OrganizationRepository;
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
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationResourceIT {

    private static final String DEFAULT_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_ADDR = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDIVIDUAL = false;
    private static final Boolean UPDATED_INDIVIDUAL = true;

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IP_RANGES = "AAAAAAAAAA";
    private static final String UPDATED_IP_RANGES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity() {
        Organization organization = new Organization()
            .addr(DEFAULT_ADDR)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .name(DEFAULT_NAME)
            .registerDate(DEFAULT_REGISTER_DATE)
            .status(DEFAULT_STATUS)
            .zipCode(DEFAULT_ZIP_CODE)
            .individual(DEFAULT_INDIVIDUAL)
            .vatNumber(DEFAULT_VAT_NUMBER)
            .ipRanges(DEFAULT_IP_RANGES);
        return organization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity() {
        Organization organization = new Organization()
            .addr(UPDATED_ADDR)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .name(UPDATED_NAME)
            .registerDate(UPDATED_REGISTER_DATE)
            .status(UPDATED_STATUS)
            .zipCode(UPDATED_ZIP_CODE)
            .individual(UPDATED_INDIVIDUAL)
            .vatNumber(UPDATED_VAT_NUMBER)
            .ipRanges(UPDATED_IP_RANGES);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organizationRepository.deleteAll();
        organization = createEntity();
    }

    @Test
    void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();
        // Create the Organization
        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getAddr()).isEqualTo(DEFAULT_ADDR);
        assertThat(testOrganization.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOrganization.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testOrganization.getIndividual()).isEqualTo(DEFAULT_INDIVIDUAL);
        assertThat(testOrganization.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
        assertThat(testOrganization.getIpRanges()).isEqualTo(DEFAULT_IP_RANGES);
    }

    @Test
    void createOrganizationWithExistingId() throws Exception {
        // Create the Organization with an existing ID
        organization.setId("existing_id");

        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllOrganizations() throws Exception {
        // Initialize the database
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);

        // Get all the organizationList
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId())))
            .andExpect(jsonPath("$.[*].addr").value(hasItem(DEFAULT_ADDR)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].individual").value(hasItem(DEFAULT_INDIVIDUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER)))
            .andExpect(jsonPath("$.[*].ipRanges").value(hasItem(DEFAULT_IP_RANGES)));
    }

    @Test
    void getOrganization() throws Exception {
        // Initialize the database
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);

        // Get the organization
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId()))
            .andExpect(jsonPath("$.addr").value(DEFAULT_ADDR))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.individual").value(DEFAULT_INDIVIDUAL.booleanValue()))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER))
            .andExpect(jsonPath("$.ipRanges").value(DEFAULT_IP_RANGES));
    }

    @Test
    void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewOrganization() throws Exception {
        // Initialize the database
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        updatedOrganization
            .addr(UPDATED_ADDR)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .name(UPDATED_NAME)
            .registerDate(UPDATED_REGISTER_DATE)
            .status(UPDATED_STATUS)
            .zipCode(UPDATED_ZIP_CODE)
            .individual(UPDATED_INDIVIDUAL)
            .vatNumber(UPDATED_VAT_NUMBER)
            .ipRanges(UPDATED_IP_RANGES);

        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testOrganization.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOrganization.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testOrganization.getIndividual()).isEqualTo(UPDATED_INDIVIDUAL);
        assertThat(testOrganization.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testOrganization.getIpRanges()).isEqualTo(UPDATED_IP_RANGES);
    }

    @Test
    void putNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .addr(UPDATED_ADDR)
            .name(UPDATED_NAME)
            .registerDate(UPDATED_REGISTER_DATE)
            .individual(UPDATED_INDIVIDUAL)
            .vatNumber(UPDATED_VAT_NUMBER);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testOrganization.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOrganization.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testOrganization.getIndividual()).isEqualTo(UPDATED_INDIVIDUAL);
        assertThat(testOrganization.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testOrganization.getIpRanges()).isEqualTo(DEFAULT_IP_RANGES);
    }

    @Test
    void fullUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .addr(UPDATED_ADDR)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .name(UPDATED_NAME)
            .registerDate(UPDATED_REGISTER_DATE)
            .status(UPDATED_STATUS)
            .zipCode(UPDATED_ZIP_CODE)
            .individual(UPDATED_INDIVIDUAL)
            .vatNumber(UPDATED_VAT_NUMBER)
            .ipRanges(UPDATED_IP_RANGES);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testOrganization.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOrganization.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testOrganization.getIndividual()).isEqualTo(UPDATED_INDIVIDUAL);
        assertThat(testOrganization.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testOrganization.getIpRanges()).isEqualTo(UPDATED_IP_RANGES);
    }

    @Test
    void patchNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organization))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOrganization() throws Exception {
        // Initialize the database
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
