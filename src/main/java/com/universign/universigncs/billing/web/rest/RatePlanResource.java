package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.RatePlan;
import com.universign.universigncs.billing.repository.RatePlanRepository;
import com.universign.universigncs.billing.service.RatePlanService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.RatePlan}.
 */
@RestController
@RequestMapping("/api")
public class RatePlanResource {

    private final Logger log = LoggerFactory.getLogger(RatePlanResource.class);

    private static final String ENTITY_NAME = "ratePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatePlanService ratePlanService;

    private final RatePlanRepository ratePlanRepository;

    public RatePlanResource(RatePlanService ratePlanService, RatePlanRepository ratePlanRepository) {
        this.ratePlanService = ratePlanService;
        this.ratePlanRepository = ratePlanRepository;
    }

    /**
     * {@code POST  /rate-plans} : Create a new ratePlan.
     *
     * @param ratePlan the ratePlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratePlan, or with status {@code 400 (Bad Request)} if the ratePlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rate-plans")
    public ResponseEntity<RatePlan> createRatePlan(@RequestBody RatePlan ratePlan) throws URISyntaxException {
        log.debug("REST request to save RatePlan : {}", ratePlan);
        if (ratePlan.getId() != null) {
            throw new BadRequestAlertException("A new ratePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatePlan result = ratePlanService.save(ratePlan);
        return ResponseEntity
            .created(new URI("/api/rate-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /rate-plans/:id} : Updates an existing ratePlan.
     *
     * @param id the id of the ratePlan to save.
     * @param ratePlan the ratePlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratePlan,
     * or with status {@code 400 (Bad Request)} if the ratePlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratePlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rate-plans/{id}")
    public ResponseEntity<RatePlan> updateRatePlan(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RatePlan ratePlan
    ) throws URISyntaxException {
        log.debug("REST request to update RatePlan : {}, {}", id, ratePlan);
        if (ratePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratePlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratePlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatePlan result = ratePlanService.save(ratePlan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratePlan.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /rate-plans/:id} : Partial updates given fields of an existing ratePlan, field will ignore if it is null
     *
     * @param id the id of the ratePlan to save.
     * @param ratePlan the ratePlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratePlan,
     * or with status {@code 400 (Bad Request)} if the ratePlan is not valid,
     * or with status {@code 404 (Not Found)} if the ratePlan is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratePlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rate-plans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatePlan> partialUpdateRatePlan(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RatePlan ratePlan
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatePlan partially : {}, {}", id, ratePlan);
        if (ratePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratePlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratePlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatePlan> result = ratePlanService.partialUpdate(ratePlan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratePlan.getId())
        );
    }

    /**
     * {@code GET  /rate-plans} : get all the ratePlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratePlans in body.
     */
    @GetMapping("/rate-plans")
    public ResponseEntity<List<RatePlan>> getAllRatePlans(Pageable pageable) {
        log.debug("REST request to get a page of RatePlans");
        Page<RatePlan> page = ratePlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rate-plans/:id} : get the "id" ratePlan.
     *
     * @param id the id of the ratePlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratePlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rate-plans/{id}")
    public ResponseEntity<RatePlan> getRatePlan(@PathVariable String id) {
        log.debug("REST request to get RatePlan : {}", id);
        Optional<RatePlan> ratePlan = ratePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratePlan);
    }

    /**
     * {@code DELETE  /rate-plans/:id} : delete the "id" ratePlan.
     *
     * @param id the id of the ratePlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rate-plans/{id}")
    public ResponseEntity<Void> deleteRatePlan(@PathVariable String id) {
        log.debug("REST request to delete RatePlan : {}", id);
        ratePlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
