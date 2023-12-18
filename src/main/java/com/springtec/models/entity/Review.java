package com.springtec.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "review")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @ManyToOne
   @JoinColumn(name = "technical_id")
   private Technical technical;
   @ManyToOne
   @JoinColumn(name = "client_id")
   private Client client;
   @Column(name = "number_stars")
   private Integer numberStars;
   private String title;
   private String comment;
   private Date date;
   private Time hour;

}
