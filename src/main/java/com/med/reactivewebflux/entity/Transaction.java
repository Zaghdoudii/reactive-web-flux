package com.med.reactivewebflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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
    @DocumentReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Societe societe;
}
