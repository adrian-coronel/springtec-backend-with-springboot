package com.springtec.models.payload;

import lombok.Builder;
import lombok.Data;

/**
 * PAYLOAD se refiere a los datos del cuerpo de una solicitud o respuesta.
 * */
@Data
@Builder
public class MessageResponse {

    private String message;
    private Object body;

}
