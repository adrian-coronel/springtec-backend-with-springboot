package com.springtec.models.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profession_local")
public class ProfessionLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "profession_availability_id", nullable = false)
    private ProfessionAvailability professionAvailability;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lng")
    private Double longitude;

}
