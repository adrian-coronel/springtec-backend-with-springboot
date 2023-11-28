package com.springtec.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="service_type_availability")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceTypeAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @ManyToOne
    @JoinColumn(name = "services_id")
    private Services services;

    @ManyToOne
    @JoinColumn(name = "profession_availability_id")
    private ProfessionAvailability professionAvailability;

}
