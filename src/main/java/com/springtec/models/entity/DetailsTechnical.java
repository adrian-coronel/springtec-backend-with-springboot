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
@Table(name = "details_technical")
public class DetailsTechnical {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @ManyToOne
   @JoinColumn(name = "technical_id", nullable = false)
   private Technical technical;
   @ManyToOne
   @JoinColumn(name = "availability_id", nullable = false)
   private Availability availability;
   @ManyToOne
   @JoinColumn(name = "profession_id", nullable = false)
   private Profession profession;
   @ManyToOne
   @JoinColumn(name = "experience_id", nullable = false)
   private Experience experience;
   private Double latitude;
   private Double longitude;
   @Column(name="state",columnDefinition = "CHAR(1) NOT NULL DEFAULT '1'")
   private char state;

   public DetailsTechnical(Technical technical, Availability availability, Profession profession, Experience experience, Double latitude, Double longitude, char state) {
      this.technical = technical;
      this.availability = availability;
      this.profession = profession;
      this.experience = experience;
      this.latitude = latitude;
      this.longitude = longitude;
      this.state = state;
   }
}
