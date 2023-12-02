package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency_type")
public class CurrencyType {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column(name = "symbol", length = 4, nullable = false)
   private String symbol;
   @Column(name="state",columnDefinition = "CHAR(1) NOT NULL DEFAULT '1'")
   private char state;

   public CurrencyType(String symbol){
      this.symbol=symbol;
   }

}
