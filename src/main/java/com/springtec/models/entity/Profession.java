package com.springtec.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profession")
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToMany(
            mappedBy = "professions" // nombre del campo que asigna la relacion
    )
    private Set<Technical> technicals;
    private String  name;

    @Column(columnDefinition = "CHAR(1) NOT NULL DEFAULT '1'")
    private char state = '1';

    public Profession(String name) {
        this.name = name;
    }

}
