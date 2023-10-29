package com.springtec.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechnicalProfessionDto {

    private Integer professionId;
    private Integer experienceId;

}
