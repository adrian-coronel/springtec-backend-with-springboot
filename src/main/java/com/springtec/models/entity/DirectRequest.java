package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
   @JoinColumn(name = "technical_id")
   private Technical technical;
   @ManyToOne
   @JoinColumn(name = "client_id")
   private Client client;
   @ManyToOne
   @JoinColumn(name = "service_type_availability_id", nullable = false)
   private ServiceTypeAvailability serviceTypeAvailability;
   @ManyToOne
   @JoinColumn(name = "state_direct_request_id")
   private StateDirectRequest stateDirectRequest;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   @Column(name = "created_at",insertable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
   private Timestamp created_at;
   private Timestamp answered_at;
   private Timestamp resolved_at;


}
