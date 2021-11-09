package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.SubscriptionFeature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SubscriptionFeature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionFeatureRepository extends MongoRepository<SubscriptionFeature, String> {}
