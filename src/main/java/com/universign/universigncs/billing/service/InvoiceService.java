package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.Invoice;
import com.universign.universigncs.billing.repository.InvoiceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    /**
     * Partially update a invoice.
     *
     * @param invoice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Invoice> partialUpdate(Invoice invoice) {
        log.debug("Request to partially update Invoice : {}", invoice);

        return invoiceRepository
            .findById(invoice.getId())
            .map(existingInvoice -> {
                if (invoice.getHumanId() != null) {
                    existingInvoice.setHumanId(invoice.getHumanId());
                }
                if (invoice.getType() != null) {
                    existingInvoice.setType(invoice.getType());
                }
                if (invoice.getCustomerId() != null) {
                    existingInvoice.setCustomerId(invoice.getCustomerId());
                }
                if (invoice.getCustomerName() != null) {
                    existingInvoice.setCustomerName(invoice.getCustomerName());
                }
                if (invoice.getMonth() != null) {
                    existingInvoice.setMonth(invoice.getMonth());
                }
                if (invoice.getYear() != null) {
                    existingInvoice.setYear(invoice.getYear());
                }
                if (invoice.getEmissionDate() != null) {
                    existingInvoice.setEmissionDate(invoice.getEmissionDate());
                }
                if (invoice.getItems() != null) {
                    existingInvoice.setItems(invoice.getItems());
                }
                if (invoice.getVat() != null) {
                    existingInvoice.setVat(invoice.getVat());
                }
                if (invoice.getDueDate() != null) {
                    existingInvoice.setDueDate(invoice.getDueDate());
                }
                if (invoice.getDateToSend() != null) {
                    existingInvoice.setDateToSend(invoice.getDateToSend());
                }
                if (invoice.getSendDate() != null) {
                    existingInvoice.setSendDate(invoice.getSendDate());
                }
                if (invoice.getDunningSendDate() != null) {
                    existingInvoice.setDunningSendDate(invoice.getDunningSendDate());
                }
                if (invoice.getCreditNoteSendDate() != null) {
                    existingInvoice.setCreditNoteSendDate(invoice.getCreditNoteSendDate());
                }
                if (invoice.getStatus() != null) {
                    existingInvoice.setStatus(invoice.getStatus());
                }
                if (invoice.getComments() != null) {
                    existingInvoice.setComments(invoice.getComments());
                }
                if (invoice.getPvalidationDate() != null) {
                    existingInvoice.setPvalidationDate(invoice.getPvalidationDate());
                }
                if (invoice.getValidatorId() != null) {
                    existingInvoice.setValidatorId(invoice.getValidatorId());
                }
                if (invoice.getPayment() != null) {
                    existingInvoice.setPayment(invoice.getPayment());
                }
                if (invoice.getPaymentsHistoric() != null) {
                    existingInvoice.setPaymentsHistoric(invoice.getPaymentsHistoric());
                }
                if (invoice.getPaymentMethod() != null) {
                    existingInvoice.setPaymentMethod(invoice.getPaymentMethod());
                }
                if (invoice.getRefund() != null) {
                    existingInvoice.setRefund(invoice.getRefund());
                }
                if (invoice.getPurchaseOrder() != null) {
                    existingInvoice.setPurchaseOrder(invoice.getPurchaseOrder());
                }
                if (invoice.getMessage() != null) {
                    existingInvoice.setMessage(invoice.getMessage());
                }
                if (invoice.getAdditionalItems() != null) {
                    existingInvoice.setAdditionalItems(invoice.getAdditionalItems());
                }
                if (invoice.getTotalPriceGross() != null) {
                    existingInvoice.setTotalPriceGross(invoice.getTotalPriceGross());
                }
                if (invoice.getRefundAmount() != null) {
                    existingInvoice.setRefundAmount(invoice.getRefundAmount());
                }

                return existingInvoice;
            })
            .map(invoiceRepository::save);
    }

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Invoice> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable);
    }

    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Invoice> findOne(String id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
