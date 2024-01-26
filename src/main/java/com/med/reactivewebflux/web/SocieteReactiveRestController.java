package com.med.reactivewebflux.web;

import com.med.reactivewebflux.dao.SocieteRepository;
import com.med.reactivewebflux.entity.Societe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SocieteReactiveRestController {

    @Autowired
    private SocieteRepository societeRepository;


    @GetMapping(value = "/societes")
    public Flux<Societe> findAll() {
        return societeRepository.findAll();
    }

    @GetMapping(value = "/societe/{id}")
    public Mono<Societe> getOne(@PathVariable String id) {
        return societeRepository.findById(id);
    }

    @PostMapping(value = "/societe")
    public Mono<Societe> save(@RequestBody Societe societe) {
        return societeRepository.save(societe);
    }

    @DeleteMapping(value = "/societe/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return societeRepository.deleteById(id);
    }

    @PutMapping(value = "/societe/{id}")
    public Mono<Societe> update(@PathVariable String id, @RequestBody Societe societe) {
        societe.setId(id);
        return societeRepository.save(societe);
    }

}
