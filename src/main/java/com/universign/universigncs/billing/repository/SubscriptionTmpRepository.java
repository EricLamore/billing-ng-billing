package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.SubscriptionTmp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SubscriptionTmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionTmpRepository extends MongoRepository<SubscriptionTmp, String> {}
