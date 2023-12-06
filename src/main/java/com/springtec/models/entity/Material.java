package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "material")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Material {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column(name = "invoice_id")
   private Integer invoice;
   private String name;
   private Double price;
   private Integer stock;
   @Column(name="state",columnDefinition = "CHAR(1) NOT NULL DEFAULT '1'")
   private char state = '1';

}
