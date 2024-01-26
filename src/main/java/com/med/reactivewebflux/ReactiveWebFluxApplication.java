package com.med.reactivewebflux;

import com.med.reactivewebflux.dao.SocieteRepository;
import com.med.reactivewebflux.dao.TransactionRepository;
import com.med.reactivewebflux.entity.Societe;
import com.med.reactivewebflux.entity.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebFluxApplication.class, args);
    }

    @Bean
    CommandLineRunner start(SocieteRepository societeRepository, TransactionRepository transactionRepository) {
        return args -> {
            societeRepository.deleteAll().subscribe(null, null, () -> {

                Stream.of("SG", "AXA", "BNK", "CO").forEach(s -> {
                    societeRepository.save(new Societe(s, s, 100 + Math.random() * 900))
                            .subscribe(soc -> {
                                System.out.println(soc.toString());
                            });
                });
                transactionRepository.deleteAll().subscribe(null, null, () -> {
                    Stream.of("SG", "AXA", "BNK", "CO").forEach(s -> {
                        societeRepository.findById(s).subscribe(soc -> {
                            for (int i = 0; i < 10; i++) {
                                Transaction transaction = new Transaction();
                                transaction.setInstant(Instant.now());
                                transaction.setSociete(soc);
                                transaction.setPrice(soc.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
                                transactionRepository.save(transaction).subscribe(t -> {
                                    System.out.println(t.toString());
                                });
                            }
                        });
                    });

                    System.out.println("***********************");
                });
            });
        };

    }

}
