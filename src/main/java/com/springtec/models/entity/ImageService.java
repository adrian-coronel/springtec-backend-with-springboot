package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageService {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "services_id", nullable = false)
    private Services service;


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
