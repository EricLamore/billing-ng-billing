package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.Feature;
import com.universign.universigncs.billing.repository.FeatureRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Feature}.
 */
@Service
public class FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureService.class);

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    /**
     * Save a feature.
     *
     * @param feature the entity to save.
     * @return the persisted entity.
     */
    public Feature save(Feature feature) {
        log.debug("Request to save Feature : {}", feature);
        return featureRepository.save(feature);
    }

    /**
     * Partially update a feature.
     *
     * @param feature the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Feature> partialUpdate(Feature feature) {
        log.debug("Request to partially update Feature : {}", feature);

        return featureRepository
            .findById(feature.getId())
            .map(existingFeature -> {
                if (feature.getName() != null) {
                    existingFeature.setName(feature.getName());
                }
                if (feature.getLastUpdate() != null) {
                    existingFeature.setLastUpdate(feature.getLastUpdate());
                }
                if (feature.getDescription() != null) {
                    existingFeature.setDescription(feature.getDescription());
                }
                if (feature.getIsVisible() != null) {
                    existingFeature.setIsVisible(feature.getIsVisible());
                }

                return existingFeature;
            })
            .map(featureRepository::save);
    }

    /**
     * Get all the features.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Feature> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featureRepository.findAll(pageable);
    }

    /**
     * Get one feature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Feature> findOne(String id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id);
    }

    /**
     * Delete the feature by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.deleteById(id);
    }
}
