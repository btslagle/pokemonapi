package com.bslagle.pokemonexercise.pokemon;

import java.util.Map;
import java.util.HashMap;

import com.bslagle.pokemonexercise.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("api/some-resources")
public class SomeResourceController {
    @Autowired
    private SomeResourceService someResourceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Iterable<SomeResource>> list() {
        Iterable<SomeResource> someResources = someResourceService.list();
        return createHashPlural(someResources);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, SomeResource> read(@PathVariable Long id) {
        SomeResource someResource = someResourceService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with that ID"));
        return createHashSingular(someResource);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, SomeResource> create(@Validated @RequestBody SomeResource someResource) {
        SomeResource createdResource = someResourceService.create(someResource);
        return createHashSingular(createdResource);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, SomeResource> update(@RequestBody SomeResource someResource, @PathVariable Long id) {
        SomeResource updatedResource = someResourceService
                .update(someResource)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with that ID"));

        return createHashSingular(updatedResource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        someResourceService.deleteById(id);
    }

    private Map<String, SomeResource> createHashSingular(SomeResource someResource) {
        Map<String, SomeResource> response = new HashMap<String, SomeResource>();
        response.put("someResource", someResource);

        return response;
    }

    private Map<String, Iterable<SomeResource>> createHashPlural(Iterable<SomeResource> someResources) {
        Map<String, Iterable<SomeResource>> response = new HashMap<String, Iterable<SomeResource>>();
        response.put("someResources", someResources);

        return response;
    }
}