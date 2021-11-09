package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.SubscriptionFeature;
import com.universign.universigncs.billing.repository.SubscriptionFeatureRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SubscriptionFeature}.
 */
@Service
public class SubscriptionFeatureService {

    private final Logger log = LoggerFactory.getLogger(SubscriptionFeatureService.class);

    private final SubscriptionFeatureRepository subscriptionFeatureRepository;

    public SubscriptionFeatureService(SubscriptionFeatureRepository subscriptionFeatureRepository) {
        this.subscriptionFeatureRepository = subscriptionFeatureRepository;
    }

    /**
     * Save a subscriptionFeature.
     *
     * @param subscriptionFeature the entity to save.
     * @return the persisted entity.
     */
    public SubscriptionFeature save(SubscriptionFeature subscriptionFeature) {
        log.debug("Request to save SubscriptionFeature : {}", subscriptionFeature);
        return subscriptionFeatureRepository.save(subscriptionFeature);
    }

    /**
     * Partially update a subscriptionFeature.
     *
     * @param subscriptionFeature the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubscriptionFeature> partialUpdate(SubscriptionFeature subscriptionFeature) {
        log.debug("Request to partially update SubscriptionFeature : {}", subscriptionFeature);

        return subscriptionFeatureRepository
            .findById(subscriptionFeature.getId())
            .map(existingSubscriptionFeature -> {
                if (subscriptionFeature.getName() != null) {
                    existingSubscriptionFeature.setName(subscriptionFeature.getName());
                }
                if (subscriptionFeature.getLastUpdate() != null) {
                    existingSubscriptionFeature.setLastUpdate(subscriptionFeature.getLastUpdate());
                }
                if (subscriptionFeature.getDescription() != null) {
                    existingSubscriptionFeature.setDescription(subscriptionFeature.getDescription());
                }
                if (subscriptionFeature.getIsVisible() != null) {
                    existingSubscriptionFeature.setIsVisible(subscriptionFeature.getIsVisible());
                }

                return existingSubscriptionFeature;
            })
            .map(subscriptionFeatureRepository::save);
    }

    /**
     * Get all the subscriptionFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SubscriptionFeature> findAll(Pageable pageable) {
        log.debug("Request to get all SubscriptionFeatures");
        return subscriptionFeatureRepository.findAll(pageable);
    }

    /**
     * Get one subscriptionFeature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SubscriptionFeature> findOne(String id) {
        log.debug("Request to get SubscriptionFeature : {}", id);
        return subscriptionFeatureRepository.findById(id);
    }

    /**
     * Delete the subscriptionFeature by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SubscriptionFeature : {}", id);
        subscriptionFeatureRepository.deleteById(id);
    }
}
