package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.InvoiceItem;
import com.universign.universigncs.billing.repository.InvoiceItemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link InvoiceItem}.
 */
@Service
public class InvoiceItemService {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemService.class);

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    /**
     * Save a invoiceItem.
     *
     * @param invoiceItem the entity to save.
     * @return the persisted entity.
     */
    public InvoiceItem save(InvoiceItem invoiceItem) {
        log.debug("Request to save InvoiceItem : {}", invoiceItem);
        return invoiceItemRepository.save(invoiceItem);
    }

    /**
     * Partially update a invoiceItem.
     *
     * @param invoiceItem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoiceItem> partialUpdate(InvoiceItem invoiceItem) {
        log.debug("Request to partially update InvoiceItem : {}", invoiceItem);

        return invoiceItemRepository
            .findById(invoiceItem.getId())
            .map(existingInvoiceItem -> {
                if (invoiceItem.getName() != null) {
                    existingInvoiceItem.setName(invoiceItem.getName());
                }
                if (invoiceItem.getItemType() != null) {
                    existingInvoiceItem.setItemType(invoiceItem.getItemType());
                }
                if (invoiceItem.getMinStep() != null) {
                    existingInvoiceItem.setMinStep(invoiceItem.getMinStep());
                }
                if (invoiceItem.getMaxStep() != null) {
                    existingInvoiceItem.setMaxStep(invoiceItem.getMaxStep());
                }
                if (invoiceItem.getQuantity() != null) {
                    existingInvoiceItem.setQuantity(invoiceItem.getQuantity());
                }
                if (invoiceItem.getUnitPrice() != null) {
                    existingInvoiceItem.setUnitPrice(invoiceItem.getUnitPrice());
                }
                if (invoiceItem.getDiscount() != null) {
                    existingInvoiceItem.setDiscount(invoiceItem.getDiscount());
                }
                if (invoiceItem.getPrice() != null) {
                    existingInvoiceItem.setPrice(invoiceItem.getPrice());
                }
                if (invoiceItem.getProduct() != null) {
                    existingInvoiceItem.setProduct(invoiceItem.getProduct());
                }
                if (invoiceItem.getGlobalQuantity() != null) {
                    existingInvoiceItem.setGlobalQuantity(invoiceItem.getGlobalQuantity());
                }
                if (invoiceItem.getUntilDate() != null) {
                    existingInvoiceItem.setUntilDate(invoiceItem.getUntilDate());
                }

                return existingInvoiceItem;
            })
            .map(invoiceItemRepository::save);
    }

    /**
     * Get all the invoiceItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<InvoiceItem> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceItems");
        return invoiceItemRepository.findAll(pageable);
    }

    /**
     * Get one invoiceItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<InvoiceItem> findOne(String id) {
        log.debug("Request to get InvoiceItem : {}", id);
        return invoiceItemRepository.findById(id);
    }

    /**
     * Delete the invoiceItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete InvoiceItem : {}", id);
        invoiceItemRepository.deleteById(id);
    }
}
