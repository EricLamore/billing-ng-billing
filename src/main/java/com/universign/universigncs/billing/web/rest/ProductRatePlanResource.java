package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.ProductRatePlan;
import com.universign.universigncs.billing.repository.ProductRatePlanRepository;
import com.universign.universigncs.billing.service.ProductRatePlanService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.ProductRatePlan}.
 */
@RestController
@RequestMapping("/api")
public class ProductRatePlanResource {

    private final Logger log = LoggerFactory.getLogger(ProductRatePlanResource.class);

    private static final String ENTITY_NAME = "productRatePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductRatePlanService productRatePlanService;

    private final ProductRatePlanRepository productRatePlanRepository;

    public ProductRatePlanResource(ProductRatePlanService productRatePlanService, ProductRatePlanRepository productRatePlanRepository) {
        this.productRatePlanService = productRatePlanService;
        this.productRatePlanRepository = productRatePlanRepository;
    }

    /**
     * {@code POST  /product-rate-plans} : Create a new productRatePlan.
     *
     * @param productRatePlan the productRatePlan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productRatePlan, or with status {@code 400 (Bad Request)} if the productRatePlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-rate-plans")
    public ResponseEntity<ProductRatePlan> createProductRatePlan(@RequestBody ProductRatePlan productRatePlan) throws URISyntaxException {
        log.debug("REST request to save ProductRatePlan : {}", productRatePlan);
        if (productRatePlan.getId() != null) {
            throw new BadRequestAlertException("A new productRatePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductRatePlan result = productRatePlanService.save(productRatePlan);
        return ResponseEntity
            .created(new URI("/api/product-rate-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /product-rate-plans/:id} : Updates an existing productRatePlan.
     *
     * @param id the id of the productRatePlan to save.
     * @param productRatePlan the productRatePlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productRatePlan,
     * or with status {@code 400 (Bad Request)} if the productRatePlan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productRatePlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-rate-plans/{id}")
    public ResponseEntity<ProductRatePlan> updateProductRatePlan(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductRatePlan productRatePlan
    ) throws URISyntaxException {
        log.debug("REST request to update ProductRatePlan : {}, {}", id, productRatePlan);
        if (productRatePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productRatePlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRatePlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductRatePlan result = productRatePlanService.save(productRatePlan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productRatePlan.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-rate-plans/:id} : Partial updates given fields of an existing productRatePlan, field will ignore if it is null
     *
     * @param id the id of the productRatePlan to save.
     * @param productRatePlan the productRatePlan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productRatePlan,
     * or with status {@code 400 (Bad Request)} if the productRatePlan is not valid,
     * or with status {@code 404 (Not Found)} if the productRatePlan is not found,
     * or with status {@code 500 (Internal Server Error)} if the productRatePlan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-rate-plans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductRatePlan> partialUpdateProductRatePlan(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductRatePlan productRatePlan
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductRatePlan partially : {}, {}", id, productRatePlan);
        if (productRatePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productRatePlan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRatePlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductRatePlan> result = productRatePlanService.partialUpdate(productRatePlan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productRatePlan.getId())
        );
    }

    /**
     * {@code GET  /product-rate-plans} : get all the productRatePlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productRatePlans in body.
     */
    @GetMapping("/product-rate-plans")
    public ResponseEntity<List<ProductRatePlan>> getAllProductRatePlans(Pageable pageable) {
        log.debug("REST request to get a page of ProductRatePlans");
        Page<ProductRatePlan> page = productRatePlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-rate-plans/:id} : get the "id" productRatePlan.
     *
     * @param id the id of the productRatePlan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productRatePlan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-rate-plans/{id}")
    public ResponseEntity<ProductRatePlan> getProductRatePlan(@PathVariable String id) {
        log.debug("REST request to get ProductRatePlan : {}", id);
        Optional<ProductRatePlan> productRatePlan = productRatePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productRatePlan);
    }

    /**
     * {@code DELETE  /product-rate-plans/:id} : delete the "id" productRatePlan.
     *
     * @param id the id of the productRatePlan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-rate-plans/{id}")
    public ResponseEntity<Void> deleteProductRatePlan(@PathVariable String id) {
        log.debug("REST request to delete ProductRatePlan : {}", id);
        productRatePlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
