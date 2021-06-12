package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Film title
    private String name;

    // Ticket price
    private double price;

    // Poster
    private String image;

    // General description
    @Column(length = 65535)
    private String description;

    // Release year
    private int year;

    // Age rating
    private int pg;

    // Film's director
    private String director;

    //Type of film
    @OneToOne
    @JoinColumn(name = "prodyct_type_id")
    private ProductType productType;
}
