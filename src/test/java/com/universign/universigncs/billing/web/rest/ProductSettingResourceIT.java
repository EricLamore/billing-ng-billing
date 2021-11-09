package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.ProductSetting;
import com.universign.universigncs.billing.repository.ProductSettingRepository;
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
 * Integration tests for the {@link ProductSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductSettingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductSettingRepository productSettingRepository;

    @Autowired
    private MockMvc restProductSettingMockMvc;

    private ProductSetting productSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSetting createEntity() {
        ProductSetting productSetting = new ProductSetting().name(DEFAULT_NAME).value(DEFAULT_VALUE);
        return productSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSetting createUpdatedEntity() {
        ProductSetting productSetting = new ProductSetting().name(UPDATED_NAME).value(UPDATED_VALUE);
        return productSetting;
    }

    @BeforeEach
    public void initTest() {
        productSettingRepository.deleteAll();
        productSetting = createEntity();
    }

    @Test
    void createProductSetting() throws Exception {
        int databaseSizeBeforeCreate = productSettingRepository.findAll().size();
        // Create the ProductSetting
        restProductSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSetting testProductSetting = productSettingList.get(productSettingList.size() - 1);
        assertThat(testProductSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSetting.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    void createProductSettingWithExistingId() throws Exception {
        // Create the ProductSetting with an existing ID
        productSetting.setId("existing_id");

        int databaseSizeBeforeCreate = productSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductSettings() throws Exception {
        // Initialize the database
        productSettingRepository.save(productSetting);

        // Get all the productSettingList
        restProductSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSetting.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    void getProductSetting() throws Exception {
        // Initialize the database
        productSettingRepository.save(productSetting);

        // Get the productSetting
        restProductSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, productSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSetting.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    void getNonExistingProductSetting() throws Exception {
        // Get the productSetting
        restProductSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewProductSetting() throws Exception {
        // Initialize the database
        productSettingRepository.save(productSetting);

        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();

        // Update the productSetting
        ProductSetting updatedProductSetting = productSettingRepository.findById(productSetting.getId()).get();
        updatedProductSetting.name(UPDATED_NAME).value(UPDATED_VALUE);

        restProductSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductSetting))
            )
            .andExpect(status().isOk());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
        ProductSetting testProductSetting = productSettingList.get(productSettingList.size() - 1);
        assertThat(testProductSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    void putNonExistingProductSetting() throws Exception {
        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();
        productSetting.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductSetting() throws Exception {
        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();
        productSetting.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductSetting() throws Exception {
        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();
        productSetting.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSetting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductSettingWithPatch() throws Exception {
        // Initialize the database
        productSettingRepository.save(productSetting);

        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();

        // Update the productSetting using partial update
        ProductSetting partialUpdatedProductSetting = new ProductSetting();
        partialUpdatedProductSetting.setId(productSetting.getId());

        partialUpdatedProductSetting.value(UPDATED_VALUE);

        restProductSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSetting))
            )
            .andExpect(status().isOk());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
        ProductSetting testProductSetting = productSettingList.get(productSettingList.size() - 1);
        assertThat(testProductSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    void fullUpdateProductSettingWithPatch() throws Exception {
        // Initialize the database
        productSettingRepository.save(productSetting);

        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();

        // Update the productSetting using partial update
        ProductSetting partialUpdatedProductSetting = new ProductSetting();
        partialUpdatedProductSetting.setId(productSetting.getId());

        partialUpdatedProductSetting.name(UPDATED_NAME).value(UPDATED_VALUE);

        restProductSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSetting))
            )
            .andExpect(status().isOk());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
        ProductSetting testProductSetting = productSettingList.get(productSettingList.size() - 1);
        assertThat(testProductSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    void patchNonExistingProductSetting() throws Exception {
        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();
        productSetting.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductSetting() throws Exception {
        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();
        productSetting.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductSetting() throws Exception {
        int databaseSizeBeforeUpdate = productSettingRepository.findAll().size();
        productSetting.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSettingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productSetting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSetting in the database
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductSetting() throws Exception {
        // Initialize the database
        productSettingRepository.save(productSetting);

        int databaseSizeBeforeDelete = productSettingRepository.findAll().size();

        // Delete the productSetting
        restProductSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSetting> productSettingList = productSettingRepository.findAll();
        assertThat(productSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
