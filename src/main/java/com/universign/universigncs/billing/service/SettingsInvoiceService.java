package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.SettingsInvoice;
import com.universign.universigncs.billing.repository.SettingsInvoiceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SettingsInvoice}.
 */
@Service
public class SettingsInvoiceService {

    private final Logger log = LoggerFactory.getLogger(SettingsInvoiceService.class);

    private final SettingsInvoiceRepository settingsInvoiceRepository;

    public SettingsInvoiceService(SettingsInvoiceRepository settingsInvoiceRepository) {
        this.settingsInvoiceRepository = settingsInvoiceRepository;
    }

    /**
     * Save a settingsInvoice.
     *
     * @param settingsInvoice the entity to save.
     * @return the persisted entity.
     */
    public SettingsInvoice save(SettingsInvoice settingsInvoice) {
        log.debug("Request to save SettingsInvoice : {}", settingsInvoice);
        return settingsInvoiceRepository.save(settingsInvoice);
    }

    /**
     * Partially update a settingsInvoice.
     *
     * @param settingsInvoice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SettingsInvoice> partialUpdate(SettingsInvoice settingsInvoice) {
        log.debug("Request to partially update SettingsInvoice : {}", settingsInvoice);

        return settingsInvoiceRepository
            .findById(settingsInvoice.getId())
            .map(existingSettingsInvoice -> {
                if (settingsInvoice.getGlobal() != null) {
                    existingSettingsInvoice.setGlobal(settingsInvoice.getGlobal());
                }
                if (settingsInvoice.getSubscription() != null) {
                    existingSettingsInvoice.setSubscription(settingsInvoice.getSubscription());
                }
                if (settingsInvoice.getTaxPerCent() != null) {
                    existingSettingsInvoice.setTaxPerCent(settingsInvoice.getTaxPerCent());
                }
                if (settingsInvoice.getDetailSkipped() != null) {
                    existingSettingsInvoice.setDetailSkipped(settingsInvoice.getDetailSkipped());
                }
                if (settingsInvoice.getManualBillingOnly() != null) {
                    existingSettingsInvoice.setManualBillingOnly(settingsInvoice.getManualBillingOnly());
                }
                if (settingsInvoice.getBillingActive() != null) {
                    existingSettingsInvoice.setBillingActive(settingsInvoice.getBillingActive());
                }
                if (settingsInvoice.getPerOrganization() != null) {
                    existingSettingsInvoice.setPerOrganization(settingsInvoice.getPerOrganization());
                }
                if (settingsInvoice.getFullyAutomatic() != null) {
                    existingSettingsInvoice.setFullyAutomatic(settingsInvoice.getFullyAutomatic());
                }
                if (settingsInvoice.getPeriod() != null) {
                    existingSettingsInvoice.setPeriod(settingsInvoice.getPeriod());
                }
                if (settingsInvoice.getLocale() != null) {
                    existingSettingsInvoice.setLocale(settingsInvoice.getLocale());
                }
                if (settingsInvoice.getPaymentTerms() != null) {
                    existingSettingsInvoice.setPaymentTerms(settingsInvoice.getPaymentTerms());
                }

                return existingSettingsInvoice;
            })
            .map(settingsInvoiceRepository::save);
    }

    /**
     * Get all the settingsInvoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SettingsInvoice> findAll(Pageable pageable) {
        log.debug("Request to get all SettingsInvoices");
        return settingsInvoiceRepository.findAll(pageable);
    }

    /**
     * Get one settingsInvoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SettingsInvoice> findOne(String id) {
        log.debug("Request to get SettingsInvoice : {}", id);
        return settingsInvoiceRepository.findById(id);
    }

    /**
     * Delete the settingsInvoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SettingsInvoice : {}", id);
        settingsInvoiceRepository.deleteById(id);
    }
}
