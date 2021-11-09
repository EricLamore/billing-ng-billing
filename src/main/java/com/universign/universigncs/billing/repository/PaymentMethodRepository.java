package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PaymentMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, String> {}
