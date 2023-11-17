package com.springtec.models.dto;

import com.springtec.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSupportDTO {
    private Integer id;

    private Integer userId ;

    private String issue;

    private String description;

    private Date date;

    private char state;
}
