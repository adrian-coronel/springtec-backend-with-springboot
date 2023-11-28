package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_services_id")
    private CategoryService categoryService;
    @ManyToOne
    @JoinColumn(name = "currency_type_id")
    private CurrencyType currencyType;
    private String name;
    private String description;
    private double price;
    @Column(name="state",columnDefinition = "CHAR(1) NOT NULL DEFAULT '1'")
    private char state;


}
