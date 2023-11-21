package com.springtec.models.dto;

import com.springtec.models.entity.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyTypeDto {

   private Integer id;
   private String symbol;
   private char state;

   public  CurrencyTypeDto(CurrencyType currencyType){
      this.id = currencyType.getId();
      this.symbol = currencyType.getSymbol();
      this.state =currencyType.getState();

   }
}