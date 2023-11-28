package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "image_upload")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "direct_request_id", nullable = false)
    private DirectRequest directRequest;

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
