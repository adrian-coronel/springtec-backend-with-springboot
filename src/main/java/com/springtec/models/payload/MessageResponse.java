package com.springtec.models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PAYLOAD se refiere a los datos del cuerpo de una solicitud o respuesta.
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private String message;
    private Object body;

}
