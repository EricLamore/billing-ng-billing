package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.ProductSetting;
import com.universign.universigncs.billing.repository.ProductSettingRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ProductSetting}.
 */
@Service
public class ProductSettingService {

    private final Logger log = LoggerFactory.getLogger(ProductSettingService.class);

    private final ProductSettingRepository productSettingRepository;

    public ProductSettingService(ProductSettingRepository productSettingRepository) {
        this.productSettingRepository = productSettingRepository;
    }

    /**
     * Save a productSetting.
     *
     * @param productSetting the entity to save.
     * @return the persisted entity.
     */
    public ProductSetting save(ProductSetting productSetting) {
        log.debug("Request to save ProductSetting : {}", productSetting);
        return productSettingRepository.save(productSetting);
    }

    /**
     * Partially update a productSetting.
     *
     * @param productSetting the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductSetting> partialUpdate(ProductSetting productSetting) {
        log.debug("Request to partially update ProductSetting : {}", productSetting);

        return productSettingRepository
            .findById(productSetting.getId())
            .map(existingProductSetting -> {
                if (productSetting.getName() != null) {
                    existingProductSetting.setName(productSetting.getName());
                }
                if (productSetting.getValue() != null) {
                    existingProductSetting.setValue(productSetting.getValue());
                }

                return existingProductSetting;
            })
            .map(productSettingRepository::save);
    }

    /**
     * Get all the productSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProductSetting> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSettings");
        return productSettingRepository.findAll(pageable);
    }

    /**
     * Get one productSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProductSetting> findOne(String id) {
        log.debug("Request to get ProductSetting : {}", id);
        return productSettingRepository.findById(id);
    }

    /**
     * Delete the productSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProductSetting : {}", id);
        productSettingRepository.deleteById(id);
    }
}
