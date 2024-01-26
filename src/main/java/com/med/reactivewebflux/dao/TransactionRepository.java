package com.med.reactivewebflux.dao;

import com.med.reactivewebflux.entity.Societe;
import com.med.reactivewebflux.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    public Flux<Transaction> findBySocieteId(Societe societe);
}
