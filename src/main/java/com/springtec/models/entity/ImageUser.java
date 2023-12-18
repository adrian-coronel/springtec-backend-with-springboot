package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageUser {



   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(name = "user_id", nullable = false)
   private Integer userId;

   @Column(name = "original_name", nullable = false)
   private String originalName;


   @Column(name = "extension_name", nullable = false)
   private String extensionName;

   @Column(name = "content_type", nullable = false)
   private String contentType;

   @Column(name = "fake_name", nullable = false)
   private String fakeName;

   @Column(name = "fake_extension_name", nullable = false)
   private String fakeExtensionName;
}
