package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.SubscriptionTmp;
import com.universign.universigncs.billing.repository.SubscriptionTmpRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SubscriptionTmp}.
 */
@Service
public class SubscriptionTmpService {

    private final Logger log = LoggerFactory.getLogger(SubscriptionTmpService.class);

    private final SubscriptionTmpRepository subscriptionTmpRepository;

    public SubscriptionTmpService(SubscriptionTmpRepository subscriptionTmpRepository) {
        this.subscriptionTmpRepository = subscriptionTmpRepository;
    }

    /**
     * Save a subscriptionTmp.
     *
     * @param subscriptionTmp the entity to save.
     * @return the persisted entity.
     */
    public SubscriptionTmp save(SubscriptionTmp subscriptionTmp) {
        log.debug("Request to save SubscriptionTmp : {}", subscriptionTmp);
        return subscriptionTmpRepository.save(subscriptionTmp);
    }

    /**
     * Partially update a subscriptionTmp.
     *
     * @param subscriptionTmp the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubscriptionTmp> partialUpdate(SubscriptionTmp subscriptionTmp) {
        log.debug("Request to partially update SubscriptionTmp : {}", subscriptionTmp);

        return subscriptionTmpRepository
            .findById(subscriptionTmp.getId())
            .map(existingSubscriptionTmp -> {
                if (subscriptionTmp.getName() != null) {
                    existingSubscriptionTmp.setName(subscriptionTmp.getName());
                }
                if (subscriptionTmp.getLastUpdate() != null) {
                    existingSubscriptionTmp.setLastUpdate(subscriptionTmp.getLastUpdate());
                }
                if (subscriptionTmp.getDescription() != null) {
                    existingSubscriptionTmp.setDescription(subscriptionTmp.getDescription());
                }
                if (subscriptionTmp.getCommercialName() != null) {
                    existingSubscriptionTmp.setCommercialName(subscriptionTmp.getCommercialName());
                }
                if (subscriptionTmp.getRatePlans() != null) {
                    existingSubscriptionTmp.setRatePlans(subscriptionTmp.getRatePlans());
                }
                if (subscriptionTmp.getSubscriptionFeatures() != null) {
                    existingSubscriptionTmp.setSubscriptionFeatures(subscriptionTmp.getSubscriptionFeatures());
                }
                if (subscriptionTmp.getVersion() != null) {
                    existingSubscriptionTmp.setVersion(subscriptionTmp.getVersion());
                }
                if (subscriptionTmp.getUsages() != null) {
                    existingSubscriptionTmp.setUsages(subscriptionTmp.getUsages());
                }
                if (subscriptionTmp.getFreeMonths() != null) {
                    existingSubscriptionTmp.setFreeMonths(subscriptionTmp.getFreeMonths());
                }
                if (subscriptionTmp.getInvoiceItemDateds() != null) {
                    existingSubscriptionTmp.setInvoiceItemDateds(subscriptionTmp.getInvoiceItemDateds());
                }

                return existingSubscriptionTmp;
            })
            .map(subscriptionTmpRepository::save);
    }

    /**
     * Get all the subscriptionTmps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SubscriptionTmp> findAll(Pageable pageable) {
        log.debug("Request to get all SubscriptionTmps");
        return subscriptionTmpRepository.findAll(pageable);
    }

    /**
     * Get one subscriptionTmp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SubscriptionTmp> findOne(String id) {
        log.debug("Request to get SubscriptionTmp : {}", id);
        return subscriptionTmpRepository.findById(id);
    }

    /**
     * Delete the subscriptionTmp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SubscriptionTmp : {}", id);
        subscriptionTmpRepository.deleteById(id);
    }
}
