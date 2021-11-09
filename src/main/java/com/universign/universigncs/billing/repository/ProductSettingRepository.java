package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.ProductSetting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ProductSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSettingRepository extends MongoRepository<ProductSetting, String> {}
