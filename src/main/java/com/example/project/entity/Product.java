package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name="product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private String image;

    //Общее описание
    @Column(length = 65535)
    private String description;

    //Год выпуска
    private int year;

    //Возрастной рейтинг
    private int pg;

    //Режиссер
    private String director;

    @OneToOne
    @JoinColumn(name = "prodyct_type_id")
    private ProductType productType;
}
