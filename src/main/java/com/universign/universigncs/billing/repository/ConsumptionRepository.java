package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.Consumption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Consumption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionRepository extends MongoRepository<Consumption, String> {}
