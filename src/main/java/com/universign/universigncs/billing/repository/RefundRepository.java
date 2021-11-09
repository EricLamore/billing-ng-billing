package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.Refund;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Refund entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefundRepository extends MongoRepository<Refund, String> {}
