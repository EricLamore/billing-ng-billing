package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.ProductRatePlanCharge;
import com.universign.universigncs.billing.repository.ProductRatePlanChargeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ProductRatePlanCharge}.
 */
@Service
public class ProductRatePlanChargeService {

    private final Logger log = LoggerFactory.getLogger(ProductRatePlanChargeService.class);

    private final ProductRatePlanChargeRepository productRatePlanChargeRepository;

    public ProductRatePlanChargeService(ProductRatePlanChargeRepository productRatePlanChargeRepository) {
        this.productRatePlanChargeRepository = productRatePlanChargeRepository;
    }

    /**
     * Save a productRatePlanCharge.
     *
     * @param productRatePlanCharge the entity to save.
     * @return the persisted entity.
     */
    public ProductRatePlanCharge save(ProductRatePlanCharge productRatePlanCharge) {
        log.debug("Request to save ProductRatePlanCharge : {}", productRatePlanCharge);
        return productRatePlanChargeRepository.save(productRatePlanCharge);
    }

    /**
     * Partially update a productRatePlanCharge.
     *
     * @param productRatePlanCharge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductRatePlanCharge> partialUpdate(ProductRatePlanCharge productRatePlanCharge) {
        log.debug("Request to partially update ProductRatePlanCharge : {}", productRatePlanCharge);

        return productRatePlanChargeRepository
            .findById(productRatePlanCharge.getId())
            .map(existingProductRatePlanCharge -> {
                if (productRatePlanCharge.getStep() != null) {
                    existingProductRatePlanCharge.setStep(productRatePlanCharge.getStep());
                }
                if (productRatePlanCharge.getUnitPrice() != null) {
                    existingProductRatePlanCharge.setUnitPrice(productRatePlanCharge.getUnitPrice());
                }

                return existingProductRatePlanCharge;
            })
            .map(productRatePlanChargeRepository::save);
    }

    /**
     * Get all the productRatePlanCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProductRatePlanCharge> findAll(Pageable pageable) {
        log.debug("Request to get all ProductRatePlanCharges");
        return productRatePlanChargeRepository.findAll(pageable);
    }

    /**
     * Get one productRatePlanCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProductRatePlanCharge> findOne(String id) {
        log.debug("Request to get ProductRatePlanCharge : {}", id);
        return productRatePlanChargeRepository.findById(id);
    }

    /**
     * Delete the productRatePlanCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProductRatePlanCharge : {}", id);
        productRatePlanChargeRepository.deleteById(id);
    }
}
