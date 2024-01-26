package com.med.reactivewebflux.web;

import com.med.reactivewebflux.dao.SocieteRepository;
import com.med.reactivewebflux.dao.TransactionRepository;
import com.med.reactivewebflux.entity.Societe;
import com.med.reactivewebflux.entity.Transaction;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class TransactionReactiveRestController {
    private final TransactionRepository transactionRepository;
    private final SocieteRepository societeRepository;

    public TransactionReactiveRestController(TransactionRepository transactionRepository, SocieteRepository societeRepository) {
        this.transactionRepository = transactionRepository;
        this.societeRepository = societeRepository;
    }


    @GetMapping(value = "/transactions")
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @GetMapping(value = "/transaction/{id}")
    public Mono<Transaction> getOne(@PathVariable String id) {
        return transactionRepository.findById(id);
    }

    @PostMapping(value = "/transaction")
    public Mono<Transaction> save(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @DeleteMapping(value = "/transaction/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return transactionRepository.deleteById(id);
    }

    @PutMapping(value = "/transaction/{id}")
    public Mono<Transaction> update(@PathVariable String id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @GetMapping(value = "/streamTransactions", produces = "text/event-stream")
    public Flux<Transaction> streamTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping(value = "/transactionBySociete/{id}")
    public Flux<Transaction> getTransactionBySociete(@PathVariable String id) {
        Societe societe = new Societe();
        societe.setId(id);
        return transactionRepository.findBySocieteId(societe);
    }

    @GetMapping(value = "/streamTransactionBySociete/{id}", produces = "application/json")
    public Flux<Transaction> stream(@PathVariable String id) {
        return societeRepository.findById(id).flatMapMany(soc -> {
            Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
            Flux<Transaction> transactionFlux = Flux.fromStream(Stream.generate(() -> {
                Transaction transaction = new Transaction();
                transaction.setSociete(soc);
                transaction.setPrice(soc.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
                return transaction;
            }));
            return Flux.zip(interval, transactionFlux).map(data -> {
                return data.getT2();
            });

        });
    }


}
