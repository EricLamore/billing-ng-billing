package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.SubscriptionFeature;
import com.universign.universigncs.billing.repository.SubscriptionFeatureRepository;
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
 * Integration tests for the {@link SubscriptionFeatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubscriptionFeatureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VISIBLE = false;
    private static final Boolean UPDATED_IS_VISIBLE = true;

    private static final String ENTITY_API_URL = "/api/subscription-features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SubscriptionFeatureRepository subscriptionFeatureRepository;

    @Autowired
    private MockMvc restSubscriptionFeatureMockMvc;

    private SubscriptionFeature subscriptionFeature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionFeature createEntity() {
        SubscriptionFeature subscriptionFeature = new SubscriptionFeature()
            .name(DEFAULT_NAME)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .description(DEFAULT_DESCRIPTION)
            .isVisible(DEFAULT_IS_VISIBLE);
        return subscriptionFeature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionFeature createUpdatedEntity() {
        SubscriptionFeature subscriptionFeature = new SubscriptionFeature()
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .isVisible(UPDATED_IS_VISIBLE);
        return subscriptionFeature;
    }

    @BeforeEach
    public void initTest() {
        subscriptionFeatureRepository.deleteAll();
        subscriptionFeature = createEntity();
    }

    @Test
    void createSubscriptionFeature() throws Exception {
        int databaseSizeBeforeCreate = subscriptionFeatureRepository.findAll().size();
        // Create the SubscriptionFeature
        restSubscriptionFeatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isCreated());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionFeature testSubscriptionFeature = subscriptionFeatureList.get(subscriptionFeatureList.size() - 1);
        assertThat(testSubscriptionFeature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubscriptionFeature.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSubscriptionFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubscriptionFeature.getIsVisible()).isEqualTo(DEFAULT_IS_VISIBLE);
    }

    @Test
    void createSubscriptionFeatureWithExistingId() throws Exception {
        // Create the SubscriptionFeature with an existing ID
        subscriptionFeature.setId("existing_id");

        int databaseSizeBeforeCreate = subscriptionFeatureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionFeatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSubscriptionFeatures() throws Exception {
        // Initialize the database
        subscriptionFeatureRepository.save(subscriptionFeature);

        // Get all the subscriptionFeatureList
        restSubscriptionFeatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionFeature.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isVisible").value(hasItem(DEFAULT_IS_VISIBLE.booleanValue())));
    }

    @Test
    void getSubscriptionFeature() throws Exception {
        // Initialize the database
        subscriptionFeatureRepository.save(subscriptionFeature);

        // Get the subscriptionFeature
        restSubscriptionFeatureMockMvc
            .perform(get(ENTITY_API_URL_ID, subscriptionFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionFeature.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isVisible").value(DEFAULT_IS_VISIBLE.booleanValue()));
    }

    @Test
    void getNonExistingSubscriptionFeature() throws Exception {
        // Get the subscriptionFeature
        restSubscriptionFeatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewSubscriptionFeature() throws Exception {
        // Initialize the database
        subscriptionFeatureRepository.save(subscriptionFeature);

        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();

        // Update the subscriptionFeature
        SubscriptionFeature updatedSubscriptionFeature = subscriptionFeatureRepository.findById(subscriptionFeature.getId()).get();
        updatedSubscriptionFeature
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .isVisible(UPDATED_IS_VISIBLE);

        restSubscriptionFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubscriptionFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubscriptionFeature))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionFeature testSubscriptionFeature = subscriptionFeatureList.get(subscriptionFeatureList.size() - 1);
        assertThat(testSubscriptionFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubscriptionFeature.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSubscriptionFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubscriptionFeature.getIsVisible()).isEqualTo(UPDATED_IS_VISIBLE);
    }

    @Test
    void putNonExistingSubscriptionFeature() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();
        subscriptionFeature.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subscriptionFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSubscriptionFeature() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();
        subscriptionFeature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSubscriptionFeature() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();
        subscriptionFeature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionFeatureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSubscriptionFeatureWithPatch() throws Exception {
        // Initialize the database
        subscriptionFeatureRepository.save(subscriptionFeature);

        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();

        // Update the subscriptionFeature using partial update
        SubscriptionFeature partialUpdatedSubscriptionFeature = new SubscriptionFeature();
        partialUpdatedSubscriptionFeature.setId(subscriptionFeature.getId());

        partialUpdatedSubscriptionFeature.lastUpdate(UPDATED_LAST_UPDATE);

        restSubscriptionFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscriptionFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubscriptionFeature))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionFeature testSubscriptionFeature = subscriptionFeatureList.get(subscriptionFeatureList.size() - 1);
        assertThat(testSubscriptionFeature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubscriptionFeature.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSubscriptionFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubscriptionFeature.getIsVisible()).isEqualTo(DEFAULT_IS_VISIBLE);
    }

    @Test
    void fullUpdateSubscriptionFeatureWithPatch() throws Exception {
        // Initialize the database
        subscriptionFeatureRepository.save(subscriptionFeature);

        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();

        // Update the subscriptionFeature using partial update
        SubscriptionFeature partialUpdatedSubscriptionFeature = new SubscriptionFeature();
        partialUpdatedSubscriptionFeature.setId(subscriptionFeature.getId());

        partialUpdatedSubscriptionFeature
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .isVisible(UPDATED_IS_VISIBLE);

        restSubscriptionFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscriptionFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubscriptionFeature))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionFeature testSubscriptionFeature = subscriptionFeatureList.get(subscriptionFeatureList.size() - 1);
        assertThat(testSubscriptionFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubscriptionFeature.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSubscriptionFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubscriptionFeature.getIsVisible()).isEqualTo(UPDATED_IS_VISIBLE);
    }

    @Test
    void patchNonExistingSubscriptionFeature() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();
        subscriptionFeature.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subscriptionFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSubscriptionFeature() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();
        subscriptionFeature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSubscriptionFeature() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionFeatureRepository.findAll().size();
        subscriptionFeature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionFeature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubscriptionFeature in the database
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSubscriptionFeature() throws Exception {
        // Initialize the database
        subscriptionFeatureRepository.save(subscriptionFeature);

        int databaseSizeBeforeDelete = subscriptionFeatureRepository.findAll().size();

        // Delete the subscriptionFeature
        restSubscriptionFeatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, subscriptionFeature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubscriptionFeature> subscriptionFeatureList = subscriptionFeatureRepository.findAll();
        assertThat(subscriptionFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
