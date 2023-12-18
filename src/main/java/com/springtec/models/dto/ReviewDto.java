package com.springtec.models.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

   private Integer id;
   @NotNull(message = "technicalId no puede ser nulo")
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer technicalId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private TechnicalDto technical;
   @NotNull(message = "clientId no puede ser nulo")
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer clientId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ClientDto client;
   @NotNull(message = "La cantidad de estrellas no puede ser nula")
   private Integer numberStars;
   @NotBlank(message = "El titulo no puede estar vacio")
   private String title;
   @NotBlank(message = "El comentario no puede estar vacio")
   private String comment;
   private Date date;
   private Time hour;

   public ReviewDto(Review review) {
      this.id = review.getId();
      this.technical = new TechnicalDto(review.getTechnical());
      this.client = new ClientDto(review.getClient());
      this.numberStars = review.getNumberStars();
      this.title = review.getTitle();
      this.comment = review.getComment();
      this.date = review.getDate();
      this.hour = review.getHour();
   }

}
