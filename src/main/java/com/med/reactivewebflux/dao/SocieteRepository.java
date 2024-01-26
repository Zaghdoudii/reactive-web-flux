package com.med.reactivewebflux.dao;

import com.med.reactivewebflux.entity.Societe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SocieteRepository extends ReactiveMongoRepository<Societe, String> {
}
