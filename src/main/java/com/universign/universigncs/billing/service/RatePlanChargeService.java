package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.RatePlanCharge;
import com.universign.universigncs.billing.repository.RatePlanChargeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link RatePlanCharge}.
 */
@Service
public class RatePlanChargeService {

    private final Logger log = LoggerFactory.getLogger(RatePlanChargeService.class);

    private final RatePlanChargeRepository ratePlanChargeRepository;

    public RatePlanChargeService(RatePlanChargeRepository ratePlanChargeRepository) {
        this.ratePlanChargeRepository = ratePlanChargeRepository;
    }

    /**
     * Save a ratePlanCharge.
     *
     * @param ratePlanCharge the entity to save.
     * @return the persisted entity.
     */
    public RatePlanCharge save(RatePlanCharge ratePlanCharge) {
        log.debug("Request to save RatePlanCharge : {}", ratePlanCharge);
        return ratePlanChargeRepository.save(ratePlanCharge);
    }

    /**
     * Partially update a ratePlanCharge.
     *
     * @param ratePlanCharge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatePlanCharge> partialUpdate(RatePlanCharge ratePlanCharge) {
        log.debug("Request to partially update RatePlanCharge : {}", ratePlanCharge);

        return ratePlanChargeRepository
            .findById(ratePlanCharge.getId())
            .map(existingRatePlanCharge -> {
                if (ratePlanCharge.getStep() != null) {
                    existingRatePlanCharge.setStep(ratePlanCharge.getStep());
                }
                if (ratePlanCharge.getUnitPrice() != null) {
                    existingRatePlanCharge.setUnitPrice(ratePlanCharge.getUnitPrice());
                }
                if (ratePlanCharge.getDiscount() != null) {
                    existingRatePlanCharge.setDiscount(ratePlanCharge.getDiscount());
                }

                return existingRatePlanCharge;
            })
            .map(ratePlanChargeRepository::save);
    }

    /**
     * Get all the ratePlanCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RatePlanCharge> findAll(Pageable pageable) {
        log.debug("Request to get all RatePlanCharges");
        return ratePlanChargeRepository.findAll(pageable);
    }

    /**
     * Get one ratePlanCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RatePlanCharge> findOne(String id) {
        log.debug("Request to get RatePlanCharge : {}", id);
        return ratePlanChargeRepository.findById(id);
    }

    /**
     * Delete the ratePlanCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RatePlanCharge : {}", id);
        ratePlanChargeRepository.deleteById(id);
    }
}
