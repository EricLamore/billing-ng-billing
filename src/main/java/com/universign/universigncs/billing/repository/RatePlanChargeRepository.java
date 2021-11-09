package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.RatePlanCharge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RatePlanCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatePlanChargeRepository extends MongoRepository<RatePlanCharge, String> {}
