package com.springtec.models.dto;

import com.springtec.models.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class TicketSupportDTO {
    private Integer id;

    private Integer userId ;

    private String issue;

    private String description;

    private Date date;

    private char state;
}
