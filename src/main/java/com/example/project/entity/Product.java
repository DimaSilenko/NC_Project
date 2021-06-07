package com.example.project.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name="product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[a-z A-Z0-9-:,.]{1,100}", message = "Please, use English words and special symbols like : , . -")
    @NotBlank(message = "Title cannot be empty")
    private String name;

    @Min(value = 50, message = "Lowest price is 50.0")
    private double price;

    private String image;

    //Общее описание
    @Column(length = 65535)
    @Type(type = "text")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    //Год выпуска
    @Min(value = 1927, message = "First film was created in 1927")
    @Max(value = 2021, message = "Now is a 2021")
    private int year;

    //Возрастной рейтинг
    @Min(0)
    @Max(21)
    private int pg;

    //Режиссер
    @Pattern(regexp = "[a-z A-Z,-]{1,100}", message = "Please, use English words and special symbols like , -")
    @NotBlank(message = "Director's name cannot be empty")
    private String director;

    @OneToOne
    @JoinColumn(name = "prodyct_type_id")
    private ProductType productType;
}
