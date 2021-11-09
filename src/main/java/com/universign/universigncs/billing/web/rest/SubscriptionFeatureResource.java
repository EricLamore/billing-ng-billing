package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.SubscriptionFeature;
import com.universign.universigncs.billing.repository.SubscriptionFeatureRepository;
import com.universign.universigncs.billing.service.SubscriptionFeatureService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.SubscriptionFeature}.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionFeatureResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionFeatureResource.class);

    private static final String ENTITY_NAME = "subscriptionFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionFeatureService subscriptionFeatureService;

    private final SubscriptionFeatureRepository subscriptionFeatureRepository;

    public SubscriptionFeatureResource(
        SubscriptionFeatureService subscriptionFeatureService,
        SubscriptionFeatureRepository subscriptionFeatureRepository
    ) {
        this.subscriptionFeatureService = subscriptionFeatureService;
        this.subscriptionFeatureRepository = subscriptionFeatureRepository;
    }

    /**
     * {@code POST  /subscription-features} : Create a new subscriptionFeature.
     *
     * @param subscriptionFeature the subscriptionFeature to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriptionFeature, or with status {@code 400 (Bad Request)} if the subscriptionFeature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscription-features")
    public ResponseEntity<SubscriptionFeature> createSubscriptionFeature(@RequestBody SubscriptionFeature subscriptionFeature)
        throws URISyntaxException {
        log.debug("REST request to save SubscriptionFeature : {}", subscriptionFeature);
        if (subscriptionFeature.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionFeature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionFeature result = subscriptionFeatureService.save(subscriptionFeature);
        return ResponseEntity
            .created(new URI("/api/subscription-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /subscription-features/:id} : Updates an existing subscriptionFeature.
     *
     * @param id the id of the subscriptionFeature to save.
     * @param subscriptionFeature the subscriptionFeature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionFeature,
     * or with status {@code 400 (Bad Request)} if the subscriptionFeature is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionFeature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscription-features/{id}")
    public ResponseEntity<SubscriptionFeature> updateSubscriptionFeature(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SubscriptionFeature subscriptionFeature
    ) throws URISyntaxException {
        log.debug("REST request to update SubscriptionFeature : {}, {}", id, subscriptionFeature);
        if (subscriptionFeature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriptionFeature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriptionFeatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubscriptionFeature result = subscriptionFeatureService.save(subscriptionFeature);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionFeature.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /subscription-features/:id} : Partial updates given fields of an existing subscriptionFeature, field will ignore if it is null
     *
     * @param id the id of the subscriptionFeature to save.
     * @param subscriptionFeature the subscriptionFeature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionFeature,
     * or with status {@code 400 (Bad Request)} if the subscriptionFeature is not valid,
     * or with status {@code 404 (Not Found)} if the subscriptionFeature is not found,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionFeature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subscription-features/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubscriptionFeature> partialUpdateSubscriptionFeature(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SubscriptionFeature subscriptionFeature
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubscriptionFeature partially : {}, {}", id, subscriptionFeature);
        if (subscriptionFeature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriptionFeature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriptionFeatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubscriptionFeature> result = subscriptionFeatureService.partialUpdate(subscriptionFeature);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionFeature.getId())
        );
    }

    /**
     * {@code GET  /subscription-features} : get all the subscriptionFeatures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscriptionFeatures in body.
     */
    @GetMapping("/subscription-features")
    public ResponseEntity<List<SubscriptionFeature>> getAllSubscriptionFeatures(Pageable pageable) {
        log.debug("REST request to get a page of SubscriptionFeatures");
        Page<SubscriptionFeature> page = subscriptionFeatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subscription-features/:id} : get the "id" subscriptionFeature.
     *
     * @param id the id of the subscriptionFeature to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriptionFeature, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscription-features/{id}")
    public ResponseEntity<SubscriptionFeature> getSubscriptionFeature(@PathVariable String id) {
        log.debug("REST request to get SubscriptionFeature : {}", id);
        Optional<SubscriptionFeature> subscriptionFeature = subscriptionFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriptionFeature);
    }

    /**
     * {@code DELETE  /subscription-features/:id} : delete the "id" subscriptionFeature.
     *
     * @param id the id of the subscriptionFeature to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscription-features/{id}")
    public ResponseEntity<Void> deleteSubscriptionFeature(@PathVariable String id) {
        log.debug("REST request to delete SubscriptionFeature : {}", id);
        subscriptionFeatureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
