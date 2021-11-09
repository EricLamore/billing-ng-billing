package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.Refund;
import com.universign.universigncs.billing.repository.RefundRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Refund}.
 */
@Service
public class RefundService {

    private final Logger log = LoggerFactory.getLogger(RefundService.class);

    private final RefundRepository refundRepository;

    public RefundService(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    /**
     * Save a refund.
     *
     * @param refund the entity to save.
     * @return the persisted entity.
     */
    public Refund save(Refund refund) {
        log.debug("Request to save Refund : {}", refund);
        return refundRepository.save(refund);
    }

    /**
     * Partially update a refund.
     *
     * @param refund the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Refund> partialUpdate(Refund refund) {
        log.debug("Request to partially update Refund : {}", refund);

        return refundRepository
            .findById(refund.getId())
            .map(existingRefund -> {
                if (refund.getReference() != null) {
                    existingRefund.setReference(refund.getReference());
                }
                if (refund.getAmount() != null) {
                    existingRefund.setAmount(refund.getAmount());
                }

                return existingRefund;
            })
            .map(refundRepository::save);
    }

    /**
     * Get all the refunds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Refund> findAll(Pageable pageable) {
        log.debug("Request to get all Refunds");
        return refundRepository.findAll(pageable);
    }

    /**
     * Get one refund by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Refund> findOne(String id) {
        log.debug("Request to get Refund : {}", id);
        return refundRepository.findById(id);
    }

    /**
     * Delete the refund by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Refund : {}", id);
        refundRepository.deleteById(id);
    }
}
