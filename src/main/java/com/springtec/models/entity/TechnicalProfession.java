package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "technical_profession")
public class TechnicalProfession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "technical_id")
    private Technical technical;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;

    // Otros campos y métodos según sea necesario
}


