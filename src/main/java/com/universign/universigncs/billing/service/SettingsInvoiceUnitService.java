package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.SettingsInvoiceUnit;
import com.universign.universigncs.billing.repository.SettingsInvoiceUnitRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SettingsInvoiceUnit}.
 */
@Service
public class SettingsInvoiceUnitService {

    private final Logger log = LoggerFactory.getLogger(SettingsInvoiceUnitService.class);

    private final SettingsInvoiceUnitRepository settingsInvoiceUnitRepository;

    public SettingsInvoiceUnitService(SettingsInvoiceUnitRepository settingsInvoiceUnitRepository) {
        this.settingsInvoiceUnitRepository = settingsInvoiceUnitRepository;
    }

    /**
     * Save a settingsInvoiceUnit.
     *
     * @param settingsInvoiceUnit the entity to save.
     * @return the persisted entity.
     */
    public SettingsInvoiceUnit save(SettingsInvoiceUnit settingsInvoiceUnit) {
        log.debug("Request to save SettingsInvoiceUnit : {}", settingsInvoiceUnit);
        return settingsInvoiceUnitRepository.save(settingsInvoiceUnit);
    }

    /**
     * Partially update a settingsInvoiceUnit.
     *
     * @param settingsInvoiceUnit the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SettingsInvoiceUnit> partialUpdate(SettingsInvoiceUnit settingsInvoiceUnit) {
        log.debug("Request to partially update SettingsInvoiceUnit : {}", settingsInvoiceUnit);

        return settingsInvoiceUnitRepository
            .findById(settingsInvoiceUnit.getId())
            .map(existingSettingsInvoiceUnit -> {
                if (settingsInvoiceUnit.getSeller() != null) {
                    existingSettingsInvoiceUnit.setSeller(settingsInvoiceUnit.getSeller());
                }
                if (settingsInvoiceUnit.getPersonBuyerId() != null) {
                    existingSettingsInvoiceUnit.setPersonBuyerId(settingsInvoiceUnit.getPersonBuyerId());
                }
                if (settingsInvoiceUnit.getPersonRefererCopys() != null) {
                    existingSettingsInvoiceUnit.setPersonRefererCopys(settingsInvoiceUnit.getPersonRefererCopys());
                }
                if (settingsInvoiceUnit.getSubscriptionId() != null) {
                    existingSettingsInvoiceUnit.setSubscriptionId(settingsInvoiceUnit.getSubscriptionId());
                }
                if (settingsInvoiceUnit.getUseBillingAddress() != null) {
                    existingSettingsInvoiceUnit.setUseBillingAddress(settingsInvoiceUnit.getUseBillingAddress());
                }
                if (settingsInvoiceUnit.getPaymentMethod() != null) {
                    existingSettingsInvoiceUnit.setPaymentMethod(settingsInvoiceUnit.getPaymentMethod());
                }

                return existingSettingsInvoiceUnit;
            })
            .map(settingsInvoiceUnitRepository::save);
    }

    /**
     * Get all the settingsInvoiceUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SettingsInvoiceUnit> findAll(Pageable pageable) {
        log.debug("Request to get all SettingsInvoiceUnits");
        return settingsInvoiceUnitRepository.findAll(pageable);
    }

    /**
     * Get one settingsInvoiceUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SettingsInvoiceUnit> findOne(String id) {
        log.debug("Request to get SettingsInvoiceUnit : {}", id);
        return settingsInvoiceUnitRepository.findById(id);
    }

    /**
     * Delete the settingsInvoiceUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SettingsInvoiceUnit : {}", id);
        settingsInvoiceUnitRepository.deleteById(id);
    }
}
