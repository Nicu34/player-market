package com.playerteamservice.controller;

import com.model.BaseResponse;
import com.playerteamservice.entity.BaseEntity;
import com.playerteamservice.exception.EntityNotFoundException;
import com.playerteamservice.service.GenericCRUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "CRUD operation for entities.")
public abstract class GenericCRUDController<OUT extends BaseResponse, IN> {

    public abstract GenericCRUDService<? extends BaseEntity, OUT, IN> getService();

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve all entities.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved entities."),
            @ApiResponse(code = 400, message = "Input validation error.")
    })
    public ResponseEntity<List<OUT>> findAllPlayers(Pageable pageable) {
        return ResponseEntity.ok(getService().findAll(pageable));
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve entity by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved entity."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 404, message = "Entity with given id not found")
    })
    public ResponseEntity<OUT> findById(@PathVariable int id) throws EntityNotFoundException {
        return ResponseEntity.ok(getService().getById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creation of an entity.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 404, message = "If there is another child entity related to primary entity and it is not found.")
    })
    public void save(@RequestBody @Validated IN request) throws EntityNotFoundException {
        getService().save(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update an entity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated."),
            @ApiResponse(code = 400, message = "Input validation error."),
            @ApiResponse(code = 404, message = "Entity with given id not found.")
    })
    public void update(@RequestBody @Validated IN request, @PathVariable int id) throws EntityNotFoundException {
        getService().update(request, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Deletion of an entity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted."),
            @ApiResponse(code = 400, message = "Input validation error.")
    })
    public void delete(@PathVariable int id) {
        getService().delete(id);
    }
}
