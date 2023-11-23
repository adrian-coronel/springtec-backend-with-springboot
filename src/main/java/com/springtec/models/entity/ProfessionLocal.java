package com.springtec.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "profession_local")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessionLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "profession_availability_id")
    private ProfessionAvailability professionAvailability;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lng")
    private Double longitude;

}
