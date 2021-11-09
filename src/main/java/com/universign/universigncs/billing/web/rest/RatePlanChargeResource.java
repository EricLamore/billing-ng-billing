package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.RatePlanCharge;
import com.universign.universigncs.billing.repository.RatePlanChargeRepository;
import com.universign.universigncs.billing.service.RatePlanChargeService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.RatePlanCharge}.
 */
@RestController
@RequestMapping("/api")
public class RatePlanChargeResource {

    private final Logger log = LoggerFactory.getLogger(RatePlanChargeResource.class);

    private static final String ENTITY_NAME = "ratePlanCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatePlanChargeService ratePlanChargeService;

    private final RatePlanChargeRepository ratePlanChargeRepository;

    public RatePlanChargeResource(RatePlanChargeService ratePlanChargeService, RatePlanChargeRepository ratePlanChargeRepository) {
        this.ratePlanChargeService = ratePlanChargeService;
        this.ratePlanChargeRepository = ratePlanChargeRepository;
    }

    /**
     * {@code POST  /rate-plan-charges} : Create a new ratePlanCharge.
     *
     * @param ratePlanCharge the ratePlanCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratePlanCharge, or with status {@code 400 (Bad Request)} if the ratePlanCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rate-plan-charges")
    public ResponseEntity<RatePlanCharge> createRatePlanCharge(@RequestBody RatePlanCharge ratePlanCharge) throws URISyntaxException {
        log.debug("REST request to save RatePlanCharge : {}", ratePlanCharge);
        if (ratePlanCharge.getId() != null) {
            throw new BadRequestAlertException("A new ratePlanCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatePlanCharge result = ratePlanChargeService.save(ratePlanCharge);
        return ResponseEntity
            .created(new URI("/api/rate-plan-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /rate-plan-charges/:id} : Updates an existing ratePlanCharge.
     *
     * @param id the id of the ratePlanCharge to save.
     * @param ratePlanCharge the ratePlanCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratePlanCharge,
     * or with status {@code 400 (Bad Request)} if the ratePlanCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratePlanCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rate-plan-charges/{id}")
    public ResponseEntity<RatePlanCharge> updateRatePlanCharge(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RatePlanCharge ratePlanCharge
    ) throws URISyntaxException {
        log.debug("REST request to update RatePlanCharge : {}, {}", id, ratePlanCharge);
        if (ratePlanCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratePlanCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratePlanChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatePlanCharge result = ratePlanChargeService.save(ratePlanCharge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratePlanCharge.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /rate-plan-charges/:id} : Partial updates given fields of an existing ratePlanCharge, field will ignore if it is null
     *
     * @param id the id of the ratePlanCharge to save.
     * @param ratePlanCharge the ratePlanCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratePlanCharge,
     * or with status {@code 400 (Bad Request)} if the ratePlanCharge is not valid,
     * or with status {@code 404 (Not Found)} if the ratePlanCharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratePlanCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rate-plan-charges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatePlanCharge> partialUpdateRatePlanCharge(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RatePlanCharge ratePlanCharge
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatePlanCharge partially : {}, {}", id, ratePlanCharge);
        if (ratePlanCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratePlanCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratePlanChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatePlanCharge> result = ratePlanChargeService.partialUpdate(ratePlanCharge);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratePlanCharge.getId())
        );
    }

    /**
     * {@code GET  /rate-plan-charges} : get all the ratePlanCharges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratePlanCharges in body.
     */
    @GetMapping("/rate-plan-charges")
    public ResponseEntity<List<RatePlanCharge>> getAllRatePlanCharges(Pageable pageable) {
        log.debug("REST request to get a page of RatePlanCharges");
        Page<RatePlanCharge> page = ratePlanChargeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rate-plan-charges/:id} : get the "id" ratePlanCharge.
     *
     * @param id the id of the ratePlanCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratePlanCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rate-plan-charges/{id}")
    public ResponseEntity<RatePlanCharge> getRatePlanCharge(@PathVariable String id) {
        log.debug("REST request to get RatePlanCharge : {}", id);
        Optional<RatePlanCharge> ratePlanCharge = ratePlanChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratePlanCharge);
    }

    /**
     * {@code DELETE  /rate-plan-charges/:id} : delete the "id" ratePlanCharge.
     *
     * @param id the id of the ratePlanCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rate-plan-charges/{id}")
    public ResponseEntity<Void> deleteRatePlanCharge(@PathVariable String id) {
        log.debug("REST request to delete RatePlanCharge : {}", id);
        ratePlanChargeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
