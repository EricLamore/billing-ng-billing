package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.Organization;
import com.universign.universigncs.billing.repository.OrganizationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Organization}.
 */
@Service
public class OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Save a organization.
     *
     * @param organization the entity to save.
     * @return the persisted entity.
     */
    public Organization save(Organization organization) {
        log.debug("Request to save Organization : {}", organization);
        return organizationRepository.save(organization);
    }

    /**
     * Partially update a organization.
     *
     * @param organization the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Organization> partialUpdate(Organization organization) {
        log.debug("Request to partially update Organization : {}", organization);

        return organizationRepository
            .findById(organization.getId())
            .map(existingOrganization -> {
                if (organization.getAddr() != null) {
                    existingOrganization.setAddr(organization.getAddr());
                }
                if (organization.getCity() != null) {
                    existingOrganization.setCity(organization.getCity());
                }
                if (organization.getCountry() != null) {
                    existingOrganization.setCountry(organization.getCountry());
                }
                if (organization.getName() != null) {
                    existingOrganization.setName(organization.getName());
                }
                if (organization.getRegisterDate() != null) {
                    existingOrganization.setRegisterDate(organization.getRegisterDate());
                }
                if (organization.getStatus() != null) {
                    existingOrganization.setStatus(organization.getStatus());
                }
                if (organization.getZipCode() != null) {
                    existingOrganization.setZipCode(organization.getZipCode());
                }
                if (organization.getIndividual() != null) {
                    existingOrganization.setIndividual(organization.getIndividual());
                }
                if (organization.getVatNumber() != null) {
                    existingOrganization.setVatNumber(organization.getVatNumber());
                }
                if (organization.getIpRanges() != null) {
                    existingOrganization.setIpRanges(organization.getIpRanges());
                }

                return existingOrganization;
            })
            .map(organizationRepository::save);
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable);
    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Organization> findOne(String id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
    }
}
