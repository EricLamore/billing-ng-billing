package com.universign.universigncs.billing.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.billing.IntegrationTest;
import com.universign.universigncs.billing.domain.Invoice;
import com.universign.universigncs.billing.domain.enumeration.InvoiceType;
import com.universign.universigncs.billing.domain.enumeration.Status;
import com.universign.universigncs.billing.repository.InvoiceRepository;
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
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InvoiceResourceIT {

    private static final String DEFAULT_HUMAN_ID = "AAAAAAAAAA";
    private static final String UPDATED_HUMAN_ID = "BBBBBBBBBB";

    private static final InvoiceType DEFAULT_TYPE = InvoiceType.INVOICE;
    private static final InvoiceType UPDATED_TYPE = InvoiceType.INVOICE_ONE_SHOT;

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final LocalDate DEFAULT_EMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ITEMS = "AAAAAAAAAA";
    private static final String UPDATED_ITEMS = "BBBBBBBBBB";

    private static final Double DEFAULT_VAT = 1D;
    private static final Double UPDATED_VAT = 2D;

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_TO_SEND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO_SEND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SEND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SEND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUNNING_SEND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUNNING_SEND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREDIT_NOTE_SEND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREDIT_NOTE_SEND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Status DEFAULT_STATUS = Status.DRAFT;
    private static final Status UPDATED_STATUS = Status.CREATED;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PVALIDATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PVALIDATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VALIDATOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENTS_HISTORIC = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENTS_HISTORIC = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_REFUND = "AAAAAAAAAA";
    private static final String UPDATED_REFUND = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_ITEMS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_ITEMS = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_PRICE_GROSS = 1D;
    private static final Double UPDATED_TOTAL_PRICE_GROSS = 2D;

    private static final Integer DEFAULT_REFUND_AMOUNT = 1;
    private static final Integer UPDATED_REFUND_AMOUNT = 2;

    private static final String ENTITY_API_URL = "/api/invoices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity() {
        Invoice invoice = new Invoice()
            .humanId(DEFAULT_HUMAN_ID)
            .type(DEFAULT_TYPE)
            .customerId(DEFAULT_CUSTOMER_ID)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .emissionDate(DEFAULT_EMISSION_DATE)
            .items(DEFAULT_ITEMS)
            .vat(DEFAULT_VAT)
            .dueDate(DEFAULT_DUE_DATE)
            .dateToSend(DEFAULT_DATE_TO_SEND)
            .sendDate(DEFAULT_SEND_DATE)
            .dunningSendDate(DEFAULT_DUNNING_SEND_DATE)
            .creditNoteSendDate(DEFAULT_CREDIT_NOTE_SEND_DATE)
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS)
            .pvalidationDate(DEFAULT_PVALIDATION_DATE)
            .validatorId(DEFAULT_VALIDATOR_ID)
            .payment(DEFAULT_PAYMENT)
            .paymentsHistoric(DEFAULT_PAYMENTS_HISTORIC)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .refund(DEFAULT_REFUND)
            .purchaseOrder(DEFAULT_PURCHASE_ORDER)
            .message(DEFAULT_MESSAGE)
            .additionalItems(DEFAULT_ADDITIONAL_ITEMS)
            .totalPriceGross(DEFAULT_TOTAL_PRICE_GROSS)
            .refundAmount(DEFAULT_REFUND_AMOUNT);
        return invoice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity() {
        Invoice invoice = new Invoice()
            .humanId(UPDATED_HUMAN_ID)
            .type(UPDATED_TYPE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .emissionDate(UPDATED_EMISSION_DATE)
            .items(UPDATED_ITEMS)
            .vat(UPDATED_VAT)
            .dueDate(UPDATED_DUE_DATE)
            .dateToSend(UPDATED_DATE_TO_SEND)
            .sendDate(UPDATED_SEND_DATE)
            .dunningSendDate(UPDATED_DUNNING_SEND_DATE)
            .creditNoteSendDate(UPDATED_CREDIT_NOTE_SEND_DATE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS)
            .pvalidationDate(UPDATED_PVALIDATION_DATE)
            .validatorId(UPDATED_VALIDATOR_ID)
            .payment(UPDATED_PAYMENT)
            .paymentsHistoric(UPDATED_PAYMENTS_HISTORIC)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .refund(UPDATED_REFUND)
            .purchaseOrder(UPDATED_PURCHASE_ORDER)
            .message(UPDATED_MESSAGE)
            .additionalItems(UPDATED_ADDITIONAL_ITEMS)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .refundAmount(UPDATED_REFUND_AMOUNT);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoiceRepository.deleteAll();
        invoice = createEntity();
    }

    @Test
    void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();
        // Create the Invoice
        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getHumanId()).isEqualTo(DEFAULT_HUMAN_ID);
        assertThat(testInvoice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInvoice.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testInvoice.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testInvoice.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testInvoice.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testInvoice.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testInvoice.getItems()).isEqualTo(DEFAULT_ITEMS);
        assertThat(testInvoice.getVat()).isEqualTo(DEFAULT_VAT);
        assertThat(testInvoice.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testInvoice.getDateToSend()).isEqualTo(DEFAULT_DATE_TO_SEND);
        assertThat(testInvoice.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
        assertThat(testInvoice.getDunningSendDate()).isEqualTo(DEFAULT_DUNNING_SEND_DATE);
        assertThat(testInvoice.getCreditNoteSendDate()).isEqualTo(DEFAULT_CREDIT_NOTE_SEND_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoice.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInvoice.getPvalidationDate()).isEqualTo(DEFAULT_PVALIDATION_DATE);
        assertThat(testInvoice.getValidatorId()).isEqualTo(DEFAULT_VALIDATOR_ID);
        assertThat(testInvoice.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testInvoice.getPaymentsHistoric()).isEqualTo(DEFAULT_PAYMENTS_HISTORIC);
        assertThat(testInvoice.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testInvoice.getRefund()).isEqualTo(DEFAULT_REFUND);
        assertThat(testInvoice.getPurchaseOrder()).isEqualTo(DEFAULT_PURCHASE_ORDER);
        assertThat(testInvoice.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testInvoice.getAdditionalItems()).isEqualTo(DEFAULT_ADDITIONAL_ITEMS);
        assertThat(testInvoice.getTotalPriceGross()).isEqualTo(DEFAULT_TOTAL_PRICE_GROSS);
        assertThat(testInvoice.getRefundAmount()).isEqualTo(DEFAULT_REFUND_AMOUNT);
    }

    @Test
    void createInvoiceWithExistingId() throws Exception {
        // Create the Invoice with an existing ID
        invoice.setId("existing_id");

        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.save(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId())))
            .andExpect(jsonPath("$.[*].humanId").value(hasItem(DEFAULT_HUMAN_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].items").value(hasItem(DEFAULT_ITEMS)))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateToSend").value(hasItem(DEFAULT_DATE_TO_SEND.toString())))
            .andExpect(jsonPath("$.[*].sendDate").value(hasItem(DEFAULT_SEND_DATE.toString())))
            .andExpect(jsonPath("$.[*].dunningSendDate").value(hasItem(DEFAULT_DUNNING_SEND_DATE.toString())))
            .andExpect(jsonPath("$.[*].creditNoteSendDate").value(hasItem(DEFAULT_CREDIT_NOTE_SEND_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].pvalidationDate").value(hasItem(DEFAULT_PVALIDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].validatorId").value(hasItem(DEFAULT_VALIDATOR_ID)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)))
            .andExpect(jsonPath("$.[*].paymentsHistoric").value(hasItem(DEFAULT_PAYMENTS_HISTORIC)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].refund").value(hasItem(DEFAULT_REFUND)))
            .andExpect(jsonPath("$.[*].purchaseOrder").value(hasItem(DEFAULT_PURCHASE_ORDER)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].additionalItems").value(hasItem(DEFAULT_ADDITIONAL_ITEMS)))
            .andExpect(jsonPath("$.[*].totalPriceGross").value(hasItem(DEFAULT_TOTAL_PRICE_GROSS.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmount").value(hasItem(DEFAULT_REFUND_AMOUNT)));
    }

    @Test
    void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.save(invoice);

        // Get the invoice
        restInvoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId()))
            .andExpect(jsonPath("$.humanId").value(DEFAULT_HUMAN_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.emissionDate").value(DEFAULT_EMISSION_DATE.toString()))
            .andExpect(jsonPath("$.items").value(DEFAULT_ITEMS))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.doubleValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.dateToSend").value(DEFAULT_DATE_TO_SEND.toString()))
            .andExpect(jsonPath("$.sendDate").value(DEFAULT_SEND_DATE.toString()))
            .andExpect(jsonPath("$.dunningSendDate").value(DEFAULT_DUNNING_SEND_DATE.toString()))
            .andExpect(jsonPath("$.creditNoteSendDate").value(DEFAULT_CREDIT_NOTE_SEND_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.pvalidationDate").value(DEFAULT_PVALIDATION_DATE.toString()))
            .andExpect(jsonPath("$.validatorId").value(DEFAULT_VALIDATOR_ID))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT))
            .andExpect(jsonPath("$.paymentsHistoric").value(DEFAULT_PAYMENTS_HISTORIC))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.refund").value(DEFAULT_REFUND))
            .andExpect(jsonPath("$.purchaseOrder").value(DEFAULT_PURCHASE_ORDER))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.additionalItems").value(DEFAULT_ADDITIONAL_ITEMS))
            .andExpect(jsonPath("$.totalPriceGross").value(DEFAULT_TOTAL_PRICE_GROSS.doubleValue()))
            .andExpect(jsonPath("$.refundAmount").value(DEFAULT_REFUND_AMOUNT));
    }

    @Test
    void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        updatedInvoice
            .humanId(UPDATED_HUMAN_ID)
            .type(UPDATED_TYPE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .emissionDate(UPDATED_EMISSION_DATE)
            .items(UPDATED_ITEMS)
            .vat(UPDATED_VAT)
            .dueDate(UPDATED_DUE_DATE)
            .dateToSend(UPDATED_DATE_TO_SEND)
            .sendDate(UPDATED_SEND_DATE)
            .dunningSendDate(UPDATED_DUNNING_SEND_DATE)
            .creditNoteSendDate(UPDATED_CREDIT_NOTE_SEND_DATE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS)
            .pvalidationDate(UPDATED_PVALIDATION_DATE)
            .validatorId(UPDATED_VALIDATOR_ID)
            .payment(UPDATED_PAYMENT)
            .paymentsHistoric(UPDATED_PAYMENTS_HISTORIC)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .refund(UPDATED_REFUND)
            .purchaseOrder(UPDATED_PURCHASE_ORDER)
            .message(UPDATED_MESSAGE)
            .additionalItems(UPDATED_ADDITIONAL_ITEMS)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .refundAmount(UPDATED_REFUND_AMOUNT);

        restInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInvoice))
            )
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getHumanId()).isEqualTo(UPDATED_HUMAN_ID);
        assertThat(testInvoice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoice.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testInvoice.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testInvoice.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testInvoice.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testInvoice.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testInvoice.getItems()).isEqualTo(UPDATED_ITEMS);
        assertThat(testInvoice.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getDateToSend()).isEqualTo(UPDATED_DATE_TO_SEND);
        assertThat(testInvoice.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
        assertThat(testInvoice.getDunningSendDate()).isEqualTo(UPDATED_DUNNING_SEND_DATE);
        assertThat(testInvoice.getCreditNoteSendDate()).isEqualTo(UPDATED_CREDIT_NOTE_SEND_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoice.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInvoice.getPvalidationDate()).isEqualTo(UPDATED_PVALIDATION_DATE);
        assertThat(testInvoice.getValidatorId()).isEqualTo(UPDATED_VALIDATOR_ID);
        assertThat(testInvoice.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testInvoice.getPaymentsHistoric()).isEqualTo(UPDATED_PAYMENTS_HISTORIC);
        assertThat(testInvoice.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testInvoice.getRefund()).isEqualTo(UPDATED_REFUND);
        assertThat(testInvoice.getPurchaseOrder()).isEqualTo(UPDATED_PURCHASE_ORDER);
        assertThat(testInvoice.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testInvoice.getAdditionalItems()).isEqualTo(UPDATED_ADDITIONAL_ITEMS);
        assertThat(testInvoice.getTotalPriceGross()).isEqualTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testInvoice.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
    }

    @Test
    void putNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();
        invoice.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();
        invoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();
        invoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInvoiceWithPatch() throws Exception {
        // Initialize the database
        invoiceRepository.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice using partial update
        Invoice partialUpdatedInvoice = new Invoice();
        partialUpdatedInvoice.setId(invoice.getId());

        partialUpdatedInvoice
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .month(UPDATED_MONTH)
            .vat(UPDATED_VAT)
            .dueDate(UPDATED_DUE_DATE)
            .dateToSend(UPDATED_DATE_TO_SEND)
            .creditNoteSendDate(UPDATED_CREDIT_NOTE_SEND_DATE)
            .pvalidationDate(UPDATED_PVALIDATION_DATE)
            .paymentsHistoric(UPDATED_PAYMENTS_HISTORIC)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .purchaseOrder(UPDATED_PURCHASE_ORDER)
            .additionalItems(UPDATED_ADDITIONAL_ITEMS)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .refundAmount(UPDATED_REFUND_AMOUNT);

        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoice))
            )
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getHumanId()).isEqualTo(DEFAULT_HUMAN_ID);
        assertThat(testInvoice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInvoice.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testInvoice.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testInvoice.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testInvoice.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testInvoice.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testInvoice.getItems()).isEqualTo(DEFAULT_ITEMS);
        assertThat(testInvoice.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getDateToSend()).isEqualTo(UPDATED_DATE_TO_SEND);
        assertThat(testInvoice.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
        assertThat(testInvoice.getDunningSendDate()).isEqualTo(DEFAULT_DUNNING_SEND_DATE);
        assertThat(testInvoice.getCreditNoteSendDate()).isEqualTo(UPDATED_CREDIT_NOTE_SEND_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoice.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInvoice.getPvalidationDate()).isEqualTo(UPDATED_PVALIDATION_DATE);
        assertThat(testInvoice.getValidatorId()).isEqualTo(DEFAULT_VALIDATOR_ID);
        assertThat(testInvoice.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testInvoice.getPaymentsHistoric()).isEqualTo(UPDATED_PAYMENTS_HISTORIC);
        assertThat(testInvoice.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testInvoice.getRefund()).isEqualTo(DEFAULT_REFUND);
        assertThat(testInvoice.getPurchaseOrder()).isEqualTo(UPDATED_PURCHASE_ORDER);
        assertThat(testInvoice.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testInvoice.getAdditionalItems()).isEqualTo(UPDATED_ADDITIONAL_ITEMS);
        assertThat(testInvoice.getTotalPriceGross()).isEqualTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testInvoice.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
    }

    @Test
    void fullUpdateInvoiceWithPatch() throws Exception {
        // Initialize the database
        invoiceRepository.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice using partial update
        Invoice partialUpdatedInvoice = new Invoice();
        partialUpdatedInvoice.setId(invoice.getId());

        partialUpdatedInvoice
            .humanId(UPDATED_HUMAN_ID)
            .type(UPDATED_TYPE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .emissionDate(UPDATED_EMISSION_DATE)
            .items(UPDATED_ITEMS)
            .vat(UPDATED_VAT)
            .dueDate(UPDATED_DUE_DATE)
            .dateToSend(UPDATED_DATE_TO_SEND)
            .sendDate(UPDATED_SEND_DATE)
            .dunningSendDate(UPDATED_DUNNING_SEND_DATE)
            .creditNoteSendDate(UPDATED_CREDIT_NOTE_SEND_DATE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS)
            .pvalidationDate(UPDATED_PVALIDATION_DATE)
            .validatorId(UPDATED_VALIDATOR_ID)
            .payment(UPDATED_PAYMENT)
            .paymentsHistoric(UPDATED_PAYMENTS_HISTORIC)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .refund(UPDATED_REFUND)
            .purchaseOrder(UPDATED_PURCHASE_ORDER)
            .message(UPDATED_MESSAGE)
            .additionalItems(UPDATED_ADDITIONAL_ITEMS)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .refundAmount(UPDATED_REFUND_AMOUNT);

        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoice))
            )
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getHumanId()).isEqualTo(UPDATED_HUMAN_ID);
        assertThat(testInvoice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoice.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testInvoice.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testInvoice.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testInvoice.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testInvoice.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testInvoice.getItems()).isEqualTo(UPDATED_ITEMS);
        assertThat(testInvoice.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getDateToSend()).isEqualTo(UPDATED_DATE_TO_SEND);
        assertThat(testInvoice.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
        assertThat(testInvoice.getDunningSendDate()).isEqualTo(UPDATED_DUNNING_SEND_DATE);
        assertThat(testInvoice.getCreditNoteSendDate()).isEqualTo(UPDATED_CREDIT_NOTE_SEND_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoice.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInvoice.getPvalidationDate()).isEqualTo(UPDATED_PVALIDATION_DATE);
        assertThat(testInvoice.getValidatorId()).isEqualTo(UPDATED_VALIDATOR_ID);
        assertThat(testInvoice.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testInvoice.getPaymentsHistoric()).isEqualTo(UPDATED_PAYMENTS_HISTORIC);
        assertThat(testInvoice.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testInvoice.getRefund()).isEqualTo(UPDATED_REFUND);
        assertThat(testInvoice.getPurchaseOrder()).isEqualTo(UPDATED_PURCHASE_ORDER);
        assertThat(testInvoice.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testInvoice.getAdditionalItems()).isEqualTo(UPDATED_ADDITIONAL_ITEMS);
        assertThat(testInvoice.getTotalPriceGross()).isEqualTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testInvoice.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
    }

    @Test
    void patchNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();
        invoice.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();
        invoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();
        invoice.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
