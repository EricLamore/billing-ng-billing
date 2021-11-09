package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.Refund;
import com.universign.universigncs.billing.repository.RefundRepository;
import com.universign.universigncs.billing.service.RefundService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.Refund}.
 */
@RestController
@RequestMapping("/api")
public class RefundResource {

    private final Logger log = LoggerFactory.getLogger(RefundResource.class);

    private static final String ENTITY_NAME = "refund";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundService refundService;

    private final RefundRepository refundRepository;

    public RefundResource(RefundService refundService, RefundRepository refundRepository) {
        this.refundService = refundService;
        this.refundRepository = refundRepository;
    }

    /**
     * {@code POST  /refunds} : Create a new refund.
     *
     * @param refund the refund to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refund, or with status {@code 400 (Bad Request)} if the refund has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refunds")
    public ResponseEntity<Refund> createRefund(@RequestBody Refund refund) throws URISyntaxException {
        log.debug("REST request to save Refund : {}", refund);
        if (refund.getId() != null) {
            throw new BadRequestAlertException("A new refund cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Refund result = refundService.save(refund);
        return ResponseEntity
            .created(new URI("/api/refunds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /refunds/:id} : Updates an existing refund.
     *
     * @param id the id of the refund to save.
     * @param refund the refund to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refund,
     * or with status {@code 400 (Bad Request)} if the refund is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refund couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refunds/{id}")
    public ResponseEntity<Refund> updateRefund(@PathVariable(value = "id", required = false) final String id, @RequestBody Refund refund)
        throws URISyntaxException {
        log.debug("REST request to update Refund : {}, {}", id, refund);
        if (refund.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refund.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Refund result = refundService.save(refund);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refund.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /refunds/:id} : Partial updates given fields of an existing refund, field will ignore if it is null
     *
     * @param id the id of the refund to save.
     * @param refund the refund to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refund,
     * or with status {@code 400 (Bad Request)} if the refund is not valid,
     * or with status {@code 404 (Not Found)} if the refund is not found,
     * or with status {@code 500 (Internal Server Error)} if the refund couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/refunds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Refund> partialUpdateRefund(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Refund refund
    ) throws URISyntaxException {
        log.debug("REST request to partial update Refund partially : {}, {}", id, refund);
        if (refund.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refund.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Refund> result = refundService.partialUpdate(refund);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refund.getId()));
    }

    /**
     * {@code GET  /refunds} : get all the refunds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refunds in body.
     */
    @GetMapping("/refunds")
    public ResponseEntity<List<Refund>> getAllRefunds(Pageable pageable) {
        log.debug("REST request to get a page of Refunds");
        Page<Refund> page = refundService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /refunds/:id} : get the "id" refund.
     *
     * @param id the id of the refund to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refund, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refunds/{id}")
    public ResponseEntity<Refund> getRefund(@PathVariable String id) {
        log.debug("REST request to get Refund : {}", id);
        Optional<Refund> refund = refundService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refund);
    }

    /**
     * {@code DELETE  /refunds/:id} : delete the "id" refund.
     *
     * @param id the id of the refund to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refunds/{id}")
    public ResponseEntity<Void> deleteRefund(@PathVariable String id) {
        log.debug("REST request to delete Refund : {}", id);
        refundService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
