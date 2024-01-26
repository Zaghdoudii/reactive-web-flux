package com.med.reactivewebflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String id;
    private Instant instant;
    private double price;
    @DBRef
    private Societe societe;
}
