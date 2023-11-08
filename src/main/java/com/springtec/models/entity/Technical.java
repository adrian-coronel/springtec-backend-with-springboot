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
    private String name;
    @Column(name = "father_lastname")
    private String lastname;
    @Column(name = "mother_lastname")
    private String motherLastname;
    private String dni;
    private String latitude;
    private String longitude;
    @Column(name = "birth_date")
    private Date birthDate;

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
        name = "technical_has_profession", // nombre de la tabla de union
        // se establece la columna de clave foránea en la tabla "technical_profession" que se asocia con la entidad Technical
        joinColumns = @JoinColumn(name = "technical_id"),
        inverseJoinColumns = @JoinColumn(name = "profession_id")) // El campo que cojerá de la entidad contraría
    private Set<Profession> professions;

    @ManyToOne // Muchos usuarios pueden tener UNA disponibilidad
    @JoinColumn(name = "availability_id")
    private Availability availability;

}
