package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.PersonReferer;
import com.universign.universigncs.billing.repository.PersonRefererRepository;
import com.universign.universigncs.billing.service.PersonRefererService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.PersonReferer}.
 */
@RestController
@RequestMapping("/api")
public class PersonRefererResource {

    private final Logger log = LoggerFactory.getLogger(PersonRefererResource.class);

    private static final String ENTITY_NAME = "personReferer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonRefererService personRefererService;

    private final PersonRefererRepository personRefererRepository;

    public PersonRefererResource(PersonRefererService personRefererService, PersonRefererRepository personRefererRepository) {
        this.personRefererService = personRefererService;
        this.personRefererRepository = personRefererRepository;
    }

    /**
     * {@code POST  /person-referers} : Create a new personReferer.
     *
     * @param personReferer the personReferer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personReferer, or with status {@code 400 (Bad Request)} if the personReferer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-referers")
    public ResponseEntity<PersonReferer> createPersonReferer(@RequestBody PersonReferer personReferer) throws URISyntaxException {
        log.debug("REST request to save PersonReferer : {}", personReferer);
        if (personReferer.getId() != null) {
            throw new BadRequestAlertException("A new personReferer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonReferer result = personRefererService.save(personReferer);
        return ResponseEntity
            .created(new URI("/api/person-referers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /person-referers/:id} : Updates an existing personReferer.
     *
     * @param id the id of the personReferer to save.
     * @param personReferer the personReferer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personReferer,
     * or with status {@code 400 (Bad Request)} if the personReferer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personReferer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-referers/{id}")
    public ResponseEntity<PersonReferer> updatePersonReferer(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PersonReferer personReferer
    ) throws URISyntaxException {
        log.debug("REST request to update PersonReferer : {}, {}", id, personReferer);
        if (personReferer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personReferer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personRefererRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonReferer result = personRefererService.save(personReferer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personReferer.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-referers/:id} : Partial updates given fields of an existing personReferer, field will ignore if it is null
     *
     * @param id the id of the personReferer to save.
     * @param personReferer the personReferer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personReferer,
     * or with status {@code 400 (Bad Request)} if the personReferer is not valid,
     * or with status {@code 404 (Not Found)} if the personReferer is not found,
     * or with status {@code 500 (Internal Server Error)} if the personReferer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-referers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonReferer> partialUpdatePersonReferer(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PersonReferer personReferer
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonReferer partially : {}, {}", id, personReferer);
        if (personReferer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personReferer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personRefererRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonReferer> result = personRefererService.partialUpdate(personReferer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personReferer.getId())
        );
    }

    /**
     * {@code GET  /person-referers} : get all the personReferers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personReferers in body.
     */
    @GetMapping("/person-referers")
    public ResponseEntity<List<PersonReferer>> getAllPersonReferers(Pageable pageable) {
        log.debug("REST request to get a page of PersonReferers");
        Page<PersonReferer> page = personRefererService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-referers/:id} : get the "id" personReferer.
     *
     * @param id the id of the personReferer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personReferer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-referers/{id}")
    public ResponseEntity<PersonReferer> getPersonReferer(@PathVariable String id) {
        log.debug("REST request to get PersonReferer : {}", id);
        Optional<PersonReferer> personReferer = personRefererService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personReferer);
    }

    /**
     * {@code DELETE  /person-referers/:id} : delete the "id" personReferer.
     *
     * @param id the id of the personReferer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-referers/{id}")
    public ResponseEntity<Void> deletePersonReferer(@PathVariable String id) {
        log.debug("REST request to delete PersonReferer : {}", id);
        personRefererService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
