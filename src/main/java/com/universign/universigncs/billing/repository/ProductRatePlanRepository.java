package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.ProductRatePlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ProductRatePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRatePlanRepository extends MongoRepository<ProductRatePlan, String> {}
