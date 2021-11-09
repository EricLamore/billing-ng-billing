package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.ProductRatePlanCharge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ProductRatePlanCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRatePlanChargeRepository extends MongoRepository<ProductRatePlanCharge, String> {}
