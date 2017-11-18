package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AircraftModel;
import com.mycompany.myapp.repository.AircraftModelRepository;
import com.mycompany.myapp.repository.rsql.CustomRsqlVisitor;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.util.SearchConditionUtil;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AircraftModel.
 */
@RestController
@RequestMapping("/api")
public class AircraftModelResource {

    private final Logger log = LoggerFactory.getLogger(AircraftModelResource.class);

    private static final String ENTITY_NAME = "aircraftModel";

    private final AircraftModelRepository aircraftModelRepository;

    public AircraftModelResource(AircraftModelRepository aircraftModelRepository) {
        this.aircraftModelRepository = aircraftModelRepository;
    }

    /**
     * POST  /aircraft-models : Create a new aircraftModel.
     *
     * @param aircraftModel the aircraftModel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aircraftModel, or with status 400 (Bad Request) if the aircraftModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aircraft-models")
    @Timed
    //@Secured({AuthoritiesConstants.AIRCRAFTMODEL_ADD, AuthoritiesConstants.ADMIN})
    public ResponseEntity<AircraftModel> createAircraftModel(@RequestBody AircraftModel aircraftModel) throws URISyntaxException {
        log.debug("REST request to save AircraftModel : {}", aircraftModel);
        if (aircraftModel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aircraftModel cannot already have an ID")).body(null);
        }
        AircraftModel result = aircraftModelRepository.save(aircraftModel);
        return ResponseEntity.created(new URI("/api/aircraft-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aircraft-models : Updates an existing aircraftModel.
     *
     * @param aircraftModel the aircraftModel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aircraftModel,
     * or with status 400 (Bad Request) if the aircraftModel is not valid,
     * or with status 500 (Internal Server Error) if the aircraftModel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aircraft-models")
    @Timed
   // @Secured({AuthoritiesConstants.AIRCRAFTMODEL_UP, AuthoritiesConstants.ADMIN})
    public ResponseEntity<AircraftModel> updateAircraftModel(@RequestBody AircraftModel aircraftModel) throws URISyntaxException {
        log.debug("REST request to update AircraftModel : {}", aircraftModel);
        if (aircraftModel.getId() == null) {
            return createAircraftModel(aircraftModel);
        }
        AircraftModel result = aircraftModelRepository.save(aircraftModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aircraftModel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aircraft-models : get all the aircraftModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aircraftModels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/aircraft-models")
    @Timed
    public ResponseEntity<List<AircraftModel>> getAllAircraftModels(@RequestParam(value = "search", required = false) String search, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AircraftModels");
        /*ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withIgnorePaths("deleted")
            .withIgnorePaths("createdDate")
            .withIgnorePaths("lastModifiedDate")
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<AircraftModel> example = Example.of(aircraftModel,matcher);
        Page<AircraftModel> page = aircraftModelRepository.findAll(example,pageable);*/
        Page<AircraftModel> page;
        search= SearchConditionUtil.GetAddConditon(search);
        if(search != null){
            Node rootNode = new RSQLParser().parse(search);
            Specification<AircraftModel> spec = rootNode.accept(new CustomRsqlVisitor<AircraftModel>());
            page = aircraftModelRepository.findAll(spec, pageable);
        } else {
            page = aircraftModelRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aircraft-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aircraft-models/:id : get the "id" aircraftModel.
     *
     * @param id the id of the aircraftModel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aircraftModel, or with status 404 (Not Found)
     */
    @GetMapping("/aircraft-models/{id}")
    @Timed
    //@Secured({AuthoritiesConstants.AIRCRAFTMODEL_SEE, AuthoritiesConstants.ADMIN})
    public ResponseEntity<AircraftModel> getAircraftModel(@PathVariable Long id) {
        log.debug("REST request to get AircraftModel : {}", id);
        AircraftModel aircraftModel = aircraftModelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aircraftModel));
    }

    /**
     * DELETE  /aircraft-models/:id : delete the "id" aircraftModel.
     *
     * @param id the id of the aircraftModel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aircraft-models/{id}")
    @Timed
   // @Secured({AuthoritiesConstants.AIRCRAFTMODEL_DEL, AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deleteAircraftModel(@PathVariable Long id) {
        log.debug("REST request to delete AircraftModel : {}", id);
        aircraftModelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
