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
@Table(name = "direct_request")
public class DirectRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @ManyToOne
   @JoinColumn(name = "profession_availability_id")
   private ProfessionAvailability professionAvailability;
   @ManyToOne
   @JoinColumn(name = "client_id")
   private Client client;


   @ManyToOne
   @JoinColumn(name = "service_type_availability_id", nullable = true)
   private ServiceTypeAvailability serviceTypeAvailability;


   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   @Column(name="state",columnDefinition = "CHAR(1) NOT NULL DEFAULT '1'")
   private char state;

}
