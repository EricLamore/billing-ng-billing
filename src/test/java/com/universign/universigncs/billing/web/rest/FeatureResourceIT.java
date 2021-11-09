package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.Feature;
import com.universign.universigncs.billing.repository.FeatureRepository;
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
 * Integration tests for the {@link FeatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeatureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VISIBLE = false;
    private static final Boolean UPDATED_IS_VISIBLE = true;

    private static final String ENTITY_API_URL = "/api/features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private MockMvc restFeatureMockMvc;

    private Feature feature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createEntity() {
        Feature feature = new Feature()
            .name(DEFAULT_NAME)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .description(DEFAULT_DESCRIPTION)
            .isVisible(DEFAULT_IS_VISIBLE);
        return feature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createUpdatedEntity() {
        Feature feature = new Feature()
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .isVisible(UPDATED_IS_VISIBLE);
        return feature;
    }

    @BeforeEach
    public void initTest() {
        featureRepository.deleteAll();
        feature = createEntity();
    }

    @Test
    void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();
        // Create the Feature
        restFeatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeature.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeature.getIsVisible()).isEqualTo(DEFAULT_IS_VISIBLE);
    }

    @Test
    void createFeatureWithExistingId() throws Exception {
        // Create the Feature with an existing ID
        feature.setId("existing_id");

        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        // Get all the featureList
        restFeatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isVisible").value(hasItem(DEFAULT_IS_VISIBLE.booleanValue())));
    }

    @Test
    void getFeature() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        // Get the feature
        restFeatureMockMvc
            .perform(get(ENTITY_API_URL_ID, feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isVisible").value(DEFAULT_IS_VISIBLE.booleanValue()));
    }

    @Test
    void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewFeature() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        Feature updatedFeature = featureRepository.findById(feature.getId()).get();
        updatedFeature.name(UPDATED_NAME).lastUpdate(UPDATED_LAST_UPDATE).description(UPDATED_DESCRIPTION).isVisible(UPDATED_IS_VISIBLE);

        restFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeature.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeature.getIsVisible()).isEqualTo(UPDATED_IS_VISIBLE);
    }

    @Test
    void putNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();
        feature.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();
        feature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();
        feature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFeatureWithPatch() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature using partial update
        Feature partialUpdatedFeature = new Feature();
        partialUpdatedFeature.setId(feature.getId());

        partialUpdatedFeature.name(UPDATED_NAME).lastUpdate(UPDATED_LAST_UPDATE).description(UPDATED_DESCRIPTION);

        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeature.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeature.getIsVisible()).isEqualTo(DEFAULT_IS_VISIBLE);
    }

    @Test
    void fullUpdateFeatureWithPatch() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature using partial update
        Feature partialUpdatedFeature = new Feature();
        partialUpdatedFeature.setId(feature.getId());

        partialUpdatedFeature
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .isVisible(UPDATED_IS_VISIBLE);

        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeature.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeature.getIsVisible()).isEqualTo(UPDATED_IS_VISIBLE);
    }

    @Test
    void patchNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();
        feature.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();
        feature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();
        feature.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFeature() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Delete the feature
        restFeatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, feature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
