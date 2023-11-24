package com.springtec.models.entity;

import com.springtec.models.dto.ProfessionAvailabilityDto;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profession_availability")
public class ProfessionAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "technical_id")
    private Technical technical;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "availability_id")
    private  Availability availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "experience_id")
    private Experience experience;

    public ProfessionAvailability(Technical technical, ProfessionAvailabilityDto professionAvailabilityDto){
        this.id = professionAvailabilityDto.getId();
        this.technical = technical;
        this.profession = Profession.builder()
            .id(professionAvailabilityDto.getProfessionId())
            .build();
        this.availability = Availability.builder()
            .id(professionAvailabilityDto.getAvailabilityId())
            .build();
        this.experience = Experience.builder()
            .id(professionAvailabilityDto.getExperienceId())
            .build();
    }

}
