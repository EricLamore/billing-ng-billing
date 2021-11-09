package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.SettingsInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SettingsInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingsInvoiceRepository extends MongoRepository<SettingsInvoice, String> {}
