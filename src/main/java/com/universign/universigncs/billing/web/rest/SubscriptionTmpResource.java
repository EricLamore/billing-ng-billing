package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.SubscriptionTmp;
import com.universign.universigncs.billing.repository.SubscriptionTmpRepository;
import com.universign.universigncs.billing.service.SubscriptionTmpService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.SubscriptionTmp}.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionTmpResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionTmpResource.class);

    private static final String ENTITY_NAME = "subscriptionTmp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionTmpService subscriptionTmpService;

    private final SubscriptionTmpRepository subscriptionTmpRepository;

    public SubscriptionTmpResource(SubscriptionTmpService subscriptionTmpService, SubscriptionTmpRepository subscriptionTmpRepository) {
        this.subscriptionTmpService = subscriptionTmpService;
        this.subscriptionTmpRepository = subscriptionTmpRepository;
    }

    /**
     * {@code POST  /subscription-tmps} : Create a new subscriptionTmp.
     *
     * @param subscriptionTmp the subscriptionTmp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriptionTmp, or with status {@code 400 (Bad Request)} if the subscriptionTmp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscription-tmps")
    public ResponseEntity<SubscriptionTmp> createSubscriptionTmp(@RequestBody SubscriptionTmp subscriptionTmp) throws URISyntaxException {
        log.debug("REST request to save SubscriptionTmp : {}", subscriptionTmp);
        if (subscriptionTmp.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionTmp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionTmp result = subscriptionTmpService.save(subscriptionTmp);
        return ResponseEntity
            .created(new URI("/api/subscription-tmps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /subscription-tmps/:id} : Updates an existing subscriptionTmp.
     *
     * @param id the id of the subscriptionTmp to save.
     * @param subscriptionTmp the subscriptionTmp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionTmp,
     * or with status {@code 400 (Bad Request)} if the subscriptionTmp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionTmp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscription-tmps/{id}")
    public ResponseEntity<SubscriptionTmp> updateSubscriptionTmp(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SubscriptionTmp subscriptionTmp
    ) throws URISyntaxException {
        log.debug("REST request to update SubscriptionTmp : {}, {}", id, subscriptionTmp);
        if (subscriptionTmp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriptionTmp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriptionTmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubscriptionTmp result = subscriptionTmpService.save(subscriptionTmp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionTmp.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /subscription-tmps/:id} : Partial updates given fields of an existing subscriptionTmp, field will ignore if it is null
     *
     * @param id the id of the subscriptionTmp to save.
     * @param subscriptionTmp the subscriptionTmp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionTmp,
     * or with status {@code 400 (Bad Request)} if the subscriptionTmp is not valid,
     * or with status {@code 404 (Not Found)} if the subscriptionTmp is not found,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionTmp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subscription-tmps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubscriptionTmp> partialUpdateSubscriptionTmp(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SubscriptionTmp subscriptionTmp
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubscriptionTmp partially : {}, {}", id, subscriptionTmp);
        if (subscriptionTmp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriptionTmp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriptionTmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubscriptionTmp> result = subscriptionTmpService.partialUpdate(subscriptionTmp);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionTmp.getId())
        );
    }

    /**
     * {@code GET  /subscription-tmps} : get all the subscriptionTmps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscriptionTmps in body.
     */
    @GetMapping("/subscription-tmps")
    public ResponseEntity<List<SubscriptionTmp>> getAllSubscriptionTmps(Pageable pageable) {
        log.debug("REST request to get a page of SubscriptionTmps");
        Page<SubscriptionTmp> page = subscriptionTmpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subscription-tmps/:id} : get the "id" subscriptionTmp.
     *
     * @param id the id of the subscriptionTmp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriptionTmp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscription-tmps/{id}")
    public ResponseEntity<SubscriptionTmp> getSubscriptionTmp(@PathVariable String id) {
        log.debug("REST request to get SubscriptionTmp : {}", id);
        Optional<SubscriptionTmp> subscriptionTmp = subscriptionTmpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriptionTmp);
    }

    /**
     * {@code DELETE  /subscription-tmps/:id} : delete the "id" subscriptionTmp.
     *
     * @param id the id of the subscriptionTmp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscription-tmps/{id}")
    public ResponseEntity<Void> deleteSubscriptionTmp(@PathVariable String id) {
        log.debug("REST request to delete SubscriptionTmp : {}", id);
        subscriptionTmpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
