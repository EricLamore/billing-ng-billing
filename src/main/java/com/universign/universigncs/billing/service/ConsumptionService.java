package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.Consumption;
import com.universign.universigncs.billing.repository.ConsumptionRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Consumption}.
 */
@Service
public class ConsumptionService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionService.class);

    private final ConsumptionRepository consumptionRepository;

    public ConsumptionService(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    /**
     * Save a consumption.
     *
     * @param consumption the entity to save.
     * @return the persisted entity.
     */
    public Consumption save(Consumption consumption) {
        log.debug("Request to save Consumption : {}", consumption);
        return consumptionRepository.save(consumption);
    }

    /**
     * Partially update a consumption.
     *
     * @param consumption the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Consumption> partialUpdate(Consumption consumption) {
        log.debug("Request to partially update Consumption : {}", consumption);

        return consumptionRepository
            .findById(consumption.getId())
            .map(existingConsumption -> {
                if (consumption.getOrganisationId() != null) {
                    existingConsumption.setOrganisationId(consumption.getOrganisationId());
                }
                if (consumption.getOrganizationName() != null) {
                    existingConsumption.setOrganizationName(consumption.getOrganizationName());
                }
                if (consumption.getRatePlanId() != null) {
                    existingConsumption.setRatePlanId(consumption.getRatePlanId());
                }
                if (consumption.getName() != null) {
                    existingConsumption.setName(consumption.getName());
                }
                if (consumption.getType() != null) {
                    existingConsumption.setType(consumption.getType());
                }
                if (consumption.getMonth() != null) {
                    existingConsumption.setMonth(consumption.getMonth());
                }
                if (consumption.getYear() != null) {
                    existingConsumption.setYear(consumption.getYear());
                }
                if (consumption.getDetails() != null) {
                    existingConsumption.setDetails(consumption.getDetails());
                }
                if (consumption.getNbUnits() != null) {
                    existingConsumption.setNbUnits(consumption.getNbUnits());
                }

                return existingConsumption;
            })
            .map(consumptionRepository::save);
    }

    /**
     * Get all the consumptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Consumption> findAll(Pageable pageable) {
        log.debug("Request to get all Consumptions");
        return consumptionRepository.findAll(pageable);
    }

    /**
     * Get one consumption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Consumption> findOne(String id) {
        log.debug("Request to get Consumption : {}", id);
        return consumptionRepository.findById(id);
    }

    /**
     * Delete the consumption by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Consumption : {}", id);
        consumptionRepository.deleteById(id);
    }
}
