package com.universign.universigncs.billing.repository;

import com.universign.universigncs.billing.domain.InvoiceItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the InvoiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceItemRepository extends MongoRepository<InvoiceItem, String> {}
