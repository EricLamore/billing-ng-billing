package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.ProductRatePlan;
import com.universign.universigncs.billing.repository.ProductRatePlanRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ProductRatePlan}.
 */
@Service
public class ProductRatePlanService {

    private final Logger log = LoggerFactory.getLogger(ProductRatePlanService.class);

    private final ProductRatePlanRepository productRatePlanRepository;

    public ProductRatePlanService(ProductRatePlanRepository productRatePlanRepository) {
        this.productRatePlanRepository = productRatePlanRepository;
    }

    /**
     * Save a productRatePlan.
     *
     * @param productRatePlan the entity to save.
     * @return the persisted entity.
     */
    public ProductRatePlan save(ProductRatePlan productRatePlan) {
        log.debug("Request to save ProductRatePlan : {}", productRatePlan);
        return productRatePlanRepository.save(productRatePlan);
    }

    /**
     * Partially update a productRatePlan.
     *
     * @param productRatePlan the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductRatePlan> partialUpdate(ProductRatePlan productRatePlan) {
        log.debug("Request to partially update ProductRatePlan : {}", productRatePlan);

        return productRatePlanRepository
            .findById(productRatePlan.getId())
            .map(existingProductRatePlan -> {
                if (productRatePlan.getCommercialName() != null) {
                    existingProductRatePlan.setCommercialName(productRatePlan.getCommercialName());
                }
                if (productRatePlan.getBase() != null) {
                    existingProductRatePlan.setBase(productRatePlan.getBase());
                }
                if (productRatePlan.getProduct() != null) {
                    existingProductRatePlan.setProduct(productRatePlan.getProduct());
                }
                if (productRatePlan.getProductRatePlanCharge() != null) {
                    existingProductRatePlan.setProductRatePlanCharge(productRatePlan.getProductRatePlanCharge());
                }
                if (productRatePlan.getFeatures() != null) {
                    existingProductRatePlan.setFeatures(productRatePlan.getFeatures());
                }
                if (productRatePlan.getVersion() != null) {
                    existingProductRatePlan.setVersion(productRatePlan.getVersion());
                }
                if (productRatePlan.getFixedPrice() != null) {
                    existingProductRatePlan.setFixedPrice(productRatePlan.getFixedPrice());
                }
                if (productRatePlan.getStandardModel() != null) {
                    existingProductRatePlan.setStandardModel(productRatePlan.getStandardModel());
                }
                if (productRatePlan.getUnitsIncluded() != null) {
                    existingProductRatePlan.setUnitsIncluded(productRatePlan.getUnitsIncluded());
                }
                if (productRatePlan.getUnitsIncludedPrice() != null) {
                    existingProductRatePlan.setUnitsIncludedPrice(productRatePlan.getUnitsIncludedPrice());
                }
                if (productRatePlan.getUnitsIncludedDuration() != null) {
                    existingProductRatePlan.setUnitsIncludedDuration(productRatePlan.getUnitsIncludedDuration());
                }

                return existingProductRatePlan;
            })
            .map(productRatePlanRepository::save);
    }

    /**
     * Get all the productRatePlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProductRatePlan> findAll(Pageable pageable) {
        log.debug("Request to get all ProductRatePlans");
        return productRatePlanRepository.findAll(pageable);
    }

    /**
     * Get one productRatePlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProductRatePlan> findOne(String id) {
        log.debug("Request to get ProductRatePlan : {}", id);
        return productRatePlanRepository.findById(id);
    }

    /**
     * Delete the productRatePlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProductRatePlan : {}", id);
        productRatePlanRepository.deleteById(id);
    }
}
