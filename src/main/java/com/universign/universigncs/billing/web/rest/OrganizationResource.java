package com.universign.universigncs.billing.web.rest;

import com.universign.universigncs.billing.domain.Organization;
import com.universign.universigncs.billing.repository.OrganizationRepository;
import com.universign.universigncs.billing.service.OrganizationService;
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
 * REST controller for managing {@link com.universign.universigncs.billing.domain.Organization}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    private static final String ENTITY_NAME = "organization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationService organizationService;

    private final OrganizationRepository organizationRepository;

    public OrganizationResource(OrganizationService organizationService, OrganizationRepository organizationRepository) {
        this.organizationService = organizationService;
        this.organizationRepository = organizationRepository;
    }

    /**
     * {@code POST  /organizations} : Create a new organization.
     *
     * @param organization the organization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organization, or with status {@code 400 (Bad Request)} if the organization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organizations")
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organization);
        if (organization.getId() != null) {
            throw new BadRequestAlertException("A new organization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Organization result = organizationService.save(organization);
        return ResponseEntity
            .created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /organizations/:id} : Updates an existing organization.
     *
     * @param id the id of the organization to save.
     * @param organization the organization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organization,
     * or with status {@code 400 (Bad Request)} if the organization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organizations/{id}")
    public ResponseEntity<Organization> updateOrganization(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Organization organization
    ) throws URISyntaxException {
        log.debug("REST request to update Organization : {}, {}", id, organization);
        if (organization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organization.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Organization result = organizationService.save(organization);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organization.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /organizations/:id} : Partial updates given fields of an existing organization, field will ignore if it is null
     *
     * @param id the id of the organization to save.
     * @param organization the organization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organization,
     * or with status {@code 400 (Bad Request)} if the organization is not valid,
     * or with status {@code 404 (Not Found)} if the organization is not found,
     * or with status {@code 500 (Internal Server Error)} if the organization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Organization> partialUpdateOrganization(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Organization organization
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organization partially : {}, {}", id, organization);
        if (organization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organization.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Organization> result = organizationService.partialUpdate(organization);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organization.getId())
        );
    }

    /**
     * {@code GET  /organizations} : get all the organizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizations in body.
     */
    @GetMapping("/organizations")
    public ResponseEntity<List<Organization>> getAllOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of Organizations");
        Page<Organization> page = organizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organizations/:id} : get the "id" organization.
     *
     * @param id the id of the organization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organizations/{id}")
    public ResponseEntity<Organization> getOrganization(@PathVariable String id) {
        log.debug("REST request to get Organization : {}", id);
        Optional<Organization> organization = organizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organization);
    }

    /**
     * {@code DELETE  /organizations/:id} : delete the "id" organization.
     *
     * @param id the id of the organization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organizations/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable String id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
