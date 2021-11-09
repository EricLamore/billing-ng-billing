package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.Consumption;
import com.universign.universigncs.billing.repository.ConsumptionRepository;
import com.universign.universigncs.billing.service.ConsumptionService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.Consumption}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionResource.class);

    private static final String ENTITY_NAME = "consumption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionService consumptionService;

    private final ConsumptionRepository consumptionRepository;

    public ConsumptionResource(ConsumptionService consumptionService, ConsumptionRepository consumptionRepository) {
        this.consumptionService = consumptionService;
        this.consumptionRepository = consumptionRepository;
    }

    /**
     * {@code POST  /consumptions} : Create a new consumption.
     *
     * @param consumption the consumption to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumption, or with status {@code 400 (Bad Request)} if the consumption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumptions")
    public ResponseEntity<Consumption> createConsumption(@RequestBody Consumption consumption) throws URISyntaxException {
        log.debug("REST request to save Consumption : {}", consumption);
        if (consumption.getId() != null) {
            throw new BadRequestAlertException("A new consumption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consumption result = consumptionService.save(consumption);
        return ResponseEntity
            .created(new URI("/api/consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /consumptions/:id} : Updates an existing consumption.
     *
     * @param id the id of the consumption to save.
     * @param consumption the consumption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumption,
     * or with status {@code 400 (Bad Request)} if the consumption is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumptions/{id}")
    public ResponseEntity<Consumption> updateConsumption(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Consumption consumption
    ) throws URISyntaxException {
        log.debug("REST request to update Consumption : {}, {}", id, consumption);
        if (consumption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consumption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consumptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Consumption result = consumptionService.save(consumption);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumption.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /consumptions/:id} : Partial updates given fields of an existing consumption, field will ignore if it is null
     *
     * @param id the id of the consumption to save.
     * @param consumption the consumption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumption,
     * or with status {@code 400 (Bad Request)} if the consumption is not valid,
     * or with status {@code 404 (Not Found)} if the consumption is not found,
     * or with status {@code 500 (Internal Server Error)} if the consumption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consumptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Consumption> partialUpdateConsumption(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Consumption consumption
    ) throws URISyntaxException {
        log.debug("REST request to partial update Consumption partially : {}, {}", id, consumption);
        if (consumption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consumption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consumptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Consumption> result = consumptionService.partialUpdate(consumption);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumption.getId())
        );
    }

    /**
     * {@code GET  /consumptions} : get all the consumptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptions in body.
     */
    @GetMapping("/consumptions")
    public ResponseEntity<List<Consumption>> getAllConsumptions(Pageable pageable) {
        log.debug("REST request to get a page of Consumptions");
        Page<Consumption> page = consumptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumptions/:id} : get the "id" consumption.
     *
     * @param id the id of the consumption to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumption, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumptions/{id}")
    public ResponseEntity<Consumption> getConsumption(@PathVariable String id) {
        log.debug("REST request to get Consumption : {}", id);
        Optional<Consumption> consumption = consumptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumption);
    }

    /**
     * {@code DELETE  /consumptions/:id} : delete the "id" consumption.
     *
     * @param id the id of the consumption to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumptions/{id}")
    public ResponseEntity<Void> deleteConsumption(@PathVariable String id) {
        log.debug("REST request to delete Consumption : {}", id);
        consumptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
