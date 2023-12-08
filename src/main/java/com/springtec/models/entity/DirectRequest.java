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
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "profession_availability_id")
   private ProfessionAvailability professionAvailability;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "client_id")
   private Client client;
   @ManyToOne
   @JoinColumn(name = "service_type_availability_id", nullable = true)
   private ServiceTypeAvailability serviceTypeAvailability;
   @ManyToOne
   @JoinColumn(name = "state_direct_request_id")
   private StateDirectRequest stateDirectRequest;
   @ManyToOne
   @JoinColumn(name = "category_services_id")
   private CategoryService categoryService;
   @Column(name="state_invoice",columnDefinition = " CHAR(1) NULL DEFAULT '0'")
   private char stateInvoice = '0';
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   @Column(name = "created_at",insertable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
   private Timestamp createdAt;
   private Timestamp answeredAt;
   private Timestamp resolvedAt;


}
