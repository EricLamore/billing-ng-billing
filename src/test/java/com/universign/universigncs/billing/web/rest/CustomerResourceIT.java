package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.Customer;
import com.universign.universigncs.billing.repository.CustomerRepository;
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
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_NO = "AAAAAAAAAA";
    private static final String UPDATED_TAX_NO = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_PARTY_ACCOUNTING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PARTY_ACCOUNTING_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SIRET = "AAAAAAAAAA";
    private static final String UPDATED_SIRET = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PARTICULAR = false;
    private static final Boolean UPDATED_IS_PARTICULAR = true;

    private static final String DEFAULT_PERSON_REFERERS = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_REFERERS = "BBBBBBBBBB";

    private static final String DEFAULT_MEANS_PAYMENTS = "AAAAAAAAAA";
    private static final String UPDATED_MEANS_PAYMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_USAGES = "AAAAAAAAAA";
    private static final String UPDATED_USAGES = "BBBBBBBBBB";

    private static final String DEFAULT_SETTINGS_INVOICE = "AAAAAAAAAA";
    private static final String UPDATED_SETTINGS_INVOICE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARTNER = false;
    private static final Boolean UPDATED_PARTNER = true;

    private static final String DEFAULT_PARTNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity() {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .description(DEFAULT_DESCRIPTION)
            .taxNo(DEFAULT_TAX_NO)
            .thirdPartyAccountingCode(DEFAULT_THIRD_PARTY_ACCOUNTING_CODE)
            .siret(DEFAULT_SIRET)
            .ownerId(DEFAULT_OWNER_ID)
            .isParticular(DEFAULT_IS_PARTICULAR)
            .personReferers(DEFAULT_PERSON_REFERERS)
            .meansPayments(DEFAULT_MEANS_PAYMENTS)
            .subscriptions(DEFAULT_SUBSCRIPTIONS)
            .usages(DEFAULT_USAGES)
            .settingsInvoice(DEFAULT_SETTINGS_INVOICE)
            .partner(DEFAULT_PARTNER)
            .partnerId(DEFAULT_PARTNER_ID)
            .customerId(DEFAULT_CUSTOMER_ID)
            .customerName(DEFAULT_CUSTOMER_NAME);
        return customer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity() {
        Customer customer = new Customer()
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .taxNo(UPDATED_TAX_NO)
            .thirdPartyAccountingCode(UPDATED_THIRD_PARTY_ACCOUNTING_CODE)
            .siret(UPDATED_SIRET)
            .ownerId(UPDATED_OWNER_ID)
            .isParticular(UPDATED_IS_PARTICULAR)
            .personReferers(UPDATED_PERSON_REFERERS)
            .meansPayments(UPDATED_MEANS_PAYMENTS)
            .subscriptions(UPDATED_SUBSCRIPTIONS)
            .usages(UPDATED_USAGES)
            .settingsInvoice(UPDATED_SETTINGS_INVOICE)
            .partner(UPDATED_PARTNER)
            .partnerId(UPDATED_PARTNER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customerRepository.deleteAll();
        customer = createEntity();
    }

    @Test
    void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testCustomer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomer.getTaxNo()).isEqualTo(DEFAULT_TAX_NO);
        assertThat(testCustomer.getThirdPartyAccountingCode()).isEqualTo(DEFAULT_THIRD_PARTY_ACCOUNTING_CODE);
        assertThat(testCustomer.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testCustomer.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testCustomer.getIsParticular()).isEqualTo(DEFAULT_IS_PARTICULAR);
        assertThat(testCustomer.getPersonReferers()).isEqualTo(DEFAULT_PERSON_REFERERS);
        assertThat(testCustomer.getMeansPayments()).isEqualTo(DEFAULT_MEANS_PAYMENTS);
        assertThat(testCustomer.getSubscriptions()).isEqualTo(DEFAULT_SUBSCRIPTIONS);
        assertThat(testCustomer.getUsages()).isEqualTo(DEFAULT_USAGES);
        assertThat(testCustomer.getSettingsInvoice()).isEqualTo(DEFAULT_SETTINGS_INVOICE);
        assertThat(testCustomer.getPartner()).isEqualTo(DEFAULT_PARTNER);
        assertThat(testCustomer.getPartnerId()).isEqualTo(DEFAULT_PARTNER_ID);
        assertThat(testCustomer.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCustomer.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
    }

    @Test
    void createCustomerWithExistingId() throws Exception {
        // Create the Customer with an existing ID
        customer.setId("existing_id");

        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.save(customer);

        // Get all the customerList
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxNo").value(hasItem(DEFAULT_TAX_NO)))
            .andExpect(jsonPath("$.[*].thirdPartyAccountingCode").value(hasItem(DEFAULT_THIRD_PARTY_ACCOUNTING_CODE)))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET)))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].isParticular").value(hasItem(DEFAULT_IS_PARTICULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].personReferers").value(hasItem(DEFAULT_PERSON_REFERERS)))
            .andExpect(jsonPath("$.[*].meansPayments").value(hasItem(DEFAULT_MEANS_PAYMENTS)))
            .andExpect(jsonPath("$.[*].subscriptions").value(hasItem(DEFAULT_SUBSCRIPTIONS)))
            .andExpect(jsonPath("$.[*].usages").value(hasItem(DEFAULT_USAGES)))
            .andExpect(jsonPath("$.[*].settingsInvoice").value(hasItem(DEFAULT_SETTINGS_INVOICE)))
            .andExpect(jsonPath("$.[*].partner").value(hasItem(DEFAULT_PARTNER.booleanValue())))
            .andExpect(jsonPath("$.[*].partnerId").value(hasItem(DEFAULT_PARTNER_ID)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)));
    }

    @Test
    void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.save(customer);

        // Get the customer
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL_ID, customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.taxNo").value(DEFAULT_TAX_NO))
            .andExpect(jsonPath("$.thirdPartyAccountingCode").value(DEFAULT_THIRD_PARTY_ACCOUNTING_CODE))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID))
            .andExpect(jsonPath("$.isParticular").value(DEFAULT_IS_PARTICULAR.booleanValue()))
            .andExpect(jsonPath("$.personReferers").value(DEFAULT_PERSON_REFERERS))
            .andExpect(jsonPath("$.meansPayments").value(DEFAULT_MEANS_PAYMENTS))
            .andExpect(jsonPath("$.subscriptions").value(DEFAULT_SUBSCRIPTIONS))
            .andExpect(jsonPath("$.usages").value(DEFAULT_USAGES))
            .andExpect(jsonPath("$.settingsInvoice").value(DEFAULT_SETTINGS_INVOICE))
            .andExpect(jsonPath("$.partner").value(DEFAULT_PARTNER.booleanValue()))
            .andExpect(jsonPath("$.partnerId").value(DEFAULT_PARTNER_ID))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME));
    }

    @Test
    void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewCustomer() throws Exception {
        // Initialize the database
        customerRepository.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        updatedCustomer
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .taxNo(UPDATED_TAX_NO)
            .thirdPartyAccountingCode(UPDATED_THIRD_PARTY_ACCOUNTING_CODE)
            .siret(UPDATED_SIRET)
            .ownerId(UPDATED_OWNER_ID)
            .isParticular(UPDATED_IS_PARTICULAR)
            .personReferers(UPDATED_PERSON_REFERERS)
            .meansPayments(UPDATED_MEANS_PAYMENTS)
            .subscriptions(UPDATED_SUBSCRIPTIONS)
            .usages(UPDATED_USAGES)
            .settingsInvoice(UPDATED_SETTINGS_INVOICE)
            .partner(UPDATED_PARTNER)
            .partnerId(UPDATED_PARTNER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME);

        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testCustomer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomer.getTaxNo()).isEqualTo(UPDATED_TAX_NO);
        assertThat(testCustomer.getThirdPartyAccountingCode()).isEqualTo(UPDATED_THIRD_PARTY_ACCOUNTING_CODE);
        assertThat(testCustomer.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testCustomer.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testCustomer.getIsParticular()).isEqualTo(UPDATED_IS_PARTICULAR);
        assertThat(testCustomer.getPersonReferers()).isEqualTo(UPDATED_PERSON_REFERERS);
        assertThat(testCustomer.getMeansPayments()).isEqualTo(UPDATED_MEANS_PAYMENTS);
        assertThat(testCustomer.getSubscriptions()).isEqualTo(UPDATED_SUBSCRIPTIONS);
        assertThat(testCustomer.getUsages()).isEqualTo(UPDATED_USAGES);
        assertThat(testCustomer.getSettingsInvoice()).isEqualTo(UPDATED_SETTINGS_INVOICE);
        assertThat(testCustomer.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testCustomer.getPartnerId()).isEqualTo(UPDATED_PARTNER_ID);
        assertThat(testCustomer.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustomer.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
    }

    @Test
    void putNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .name(UPDATED_NAME)
            .thirdPartyAccountingCode(UPDATED_THIRD_PARTY_ACCOUNTING_CODE)
            .isParticular(UPDATED_IS_PARTICULAR)
            .personReferers(UPDATED_PERSON_REFERERS)
            .settingsInvoice(UPDATED_SETTINGS_INVOICE)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testCustomer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomer.getTaxNo()).isEqualTo(DEFAULT_TAX_NO);
        assertThat(testCustomer.getThirdPartyAccountingCode()).isEqualTo(UPDATED_THIRD_PARTY_ACCOUNTING_CODE);
        assertThat(testCustomer.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testCustomer.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testCustomer.getIsParticular()).isEqualTo(UPDATED_IS_PARTICULAR);
        assertThat(testCustomer.getPersonReferers()).isEqualTo(UPDATED_PERSON_REFERERS);
        assertThat(testCustomer.getMeansPayments()).isEqualTo(DEFAULT_MEANS_PAYMENTS);
        assertThat(testCustomer.getSubscriptions()).isEqualTo(DEFAULT_SUBSCRIPTIONS);
        assertThat(testCustomer.getUsages()).isEqualTo(DEFAULT_USAGES);
        assertThat(testCustomer.getSettingsInvoice()).isEqualTo(UPDATED_SETTINGS_INVOICE);
        assertThat(testCustomer.getPartner()).isEqualTo(DEFAULT_PARTNER);
        assertThat(testCustomer.getPartnerId()).isEqualTo(DEFAULT_PARTNER_ID);
        assertThat(testCustomer.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustomer.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
    }

    @Test
    void fullUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .description(UPDATED_DESCRIPTION)
            .taxNo(UPDATED_TAX_NO)
            .thirdPartyAccountingCode(UPDATED_THIRD_PARTY_ACCOUNTING_CODE)
            .siret(UPDATED_SIRET)
            .ownerId(UPDATED_OWNER_ID)
            .isParticular(UPDATED_IS_PARTICULAR)
            .personReferers(UPDATED_PERSON_REFERERS)
            .meansPayments(UPDATED_MEANS_PAYMENTS)
            .subscriptions(UPDATED_SUBSCRIPTIONS)
            .usages(UPDATED_USAGES)
            .settingsInvoice(UPDATED_SETTINGS_INVOICE)
            .partner(UPDATED_PARTNER)
            .partnerId(UPDATED_PARTNER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testCustomer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomer.getTaxNo()).isEqualTo(UPDATED_TAX_NO);
        assertThat(testCustomer.getThirdPartyAccountingCode()).isEqualTo(UPDATED_THIRD_PARTY_ACCOUNTING_CODE);
        assertThat(testCustomer.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testCustomer.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testCustomer.getIsParticular()).isEqualTo(UPDATED_IS_PARTICULAR);
        assertThat(testCustomer.getPersonReferers()).isEqualTo(UPDATED_PERSON_REFERERS);
        assertThat(testCustomer.getMeansPayments()).isEqualTo(UPDATED_MEANS_PAYMENTS);
        assertThat(testCustomer.getSubscriptions()).isEqualTo(UPDATED_SUBSCRIPTIONS);
        assertThat(testCustomer.getUsages()).isEqualTo(UPDATED_USAGES);
        assertThat(testCustomer.getSettingsInvoice()).isEqualTo(UPDATED_SETTINGS_INVOICE);
        assertThat(testCustomer.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testCustomer.getPartnerId()).isEqualTo(UPDATED_PARTNER_ID);
        assertThat(testCustomer.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustomer.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
    }

    @Test
    void patchNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc
            .perform(delete(ENTITY_API_URL_ID, customer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
