package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.PersonReferer;
import com.universign.universigncs.billing.repository.PersonRefererRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PersonReferer}.
 */
@Service
public class PersonRefererService {

    private final Logger log = LoggerFactory.getLogger(PersonRefererService.class);

    private final PersonRefererRepository personRefererRepository;

    public PersonRefererService(PersonRefererRepository personRefererRepository) {
        this.personRefererRepository = personRefererRepository;
    }

    /**
     * Save a personReferer.
     *
     * @param personReferer the entity to save.
     * @return the persisted entity.
     */
    public PersonReferer save(PersonReferer personReferer) {
        log.debug("Request to save PersonReferer : {}", personReferer);
        return personRefererRepository.save(personReferer);
    }

    /**
     * Partially update a personReferer.
     *
     * @param personReferer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonReferer> partialUpdate(PersonReferer personReferer) {
        log.debug("Request to partially update PersonReferer : {}", personReferer);

        return personRefererRepository
            .findById(personReferer.getId())
            .map(existingPersonReferer -> {
                if (personReferer.getFirstname() != null) {
                    existingPersonReferer.setFirstname(personReferer.getFirstname());
                }
                if (personReferer.getLastname() != null) {
                    existingPersonReferer.setLastname(personReferer.getLastname());
                }
                if (personReferer.getEmail() != null) {
                    existingPersonReferer.setEmail(personReferer.getEmail());
                }
                if (personReferer.getJob() != null) {
                    existingPersonReferer.setJob(personReferer.getJob());
                }
                if (personReferer.getPhoneNumber() != null) {
                    existingPersonReferer.setPhoneNumber(personReferer.getPhoneNumber());
                }
                if (personReferer.getMobile() != null) {
                    existingPersonReferer.setMobile(personReferer.getMobile());
                }
                if (personReferer.getFax() != null) {
                    existingPersonReferer.setFax(personReferer.getFax());
                }
                if (personReferer.getDescription() != null) {
                    existingPersonReferer.setDescription(personReferer.getDescription());
                }

                return existingPersonReferer;
            })
            .map(personRefererRepository::save);
    }

    /**
     * Get all the personReferers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PersonReferer> findAll(Pageable pageable) {
        log.debug("Request to get all PersonReferers");
        return personRefererRepository.findAll(pageable);
    }

    /**
     * Get one personReferer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PersonReferer> findOne(String id) {
        log.debug("Request to get PersonReferer : {}", id);
        return personRefererRepository.findById(id);
    }

    /**
     * Delete the personReferer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PersonReferer : {}", id);
        personRefererRepository.deleteById(id);
    }
}
