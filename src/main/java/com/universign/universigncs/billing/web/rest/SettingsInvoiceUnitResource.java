package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.SettingsInvoiceUnit;
import com.universign.universigncs.billing.repository.SettingsInvoiceUnitRepository;
import com.universign.universigncs.billing.service.SettingsInvoiceUnitService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.SettingsInvoiceUnit}.
 */
@RestController
@RequestMapping("/api")
public class SettingsInvoiceUnitResource {

    private final Logger log = LoggerFactory.getLogger(SettingsInvoiceUnitResource.class);

    private static final String ENTITY_NAME = "settingsInvoiceUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SettingsInvoiceUnitService settingsInvoiceUnitService;

    private final SettingsInvoiceUnitRepository settingsInvoiceUnitRepository;

    public SettingsInvoiceUnitResource(
        SettingsInvoiceUnitService settingsInvoiceUnitService,
        SettingsInvoiceUnitRepository settingsInvoiceUnitRepository
    ) {
        this.settingsInvoiceUnitService = settingsInvoiceUnitService;
        this.settingsInvoiceUnitRepository = settingsInvoiceUnitRepository;
    }

    /**
     * {@code POST  /settings-invoice-units} : Create a new settingsInvoiceUnit.
     *
     * @param settingsInvoiceUnit the settingsInvoiceUnit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new settingsInvoiceUnit, or with status {@code 400 (Bad Request)} if the settingsInvoiceUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/settings-invoice-units")
    public ResponseEntity<SettingsInvoiceUnit> createSettingsInvoiceUnit(@RequestBody SettingsInvoiceUnit settingsInvoiceUnit)
        throws URISyntaxException {
        log.debug("REST request to save SettingsInvoiceUnit : {}", settingsInvoiceUnit);
        if (settingsInvoiceUnit.getId() != null) {
            throw new BadRequestAlertException("A new settingsInvoiceUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SettingsInvoiceUnit result = settingsInvoiceUnitService.save(settingsInvoiceUnit);
        return ResponseEntity
            .created(new URI("/api/settings-invoice-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /settings-invoice-units/:id} : Updates an existing settingsInvoiceUnit.
     *
     * @param id the id of the settingsInvoiceUnit to save.
     * @param settingsInvoiceUnit the settingsInvoiceUnit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingsInvoiceUnit,
     * or with status {@code 400 (Bad Request)} if the settingsInvoiceUnit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the settingsInvoiceUnit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/settings-invoice-units/{id}")
    public ResponseEntity<SettingsInvoiceUnit> updateSettingsInvoiceUnit(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SettingsInvoiceUnit settingsInvoiceUnit
    ) throws URISyntaxException {
        log.debug("REST request to update SettingsInvoiceUnit : {}, {}", id, settingsInvoiceUnit);
        if (settingsInvoiceUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, settingsInvoiceUnit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!settingsInvoiceUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SettingsInvoiceUnit result = settingsInvoiceUnitService.save(settingsInvoiceUnit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingsInvoiceUnit.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /settings-invoice-units/:id} : Partial updates given fields of an existing settingsInvoiceUnit, field will ignore if it is null
     *
     * @param id the id of the settingsInvoiceUnit to save.
     * @param settingsInvoiceUnit the settingsInvoiceUnit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingsInvoiceUnit,
     * or with status {@code 400 (Bad Request)} if the settingsInvoiceUnit is not valid,
     * or with status {@code 404 (Not Found)} if the settingsInvoiceUnit is not found,
     * or with status {@code 500 (Internal Server Error)} if the settingsInvoiceUnit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/settings-invoice-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SettingsInvoiceUnit> partialUpdateSettingsInvoiceUnit(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SettingsInvoiceUnit settingsInvoiceUnit
    ) throws URISyntaxException {
        log.debug("REST request to partial update SettingsInvoiceUnit partially : {}, {}", id, settingsInvoiceUnit);
        if (settingsInvoiceUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, settingsInvoiceUnit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!settingsInvoiceUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SettingsInvoiceUnit> result = settingsInvoiceUnitService.partialUpdate(settingsInvoiceUnit);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingsInvoiceUnit.getId())
        );
    }

    /**
     * {@code GET  /settings-invoice-units} : get all the settingsInvoiceUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of settingsInvoiceUnits in body.
     */
    @GetMapping("/settings-invoice-units")
    public ResponseEntity<List<SettingsInvoiceUnit>> getAllSettingsInvoiceUnits(Pageable pageable) {
        log.debug("REST request to get a page of SettingsInvoiceUnits");
        Page<SettingsInvoiceUnit> page = settingsInvoiceUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /settings-invoice-units/:id} : get the "id" settingsInvoiceUnit.
     *
     * @param id the id of the settingsInvoiceUnit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the settingsInvoiceUnit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/settings-invoice-units/{id}")
    public ResponseEntity<SettingsInvoiceUnit> getSettingsInvoiceUnit(@PathVariable String id) {
        log.debug("REST request to get SettingsInvoiceUnit : {}", id);
        Optional<SettingsInvoiceUnit> settingsInvoiceUnit = settingsInvoiceUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingsInvoiceUnit);
    }

    /**
     * {@code DELETE  /settings-invoice-units/:id} : delete the "id" settingsInvoiceUnit.
     *
     * @param id the id of the settingsInvoiceUnit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/settings-invoice-units/{id}")
    public ResponseEntity<Void> deleteSettingsInvoiceUnit(@PathVariable String id) {
        log.debug("REST request to delete SettingsInvoiceUnit : {}", id);
        settingsInvoiceUnitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
