package com.pie.kart.product.Model;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "product")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String title;
    @NonNull
    private ProductType productType;
    private double price;
    @Nullable
    @JsonIgnore
    private int quantity;
    private String description;
    private String specification;
}