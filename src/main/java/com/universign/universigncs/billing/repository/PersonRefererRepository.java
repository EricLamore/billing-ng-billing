package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.PersonReferer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PersonReferer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRefererRepository extends MongoRepository<PersonReferer, String> {}
