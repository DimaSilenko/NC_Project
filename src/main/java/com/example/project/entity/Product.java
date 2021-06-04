package com.example.project.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name="product")
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

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPg() {
        return pg;
    }

    public void setPg(Integer pg) {
        this.pg = pg;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
