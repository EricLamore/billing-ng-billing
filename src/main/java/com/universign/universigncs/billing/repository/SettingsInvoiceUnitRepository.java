package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.SettingsInvoiceUnit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SettingsInvoiceUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingsInvoiceUnitRepository extends MongoRepository<SettingsInvoiceUnit, String> {}
