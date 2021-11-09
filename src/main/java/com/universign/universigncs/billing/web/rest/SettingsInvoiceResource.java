package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.SettingsInvoice;
import com.universign.universigncs.billing.repository.SettingsInvoiceRepository;
import com.universign.universigncs.billing.service.SettingsInvoiceService;
import com.universign.universigncs.billing.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.universign.universigncs.billing.domain.SettingsInvoice}.
 */
@RestController
@RequestMapping("/api")
public class SettingsInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(SettingsInvoiceResource.class);

    private static final String ENTITY_NAME = "settingsInvoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SettingsInvoiceService settingsInvoiceService;

    private final SettingsInvoiceRepository settingsInvoiceRepository;

    public SettingsInvoiceResource(SettingsInvoiceService settingsInvoiceService, SettingsInvoiceRepository settingsInvoiceRepository) {
        this.settingsInvoiceService = settingsInvoiceService;
        this.settingsInvoiceRepository = settingsInvoiceRepository;
    }

    /**
     * {@code POST  /settings-invoices} : Create a new settingsInvoice.
     *
     * @param settingsInvoice the settingsInvoice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new settingsInvoice, or with status {@code 400 (Bad Request)} if the settingsInvoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/settings-invoices")
    public ResponseEntity<SettingsInvoice> createSettingsInvoice(@RequestBody SettingsInvoice settingsInvoice) throws URISyntaxException {
        log.debug("REST request to save SettingsInvoice : {}", settingsInvoice);
        if (settingsInvoice.getId() != null) {
            throw new BadRequestAlertException("A new settingsInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SettingsInvoice result = settingsInvoiceService.save(settingsInvoice);
        return ResponseEntity
            .created(new URI("/api/settings-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /settings-invoices/:id} : Updates an existing settingsInvoice.
     *
     * @param id the id of the settingsInvoice to save.
     * @param settingsInvoice the settingsInvoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingsInvoice,
     * or with status {@code 400 (Bad Request)} if the settingsInvoice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the settingsInvoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/settings-invoices/{id}")
    public ResponseEntity<SettingsInvoice> updateSettingsInvoice(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SettingsInvoice settingsInvoice
    ) throws URISyntaxException {
        log.debug("REST request to update SettingsInvoice : {}, {}", id, settingsInvoice);
        if (settingsInvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, settingsInvoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!settingsInvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SettingsInvoice result = settingsInvoiceService.save(settingsInvoice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingsInvoice.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /settings-invoices/:id} : Partial updates given fields of an existing settingsInvoice, field will ignore if it is null
     *
     * @param id the id of the settingsInvoice to save.
     * @param settingsInvoice the settingsInvoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingsInvoice,
     * or with status {@code 400 (Bad Request)} if the settingsInvoice is not valid,
     * or with status {@code 404 (Not Found)} if the settingsInvoice is not found,
     * or with status {@code 500 (Internal Server Error)} if the settingsInvoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/settings-invoices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SettingsInvoice> partialUpdateSettingsInvoice(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SettingsInvoice settingsInvoice
    ) throws URISyntaxException {
        log.debug("REST request to partial update SettingsInvoice partially : {}, {}", id, settingsInvoice);
        if (settingsInvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, settingsInvoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!settingsInvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SettingsInvoice> result = settingsInvoiceService.partialUpdate(settingsInvoice);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingsInvoice.getId())
        );
    }

    /**
     * {@code GET  /settings-invoices} : get all the settingsInvoices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of settingsInvoices in body.
     */
    @GetMapping("/settings-invoices")
    public ResponseEntity<List<SettingsInvoice>> getAllSettingsInvoices(Pageable pageable) {
        log.debug("REST request to get a page of SettingsInvoices");
        Page<SettingsInvoice> page = settingsInvoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /settings-invoices/:id} : get the "id" settingsInvoice.
     *
     * @param id the id of the settingsInvoice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the settingsInvoice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/settings-invoices/{id}")
    public ResponseEntity<SettingsInvoice> getSettingsInvoice(@PathVariable String id) {
        log.debug("REST request to get SettingsInvoice : {}", id);
        Optional<SettingsInvoice> settingsInvoice = settingsInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingsInvoice);
    }

    /**
     * {@code DELETE  /settings-invoices/:id} : delete the "id" settingsInvoice.
     *
     * @param id the id of the settingsInvoice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/settings-invoices/{id}")
    public ResponseEntity<Void> deleteSettingsInvoice(@PathVariable String id) {
        log.debug("REST request to delete SettingsInvoice : {}", id);
        settingsInvoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
