package com.springtec.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    /*
    * Ventajas de utilizar SET:
    * Eliminación de duplicados
    * Eficiencia en la búsqueda
    * Rendimiento en operaciones de conjunto
    * */
    @ManyToMany
    @JoinTable(
            name = "technical_profession", // nombre de la tabla de union
            joinColumns = @JoinColumn(name = "technical_id"),// Especifica el campo que cojerá de nuestra entidad
            inverseJoinColumns = @JoinColumn(name = "profession_id")) // El campo que cojerá de la entidad contraría
    private Set<Profession> professions;

    @ManyToOne // Muchos usuarios pueden tener UNA disponibilidad
    @JoinColumn(name = "availability_id", unique = false)
    private Availability availability;
    private String name;
    private String lastname;
    private String motherLastname;
    private String dni;
    private String latitude;
    private String longitude;
    private Date birthDate;

}
