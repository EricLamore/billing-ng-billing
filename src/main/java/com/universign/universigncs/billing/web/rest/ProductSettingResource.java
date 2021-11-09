package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.ProductSetting;
import com.universign.universigncs.billing.repository.ProductSettingRepository;
import com.universign.universigncs.billing.service.ProductSettingService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.ProductSetting}.
 */
@RestController
@RequestMapping("/api")
public class ProductSettingResource {

    private final Logger log = LoggerFactory.getLogger(ProductSettingResource.class);

    private static final String ENTITY_NAME = "productSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSettingService productSettingService;

    private final ProductSettingRepository productSettingRepository;

    public ProductSettingResource(ProductSettingService productSettingService, ProductSettingRepository productSettingRepository) {
        this.productSettingService = productSettingService;
        this.productSettingRepository = productSettingRepository;
    }

    /**
     * {@code POST  /product-settings} : Create a new productSetting.
     *
     * @param productSetting the productSetting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSetting, or with status {@code 400 (Bad Request)} if the productSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-settings")
    public ResponseEntity<ProductSetting> createProductSetting(@RequestBody ProductSetting productSetting) throws URISyntaxException {
        log.debug("REST request to save ProductSetting : {}", productSetting);
        if (productSetting.getId() != null) {
            throw new BadRequestAlertException("A new productSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSetting result = productSettingService.save(productSetting);
        return ResponseEntity
            .created(new URI("/api/product-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /product-settings/:id} : Updates an existing productSetting.
     *
     * @param id the id of the productSetting to save.
     * @param productSetting the productSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSetting,
     * or with status {@code 400 (Bad Request)} if the productSetting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSetting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-settings/{id}")
    public ResponseEntity<ProductSetting> updateProductSetting(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSetting productSetting
    ) throws URISyntaxException {
        log.debug("REST request to update ProductSetting : {}, {}", id, productSetting);
        if (productSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductSetting result = productSettingService.save(productSetting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSetting.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-settings/:id} : Partial updates given fields of an existing productSetting, field will ignore if it is null
     *
     * @param id the id of the productSetting to save.
     * @param productSetting the productSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSetting,
     * or with status {@code 400 (Bad Request)} if the productSetting is not valid,
     * or with status {@code 404 (Not Found)} if the productSetting is not found,
     * or with status {@code 500 (Internal Server Error)} if the productSetting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductSetting> partialUpdateProductSetting(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ProductSetting productSetting
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductSetting partially : {}, {}", id, productSetting);
        if (productSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductSetting> result = productSettingService.partialUpdate(productSetting);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSetting.getId())
        );
    }

    /**
     * {@code GET  /product-settings} : get all the productSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSettings in body.
     */
    @GetMapping("/product-settings")
    public ResponseEntity<List<ProductSetting>> getAllProductSettings(Pageable pageable) {
        log.debug("REST request to get a page of ProductSettings");
        Page<ProductSetting> page = productSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-settings/:id} : get the "id" productSetting.
     *
     * @param id the id of the productSetting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSetting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-settings/{id}")
    public ResponseEntity<ProductSetting> getProductSetting(@PathVariable String id) {
        log.debug("REST request to get ProductSetting : {}", id);
        Optional<ProductSetting> productSetting = productSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSetting);
    }

    /**
     * {@code DELETE  /product-settings/:id} : delete the "id" productSetting.
     *
     * @param id the id of the productSetting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-settings/{id}")
    public ResponseEntity<Void> deleteProductSetting(@PathVariable String id) {
        log.debug("REST request to delete ProductSetting : {}", id);
        productSettingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
