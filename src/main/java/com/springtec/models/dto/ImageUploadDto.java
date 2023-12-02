package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fileName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contentType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private byte[] file;

}