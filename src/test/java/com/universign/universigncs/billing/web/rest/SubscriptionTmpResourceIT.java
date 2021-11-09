package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.SubscriptionTmp;
import com.universign.universigncs.billing.repository.SubscriptionTmpRepository;
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
 * Integration tests for the {@link SubscriptionTmpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubscriptionTmpResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMERCIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMERCIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RATE_PLANS = "AAAAAAAAAA";
    private static final String UPDATED_RATE_PLANS = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTION_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTION_FEATURES = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_USAGES = "AAAAAAAAAA";
    private static final String UPDATED_USAGES = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREE_MONTHS = 1;
    private static final Integer UPDATED_FREE_MONTHS = 2;

    private static final String DEFAULT_INVOICE_ITEM_DATEDS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_ITEM_DATEDS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subscription-tmps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SubscriptionTmpRepository subscriptionTmpRepository;

    @Autowired
    private MockMvc restSubscriptionTmpMockMvc;

    private SubscriptionTmp subscriptionTmp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionTmp createEntity() {
        SubscriptionTmp subscriptionTmp = new SubscriptionTmp()
            .name(DEFAULT_NAME)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .description(DEFAULT_DESCRIPTION)
            .commercialName(DEFAULT_COMMERCIAL_NAME)
            .ratePlans(DEFAULT_RATE_PLANS)
            .subscriptionFeatures(DEFAULT_SUBSCRIPTION_FEATURES)
            .version(DEFAULT_VERSION)
            .usages(DEFAULT_USAGES)
            .freeMonths(DEFAULT_FREE_MONTHS)
            .invoiceItemDateds(DEFAULT_INVOICE_ITEM_DATEDS);
        return subscriptionTmp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionTmp createUpdatedEntity() {
        SubscriptionTmp subscriptionTmp = new SubscriptionTmp()
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .ratePlans(UPDATED_RATE_PLANS)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES)
            .version(UPDATED_VERSION)
            .usages(UPDATED_USAGES)
            .freeMonths(UPDATED_FREE_MONTHS)
            .invoiceItemDateds(UPDATED_INVOICE_ITEM_DATEDS);
        return subscriptionTmp;
    }

    @BeforeEach
    public void initTest() {
        subscriptionTmpRepository.deleteAll();
        subscriptionTmp = createEntity();
    }

    @Test
    void createSubscriptionTmp() throws Exception {
        int databaseSizeBeforeCreate = subscriptionTmpRepository.findAll().size();
        // Create the SubscriptionTmp
        restSubscriptionTmpMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isCreated());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionTmp testSubscriptionTmp = subscriptionTmpList.get(subscriptionTmpList.size() - 1);
        assertThat(testSubscriptionTmp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubscriptionTmp.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSubscriptionTmp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubscriptionTmp.getCommercialName()).isEqualTo(DEFAULT_COMMERCIAL_NAME);
        assertThat(testSubscriptionTmp.getRatePlans()).isEqualTo(DEFAULT_RATE_PLANS);
        assertThat(testSubscriptionTmp.getSubscriptionFeatures()).isEqualTo(DEFAULT_SUBSCRIPTION_FEATURES);
        assertThat(testSubscriptionTmp.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSubscriptionTmp.getUsages()).isEqualTo(DEFAULT_USAGES);
        assertThat(testSubscriptionTmp.getFreeMonths()).isEqualTo(DEFAULT_FREE_MONTHS);
        assertThat(testSubscriptionTmp.getInvoiceItemDateds()).isEqualTo(DEFAULT_INVOICE_ITEM_DATEDS);
    }

    @Test
    void createSubscriptionTmpWithExistingId() throws Exception {
        // Create the SubscriptionTmp with an existing ID
        subscriptionTmp.setId("existing_id");

        int databaseSizeBeforeCreate = subscriptionTmpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionTmpMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSubscriptionTmps() throws Exception {
        // Initialize the database
        subscriptionTmpRepository.save(subscriptionTmp);

        // Get all the subscriptionTmpList
        restSubscriptionTmpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionTmp.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].commercialName").value(hasItem(DEFAULT_COMMERCIAL_NAME)))
            .andExpect(jsonPath("$.[*].ratePlans").value(hasItem(DEFAULT_RATE_PLANS)))
            .andExpect(jsonPath("$.[*].subscriptionFeatures").value(hasItem(DEFAULT_SUBSCRIPTION_FEATURES)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].usages").value(hasItem(DEFAULT_USAGES)))
            .andExpect(jsonPath("$.[*].freeMonths").value(hasItem(DEFAULT_FREE_MONTHS)))
            .andExpect(jsonPath("$.[*].invoiceItemDateds").value(hasItem(DEFAULT_INVOICE_ITEM_DATEDS)));
    }

    @Test
    void getSubscriptionTmp() throws Exception {
        // Initialize the database
        subscriptionTmpRepository.save(subscriptionTmp);

        // Get the subscriptionTmp
        restSubscriptionTmpMockMvc
            .perform(get(ENTITY_API_URL_ID, subscriptionTmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionTmp.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.commercialName").value(DEFAULT_COMMERCIAL_NAME))
            .andExpect(jsonPath("$.ratePlans").value(DEFAULT_RATE_PLANS))
            .andExpect(jsonPath("$.subscriptionFeatures").value(DEFAULT_SUBSCRIPTION_FEATURES))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.usages").value(DEFAULT_USAGES))
            .andExpect(jsonPath("$.freeMonths").value(DEFAULT_FREE_MONTHS))
            .andExpect(jsonPath("$.invoiceItemDateds").value(DEFAULT_INVOICE_ITEM_DATEDS));
    }

    @Test
    void getNonExistingSubscriptionTmp() throws Exception {
        // Get the subscriptionTmp
        restSubscriptionTmpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewSubscriptionTmp() throws Exception {
        // Initialize the database
        subscriptionTmpRepository.save(subscriptionTmp);

        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();

        // Update the subscriptionTmp
        SubscriptionTmp updatedSubscriptionTmp = subscriptionTmpRepository.findById(subscriptionTmp.getId()).get();
        updatedSubscriptionTmp
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .ratePlans(UPDATED_RATE_PLANS)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES)
            .version(UPDATED_VERSION)
            .usages(UPDATED_USAGES)
            .freeMonths(UPDATED_FREE_MONTHS)
            .invoiceItemDateds(UPDATED_INVOICE_ITEM_DATEDS);

        restSubscriptionTmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubscriptionTmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubscriptionTmp))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionTmp testSubscriptionTmp = subscriptionTmpList.get(subscriptionTmpList.size() - 1);
        assertThat(testSubscriptionTmp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubscriptionTmp.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSubscriptionTmp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubscriptionTmp.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testSubscriptionTmp.getRatePlans()).isEqualTo(UPDATED_RATE_PLANS);
        assertThat(testSubscriptionTmp.getSubscriptionFeatures()).isEqualTo(UPDATED_SUBSCRIPTION_FEATURES);
        assertThat(testSubscriptionTmp.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSubscriptionTmp.getUsages()).isEqualTo(UPDATED_USAGES);
        assertThat(testSubscriptionTmp.getFreeMonths()).isEqualTo(UPDATED_FREE_MONTHS);
        assertThat(testSubscriptionTmp.getInvoiceItemDateds()).isEqualTo(UPDATED_INVOICE_ITEM_DATEDS);
    }

    @Test
    void putNonExistingSubscriptionTmp() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();
        subscriptionTmp.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionTmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subscriptionTmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSubscriptionTmp() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();
        subscriptionTmp.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSubscriptionTmp() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();
        subscriptionTmp.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTmpMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSubscriptionTmpWithPatch() throws Exception {
        // Initialize the database
        subscriptionTmpRepository.save(subscriptionTmp);

        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();

        // Update the subscriptionTmp using partial update
        SubscriptionTmp partialUpdatedSubscriptionTmp = new SubscriptionTmp();
        partialUpdatedSubscriptionTmp.setId(subscriptionTmp.getId());

        partialUpdatedSubscriptionTmp
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .usages(UPDATED_USAGES)
            .invoiceItemDateds(UPDATED_INVOICE_ITEM_DATEDS);

        restSubscriptionTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscriptionTmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubscriptionTmp))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionTmp testSubscriptionTmp = subscriptionTmpList.get(subscriptionTmpList.size() - 1);
        assertThat(testSubscriptionTmp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubscriptionTmp.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSubscriptionTmp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubscriptionTmp.getCommercialName()).isEqualTo(DEFAULT_COMMERCIAL_NAME);
        assertThat(testSubscriptionTmp.getRatePlans()).isEqualTo(DEFAULT_RATE_PLANS);
        assertThat(testSubscriptionTmp.getSubscriptionFeatures()).isEqualTo(DEFAULT_SUBSCRIPTION_FEATURES);
        assertThat(testSubscriptionTmp.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSubscriptionTmp.getUsages()).isEqualTo(UPDATED_USAGES);
        assertThat(testSubscriptionTmp.getFreeMonths()).isEqualTo(DEFAULT_FREE_MONTHS);
        assertThat(testSubscriptionTmp.getInvoiceItemDateds()).isEqualTo(UPDATED_INVOICE_ITEM_DATEDS);
    }

    @Test
    void fullUpdateSubscriptionTmpWithPatch() throws Exception {
        // Initialize the database
        subscriptionTmpRepository.save(subscriptionTmp);

        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();

        // Update the subscriptionTmp using partial update
        SubscriptionTmp partialUpdatedSubscriptionTmp = new SubscriptionTmp();
        partialUpdatedSubscriptionTmp.setId(subscriptionTmp.getId());

        partialUpdatedSubscriptionTmp
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .commercialName(UPDATED_COMMERCIAL_NAME)
            .ratePlans(UPDATED_RATE_PLANS)
            .subscriptionFeatures(UPDATED_SUBSCRIPTION_FEATURES)
            .version(UPDATED_VERSION)
            .usages(UPDATED_USAGES)
            .freeMonths(UPDATED_FREE_MONTHS)
            .invoiceItemDateds(UPDATED_INVOICE_ITEM_DATEDS);

        restSubscriptionTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscriptionTmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubscriptionTmp))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionTmp testSubscriptionTmp = subscriptionTmpList.get(subscriptionTmpList.size() - 1);
        assertThat(testSubscriptionTmp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubscriptionTmp.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSubscriptionTmp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubscriptionTmp.getCommercialName()).isEqualTo(UPDATED_COMMERCIAL_NAME);
        assertThat(testSubscriptionTmp.getRatePlans()).isEqualTo(UPDATED_RATE_PLANS);
        assertThat(testSubscriptionTmp.getSubscriptionFeatures()).isEqualTo(UPDATED_SUBSCRIPTION_FEATURES);
        assertThat(testSubscriptionTmp.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSubscriptionTmp.getUsages()).isEqualTo(UPDATED_USAGES);
        assertThat(testSubscriptionTmp.getFreeMonths()).isEqualTo(UPDATED_FREE_MONTHS);
        assertThat(testSubscriptionTmp.getInvoiceItemDateds()).isEqualTo(UPDATED_INVOICE_ITEM_DATEDS);
    }

    @Test
    void patchNonExistingSubscriptionTmp() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();
        subscriptionTmp.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subscriptionTmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSubscriptionTmp() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();
        subscriptionTmp.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSubscriptionTmp() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTmpRepository.findAll().size();
        subscriptionTmp.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTmpMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subscriptionTmp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubscriptionTmp in the database
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSubscriptionTmp() throws Exception {
        // Initialize the database
        subscriptionTmpRepository.save(subscriptionTmp);

        int databaseSizeBeforeDelete = subscriptionTmpRepository.findAll().size();

        // Delete the subscriptionTmp
        restSubscriptionTmpMockMvc
            .perform(delete(ENTITY_API_URL_ID, subscriptionTmp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubscriptionTmp> subscriptionTmpList = subscriptionTmpRepository.findAll();
        assertThat(subscriptionTmpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
