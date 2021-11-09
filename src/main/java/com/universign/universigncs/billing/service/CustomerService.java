package com.universign.universigncs.billing.service;

import com.universign.universigncs.billing.domain.Customer;
import com.universign.universigncs.billing.repository.CustomerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Partially update a customer.
     *
     * @param customer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getName() != null) {
                    existingCustomer.setName(customer.getName());
                }
                if (customer.getLastUpdate() != null) {
                    existingCustomer.setLastUpdate(customer.getLastUpdate());
                }
                if (customer.getDescription() != null) {
                    existingCustomer.setDescription(customer.getDescription());
                }
                if (customer.getTaxNo() != null) {
                    existingCustomer.setTaxNo(customer.getTaxNo());
                }
                if (customer.getThirdPartyAccountingCode() != null) {
                    existingCustomer.setThirdPartyAccountingCode(customer.getThirdPartyAccountingCode());
                }
                if (customer.getSiret() != null) {
                    existingCustomer.setSiret(customer.getSiret());
                }
                if (customer.getOwnerId() != null) {
                    existingCustomer.setOwnerId(customer.getOwnerId());
                }
                if (customer.getIsParticular() != null) {
                    existingCustomer.setIsParticular(customer.getIsParticular());
                }
                if (customer.getPersonReferers() != null) {
                    existingCustomer.setPersonReferers(customer.getPersonReferers());
                }
                if (customer.getMeansPayments() != null) {
                    existingCustomer.setMeansPayments(customer.getMeansPayments());
                }
                if (customer.getSubscriptions() != null) {
                    existingCustomer.setSubscriptions(customer.getSubscriptions());
                }
                if (customer.getUsages() != null) {
                    existingCustomer.setUsages(customer.getUsages());
                }
                if (customer.getSettingsInvoice() != null) {
                    existingCustomer.setSettingsInvoice(customer.getSettingsInvoice());
                }
                if (customer.getPartner() != null) {
                    existingCustomer.setPartner(customer.getPartner());
                }
                if (customer.getPartnerId() != null) {
                    existingCustomer.setPartnerId(customer.getPartnerId());
                }
                if (customer.getCustomerId() != null) {
                    existingCustomer.setCustomerId(customer.getCustomerId());
                }
                if (customer.getCustomerName() != null) {
                    existingCustomer.setCustomerName(customer.getCustomerName());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Customer> findOne(String id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
