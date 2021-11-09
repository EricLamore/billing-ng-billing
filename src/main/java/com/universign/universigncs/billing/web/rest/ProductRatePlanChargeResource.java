package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.ProductRatePlanCharge;
import com.universign.universigncs.billing.repository.ProductRatePlanChargeRepository;
import com.universign.universigncs.billing.service.ProductRatePlanChargeService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.ProductRatePlanCharge}.
 */
@RestController
@RequestMapping("/api")
public class ProductRatePlanChargeResource {

    private final Logger log = LoggerFactory.getLogger(ProductRatePlanChargeResource.class);

    private static final String ENTITY_NAME = "productRatePlanCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductRatePlanChargeService productRatePlanChargeService;

    private final ProductRatePlanChargeRepository productRatePlanChargeRepository;

    public ProductRatePlanChargeResource(
        ProductRatePlanChargeService productRatePlanChargeService,
        ProductRatePlanChargeRepository productRatePlanChargeRepository
    ) {
        this.productRatePlanChargeService = productRatePlanChargeService;
        this.productRatePlanChargeRepository = productRatePlanChargeRepository;
    }

    /**
     * {@code POST  /product-rate-plan-charges} : Create a new productRatePlanCharge.
     *
     * @param productRatePlanCharge the productRatePlanCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productRatePlanCharge, or with status {@code 400 (Bad Request)} if the productRatePlanCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-rate-plan-charges")
    public ResponseEntity<ProductRatePlanCharge> createProductRatePlanCharge(@RequestBody ProductRatePlanCharge productRatePlanCharge)
        throws URISyntaxException {
        log.debug("REST request to save ProductRatePlanCharge : {}", productRatePlanCharge);
        if (productRatePlanCharge.getId() != null) {
            throw new BadRequestAlertException("A new productRatePlanCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductRatePlanCharge result = productRatePlanChargeService.save(productRatePlanCharge);
        return ResponseEntity
            .created(new URI("/api/product-rate-plan-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /product-rate-plan-charges/:id} : Updates an existing productRatePlanCharge.
     *
     * @param id the id of the productRatePlanCharge to save.
     * @param productRatePlanCharge the productRatePlanCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productRatePlanCharge,
     * or with status {@code 400 (Bad Request)} if the productRatePlanCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productRatePlanCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-rate-plan-charges/{id}")
    public ResponseEntity<ProductRatePlanCharge> updateProductRatePlanCharge(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductRatePlanCharge productRatePlanCharge
    ) throws URISyntaxException {
        log.debug("REST request to update ProductRatePlanCharge : {}, {}", id, productRatePlanCharge);
        if (productRatePlanCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productRatePlanCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRatePlanChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductRatePlanCharge result = productRatePlanChargeService.save(productRatePlanCharge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productRatePlanCharge.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-rate-plan-charges/:id} : Partial updates given fields of an existing productRatePlanCharge, field will ignore if it is null
     *
     * @param id the id of the productRatePlanCharge to save.
     * @param productRatePlanCharge the productRatePlanCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productRatePlanCharge,
     * or with status {@code 400 (Bad Request)} if the productRatePlanCharge is not valid,
     * or with status {@code 404 (Not Found)} if the productRatePlanCharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the productRatePlanCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-rate-plan-charges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductRatePlanCharge> partialUpdateProductRatePlanCharge(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductRatePlanCharge productRatePlanCharge
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductRatePlanCharge partially : {}, {}", id, productRatePlanCharge);
        if (productRatePlanCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productRatePlanCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRatePlanChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductRatePlanCharge> result = productRatePlanChargeService.partialUpdate(productRatePlanCharge);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productRatePlanCharge.getId())
        );
    }

    /**
     * {@code GET  /product-rate-plan-charges} : get all the productRatePlanCharges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productRatePlanCharges in body.
     */
    @GetMapping("/product-rate-plan-charges")
    public ResponseEntity<List<ProductRatePlanCharge>> getAllProductRatePlanCharges(Pageable pageable) {
        log.debug("REST request to get a page of ProductRatePlanCharges");
        Page<ProductRatePlanCharge> page = productRatePlanChargeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-rate-plan-charges/:id} : get the "id" productRatePlanCharge.
     *
     * @param id the id of the productRatePlanCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productRatePlanCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-rate-plan-charges/{id}")
    public ResponseEntity<ProductRatePlanCharge> getProductRatePlanCharge(@PathVariable String id) {
        log.debug("REST request to get ProductRatePlanCharge : {}", id);
        Optional<ProductRatePlanCharge> productRatePlanCharge = productRatePlanChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productRatePlanCharge);
    }

    /**
     * {@code DELETE  /product-rate-plan-charges/:id} : delete the "id" productRatePlanCharge.
     *
     * @param id the id of the productRatePlanCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-rate-plan-charges/{id}")
    public ResponseEntity<Void> deleteProductRatePlanCharge(@PathVariable String id) {
        log.debug("REST request to delete ProductRatePlanCharge : {}", id);
        productRatePlanChargeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
