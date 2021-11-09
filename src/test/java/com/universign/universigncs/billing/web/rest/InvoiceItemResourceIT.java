package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.InvoiceItem;
import com.universign.universigncs.billing.domain.enumeration.ItemType;
import com.universign.universigncs.billing.repository.InvoiceItemRepository;
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
 * Integration tests for the {@link InvoiceItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InvoiceItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ItemType DEFAULT_ITEM_TYPE = ItemType.SUBSCRIPTION;
    private static final ItemType UPDATED_ITEM_TYPE = ItemType.STEP;

    private static final Integer DEFAULT_MIN_STEP = 1;
    private static final Integer UPDATED_MIN_STEP = 2;

    private static final Integer DEFAULT_MAX_STEP = 1;
    private static final Integer UPDATED_MAX_STEP = 2;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final Integer DEFAULT_GLOBAL_QUANTITY = 1;
    private static final Integer UPDATED_GLOBAL_QUANTITY = 2;

    private static final LocalDate DEFAULT_UNTIL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UNTIL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/invoice-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private MockMvc restInvoiceItemMockMvc;

    private InvoiceItem invoiceItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItem createEntity() {
        InvoiceItem invoiceItem = new InvoiceItem()
            .name(DEFAULT_NAME)
            .itemType(DEFAULT_ITEM_TYPE)
            .minStep(DEFAULT_MIN_STEP)
            .maxStep(DEFAULT_MAX_STEP)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .price(DEFAULT_PRICE)
            .product(DEFAULT_PRODUCT)
            .globalQuantity(DEFAULT_GLOBAL_QUANTITY)
            .untilDate(DEFAULT_UNTIL_DATE);
        return invoiceItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItem createUpdatedEntity() {
        InvoiceItem invoiceItem = new InvoiceItem()
            .name(UPDATED_NAME)
            .itemType(UPDATED_ITEM_TYPE)
            .minStep(UPDATED_MIN_STEP)
            .maxStep(UPDATED_MAX_STEP)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .price(UPDATED_PRICE)
            .product(UPDATED_PRODUCT)
            .globalQuantity(UPDATED_GLOBAL_QUANTITY)
            .untilDate(UPDATED_UNTIL_DATE);
        return invoiceItem;
    }

    @BeforeEach
    public void initTest() {
        invoiceItemRepository.deleteAll();
        invoiceItem = createEntity();
    }

    @Test
    void createInvoiceItem() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemRepository.findAll().size();
        // Create the InvoiceItem
        restInvoiceItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isCreated());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvoiceItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testInvoiceItem.getMinStep()).isEqualTo(DEFAULT_MIN_STEP);
        assertThat(testInvoiceItem.getMaxStep()).isEqualTo(DEFAULT_MAX_STEP);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testInvoiceItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testInvoiceItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testInvoiceItem.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testInvoiceItem.getGlobalQuantity()).isEqualTo(DEFAULT_GLOBAL_QUANTITY);
        assertThat(testInvoiceItem.getUntilDate()).isEqualTo(DEFAULT_UNTIL_DATE);
    }

    @Test
    void createInvoiceItemWithExistingId() throws Exception {
        // Create the InvoiceItem with an existing ID
        invoiceItem.setId("existing_id");

        int databaseSizeBeforeCreate = invoiceItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemRepository.save(invoiceItem);

        // Get all the invoiceItemList
        restInvoiceItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItem.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].minStep").value(hasItem(DEFAULT_MIN_STEP)))
            .andExpect(jsonPath("$.[*].maxStep").value(hasItem(DEFAULT_MAX_STEP)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT)))
            .andExpect(jsonPath("$.[*].globalQuantity").value(hasItem(DEFAULT_GLOBAL_QUANTITY)))
            .andExpect(jsonPath("$.[*].untilDate").value(hasItem(DEFAULT_UNTIL_DATE.toString())));
    }

    @Test
    void getInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.save(invoiceItem);

        // Get the invoiceItem
        restInvoiceItemMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceItem.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.minStep").value(DEFAULT_MIN_STEP))
            .andExpect(jsonPath("$.maxStep").value(DEFAULT_MAX_STEP))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT))
            .andExpect(jsonPath("$.globalQuantity").value(DEFAULT_GLOBAL_QUANTITY))
            .andExpect(jsonPath("$.untilDate").value(DEFAULT_UNTIL_DATE.toString()));
    }

    @Test
    void getNonExistingInvoiceItem() throws Exception {
        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.save(invoiceItem);

        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // Update the invoiceItem
        InvoiceItem updatedInvoiceItem = invoiceItemRepository.findById(invoiceItem.getId()).get();
        updatedInvoiceItem
            .name(UPDATED_NAME)
            .itemType(UPDATED_ITEM_TYPE)
            .minStep(UPDATED_MIN_STEP)
            .maxStep(UPDATED_MAX_STEP)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .price(UPDATED_PRICE)
            .product(UPDATED_PRODUCT)
            .globalQuantity(UPDATED_GLOBAL_QUANTITY)
            .untilDate(UPDATED_UNTIL_DATE);

        restInvoiceItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInvoiceItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceItem))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvoiceItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceItem.getMinStep()).isEqualTo(UPDATED_MIN_STEP);
        assertThat(testInvoiceItem.getMaxStep()).isEqualTo(UPDATED_MAX_STEP);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testInvoiceItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testInvoiceItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testInvoiceItem.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testInvoiceItem.getGlobalQuantity()).isEqualTo(UPDATED_GLOBAL_QUANTITY);
        assertThat(testInvoiceItem.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
    }

    @Test
    void putNonExistingInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();
        invoiceItem.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();
        invoiceItem.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();
        invoiceItem.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInvoiceItemWithPatch() throws Exception {
        // Initialize the database
        invoiceItemRepository.save(invoiceItem);

        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // Update the invoiceItem using partial update
        InvoiceItem partialUpdatedInvoiceItem = new InvoiceItem();
        partialUpdatedInvoiceItem.setId(invoiceItem.getId());

        partialUpdatedInvoiceItem.globalQuantity(UPDATED_GLOBAL_QUANTITY).untilDate(UPDATED_UNTIL_DATE);

        restInvoiceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceItem))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvoiceItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testInvoiceItem.getMinStep()).isEqualTo(DEFAULT_MIN_STEP);
        assertThat(testInvoiceItem.getMaxStep()).isEqualTo(DEFAULT_MAX_STEP);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testInvoiceItem.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testInvoiceItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testInvoiceItem.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testInvoiceItem.getGlobalQuantity()).isEqualTo(UPDATED_GLOBAL_QUANTITY);
        assertThat(testInvoiceItem.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
    }

    @Test
    void fullUpdateInvoiceItemWithPatch() throws Exception {
        // Initialize the database
        invoiceItemRepository.save(invoiceItem);

        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // Update the invoiceItem using partial update
        InvoiceItem partialUpdatedInvoiceItem = new InvoiceItem();
        partialUpdatedInvoiceItem.setId(invoiceItem.getId());

        partialUpdatedInvoiceItem
            .name(UPDATED_NAME)
            .itemType(UPDATED_ITEM_TYPE)
            .minStep(UPDATED_MIN_STEP)
            .maxStep(UPDATED_MAX_STEP)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .price(UPDATED_PRICE)
            .product(UPDATED_PRODUCT)
            .globalQuantity(UPDATED_GLOBAL_QUANTITY)
            .untilDate(UPDATED_UNTIL_DATE);

        restInvoiceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceItem))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvoiceItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceItem.getMinStep()).isEqualTo(UPDATED_MIN_STEP);
        assertThat(testInvoiceItem.getMaxStep()).isEqualTo(UPDATED_MAX_STEP);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testInvoiceItem.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testInvoiceItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testInvoiceItem.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testInvoiceItem.getGlobalQuantity()).isEqualTo(UPDATED_GLOBAL_QUANTITY);
        assertThat(testInvoiceItem.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
    }

    @Test
    void patchNonExistingInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();
        invoiceItem.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();
        invoiceItem.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();
        invoiceItem.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(invoiceItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.save(invoiceItem);

        int databaseSizeBeforeDelete = invoiceItemRepository.findAll().size();

        // Delete the invoiceItem
        restInvoiceItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
