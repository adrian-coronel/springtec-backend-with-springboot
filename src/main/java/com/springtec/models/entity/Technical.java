package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "technical")
public class Technical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "father_lastname")
    private String lastname;
    @Column(name = "mother_lastname")
    private String motherLastname;
    private String dni;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name="working_status",columnDefinition = "CHAR(1) NOT NULL DEFAULT '0'")
    private char workingStatus = '0';

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * mappedBy para indicar que es una relación bidireccional, es decir,
     * la Entidad DetailsTechnical tendrá también una relación hacia esta entidad
     * */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "technical")
    private List<DetailsTechnical> detailsTechnicals;

}
