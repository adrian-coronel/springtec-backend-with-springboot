package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "direct_request_id", nullable = false)
    private DirectRequest directRequest;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "extension_name", nullable = false)
    private String extensionName;

    @Column(name = "fake_name", nullable = false)
    private String fakeName;

    @Column(name = "fake_extension_name", nullable = false)
    private String fakeExtensionName;
}
