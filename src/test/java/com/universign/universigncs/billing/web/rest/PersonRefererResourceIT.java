package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.PersonReferer;
import com.universign.universigncs.billing.repository.PersonRefererRepository;
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
 * Integration tests for the {@link PersonRefererResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonRefererResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/person-referers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PersonRefererRepository personRefererRepository;

    @Autowired
    private MockMvc restPersonRefererMockMvc;

    private PersonReferer personReferer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonReferer createEntity() {
        PersonReferer personReferer = new PersonReferer()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .job(DEFAULT_JOB)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .mobile(DEFAULT_MOBILE)
            .fax(DEFAULT_FAX)
            .description(DEFAULT_DESCRIPTION);
        return personReferer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonReferer createUpdatedEntity() {
        PersonReferer personReferer = new PersonReferer()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .job(UPDATED_JOB)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .description(UPDATED_DESCRIPTION);
        return personReferer;
    }

    @BeforeEach
    public void initTest() {
        personRefererRepository.deleteAll();
        personReferer = createEntity();
    }

    @Test
    void createPersonReferer() throws Exception {
        int databaseSizeBeforeCreate = personRefererRepository.findAll().size();
        // Create the PersonReferer
        restPersonRefererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personReferer)))
            .andExpect(status().isCreated());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeCreate + 1);
        PersonReferer testPersonReferer = personRefererList.get(personRefererList.size() - 1);
        assertThat(testPersonReferer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPersonReferer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPersonReferer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonReferer.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testPersonReferer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonReferer.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testPersonReferer.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testPersonReferer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createPersonRefererWithExistingId() throws Exception {
        // Create the PersonReferer with an existing ID
        personReferer.setId("existing_id");

        int databaseSizeBeforeCreate = personRefererRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonRefererMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personReferer)))
            .andExpect(status().isBadRequest());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPersonReferers() throws Exception {
        // Initialize the database
        personRefererRepository.save(personReferer);

        // Get all the personRefererList
        restPersonRefererMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personReferer.getId())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getPersonReferer() throws Exception {
        // Initialize the database
        personRefererRepository.save(personReferer);

        // Get the personReferer
        restPersonRefererMockMvc
            .perform(get(ENTITY_API_URL_ID, personReferer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personReferer.getId()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingPersonReferer() throws Exception {
        // Get the personReferer
        restPersonRefererMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPersonReferer() throws Exception {
        // Initialize the database
        personRefererRepository.save(personReferer);

        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();

        // Update the personReferer
        PersonReferer updatedPersonReferer = personRefererRepository.findById(personReferer.getId()).get();
        updatedPersonReferer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .job(UPDATED_JOB)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .description(UPDATED_DESCRIPTION);

        restPersonRefererMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonReferer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonReferer))
            )
            .andExpect(status().isOk());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
        PersonReferer testPersonReferer = personRefererList.get(personRefererList.size() - 1);
        assertThat(testPersonReferer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPersonReferer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPersonReferer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonReferer.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testPersonReferer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonReferer.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonReferer.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testPersonReferer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingPersonReferer() throws Exception {
        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();
        personReferer.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonRefererMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personReferer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personReferer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPersonReferer() throws Exception {
        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();
        personReferer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonRefererMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personReferer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPersonReferer() throws Exception {
        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();
        personReferer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonRefererMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personReferer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePersonRefererWithPatch() throws Exception {
        // Initialize the database
        personRefererRepository.save(personReferer);

        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();

        // Update the personReferer using partial update
        PersonReferer partialUpdatedPersonReferer = new PersonReferer();
        partialUpdatedPersonReferer.setId(personReferer.getId());

        partialUpdatedPersonReferer.phoneNumber(UPDATED_PHONE_NUMBER).mobile(UPDATED_MOBILE);

        restPersonRefererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonReferer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonReferer))
            )
            .andExpect(status().isOk());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
        PersonReferer testPersonReferer = personRefererList.get(personRefererList.size() - 1);
        assertThat(testPersonReferer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPersonReferer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPersonReferer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonReferer.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testPersonReferer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonReferer.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonReferer.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testPersonReferer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdatePersonRefererWithPatch() throws Exception {
        // Initialize the database
        personRefererRepository.save(personReferer);

        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();

        // Update the personReferer using partial update
        PersonReferer partialUpdatedPersonReferer = new PersonReferer();
        partialUpdatedPersonReferer.setId(personReferer.getId());

        partialUpdatedPersonReferer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .job(UPDATED_JOB)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .description(UPDATED_DESCRIPTION);

        restPersonRefererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonReferer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonReferer))
            )
            .andExpect(status().isOk());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
        PersonReferer testPersonReferer = personRefererList.get(personRefererList.size() - 1);
        assertThat(testPersonReferer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPersonReferer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPersonReferer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonReferer.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testPersonReferer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonReferer.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonReferer.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testPersonReferer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingPersonReferer() throws Exception {
        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();
        personReferer.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonRefererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personReferer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personReferer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPersonReferer() throws Exception {
        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();
        personReferer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonRefererMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personReferer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPersonReferer() throws Exception {
        int databaseSizeBeforeUpdate = personRefererRepository.findAll().size();
        personReferer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonRefererMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personReferer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonReferer in the database
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePersonReferer() throws Exception {
        // Initialize the database
        personRefererRepository.save(personReferer);

        int databaseSizeBeforeDelete = personRefererRepository.findAll().size();

        // Delete the personReferer
        restPersonRefererMockMvc
            .perform(delete(ENTITY_API_URL_ID, personReferer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonReferer> personRefererList = personRefererRepository.findAll();
        assertThat(personRefererList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
