package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.RatePlan;
import com.universign.universigncs.billing.repository.RatePlanRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link RatePlan}.
 */
@Service
public class RatePlanService {

    private final Logger log = LoggerFactory.getLogger(RatePlanService.class);

    private final RatePlanRepository ratePlanRepository;

    public RatePlanService(RatePlanRepository ratePlanRepository) {
        this.ratePlanRepository = ratePlanRepository;
    }

    /**
     * Save a ratePlan.
     *
     * @param ratePlan the entity to save.
     * @return the persisted entity.
     */
    public RatePlan save(RatePlan ratePlan) {
        log.debug("Request to save RatePlan : {}", ratePlan);
        return ratePlanRepository.save(ratePlan);
    }

    /**
     * Partially update a ratePlan.
     *
     * @param ratePlan the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatePlan> partialUpdate(RatePlan ratePlan) {
        log.debug("Request to partially update RatePlan : {}", ratePlan);

        return ratePlanRepository
            .findById(ratePlan.getId())
            .map(existingRatePlan -> {
                if (ratePlan.getName() != null) {
                    existingRatePlan.setName(ratePlan.getName());
                }
                if (ratePlan.getLastUpdate() != null) {
                    existingRatePlan.setLastUpdate(ratePlan.getLastUpdate());
                }
                if (ratePlan.getDescription() != null) {
                    existingRatePlan.setDescription(ratePlan.getDescription());
                }
                if (ratePlan.getCommercialName() != null) {
                    existingRatePlan.setCommercialName(ratePlan.getCommercialName());
                }
                if (ratePlan.getBase() != null) {
                    existingRatePlan.setBase(ratePlan.getBase());
                }
                if (ratePlan.getProduct() != null) {
                    existingRatePlan.setProduct(ratePlan.getProduct());
                }
                if (ratePlan.getProductRatePlanCharge() != null) {
                    existingRatePlan.setProductRatePlanCharge(ratePlan.getProductRatePlanCharge());
                }
                if (ratePlan.getFeatures() != null) {
                    existingRatePlan.setFeatures(ratePlan.getFeatures());
                }
                if (ratePlan.getVersion() != null) {
                    existingRatePlan.setVersion(ratePlan.getVersion());
                }
                if (ratePlan.getFixedPrice() != null) {
                    existingRatePlan.setFixedPrice(ratePlan.getFixedPrice());
                }
                if (ratePlan.getStandardModel() != null) {
                    existingRatePlan.setStandardModel(ratePlan.getStandardModel());
                }
                if (ratePlan.getUnitsIncluded() != null) {
                    existingRatePlan.setUnitsIncluded(ratePlan.getUnitsIncluded());
                }
                if (ratePlan.getUnitsIncludedPrice() != null) {
                    existingRatePlan.setUnitsIncludedPrice(ratePlan.getUnitsIncludedPrice());
                }
                if (ratePlan.getUnitsIncludedDuration() != null) {
                    existingRatePlan.setUnitsIncludedDuration(ratePlan.getUnitsIncludedDuration());
                }
                if (ratePlan.getRatePlanId() != null) {
                    existingRatePlan.setRatePlanId(ratePlan.getRatePlanId());
                }
                if (ratePlan.getDiscountUnitPrice() != null) {
                    existingRatePlan.setDiscountUnitPrice(ratePlan.getDiscountUnitPrice());
                }
                if (ratePlan.getDiscountBase() != null) {
                    existingRatePlan.setDiscountBase(ratePlan.getDiscountBase());
                }
                if (ratePlan.getProrata() != null) {
                    existingRatePlan.setProrata(ratePlan.getProrata());
                }
                if (ratePlan.getActivationDate() != null) {
                    existingRatePlan.setActivationDate(ratePlan.getActivationDate());
                }
                if (ratePlan.getEndDate() != null) {
                    existingRatePlan.setEndDate(ratePlan.getEndDate());
                }
                if (ratePlan.getRatePlanCharges() != null) {
                    existingRatePlan.setRatePlanCharges(ratePlan.getRatePlanCharges());
                }
                if (ratePlan.getSubscriptionFeatures() != null) {
                    existingRatePlan.setSubscriptionFeatures(ratePlan.getSubscriptionFeatures());
                }

                return existingRatePlan;
            })
            .map(ratePlanRepository::save);
    }

    /**
     * Get all the ratePlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RatePlan> findAll(Pageable pageable) {
        log.debug("Request to get all RatePlans");
        return ratePlanRepository.findAll(pageable);
    }

    /**
     * Get one ratePlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RatePlan> findOne(String id) {
        log.debug("Request to get RatePlan : {}", id);
        return ratePlanRepository.findById(id);
    }

    /**
     * Delete the ratePlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RatePlan : {}", id);
        ratePlanRepository.deleteById(id);
    }
}
