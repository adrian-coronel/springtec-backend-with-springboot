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
@Table(name = "img_firebase")
public class ImgFirebase {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column(name = "direct_request_id")
   private Integer directRequestId;
   private String url;

   public ImgFirebase(Integer directRequestId, String url) {
      this.directRequestId = directRequestId;
      this.url = url;
   }
}
