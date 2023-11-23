package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profession_availability")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessionAvailability {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @ManyToOne
    @JoinColumn(name = "technical_id")
    private Technical technical;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private  Availability availability;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

}
