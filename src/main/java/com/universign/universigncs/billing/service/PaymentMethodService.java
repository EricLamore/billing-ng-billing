package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.PaymentMethod;
import com.universign.universigncs.billing.repository.PaymentMethodRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PaymentMethod}.
 */
@Service
public class PaymentMethodService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodService.class);

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /**
     * Save a paymentMethod.
     *
     * @param paymentMethod the entity to save.
     * @return the persisted entity.
     */
    public PaymentMethod save(PaymentMethod paymentMethod) {
        log.debug("Request to save PaymentMethod : {}", paymentMethod);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Partially update a paymentMethod.
     *
     * @param paymentMethod the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentMethod> partialUpdate(PaymentMethod paymentMethod) {
        log.debug("Request to partially update PaymentMethod : {}", paymentMethod);

        return paymentMethodRepository
            .findById(paymentMethod.getId())
            .map(existingPaymentMethod -> {
                if (paymentMethod.getTypeOfMean() != null) {
                    existingPaymentMethod.setTypeOfMean(paymentMethod.getTypeOfMean());
                }
                if (paymentMethod.getAccountId() != null) {
                    existingPaymentMethod.setAccountId(paymentMethod.getAccountId());
                }
                if (paymentMethod.getIban() != null) {
                    existingPaymentMethod.setIban(paymentMethod.getIban());
                }

                return existingPaymentMethod;
            })
            .map(paymentMethodRepository::save);
    }

    /**
     * Get all the paymentMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PaymentMethod> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentMethods");
        return paymentMethodRepository.findAll(pageable);
    }

    /**
     * Get one paymentMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PaymentMethod> findOne(String id) {
        log.debug("Request to get PaymentMethod : {}", id);
        return paymentMethodRepository.findById(id);
    }

    /**
     * Delete the paymentMethod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PaymentMethod : {}", id);
        paymentMethodRepository.deleteById(id);
    }
}
