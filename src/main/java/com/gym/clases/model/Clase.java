package com.gym.clases.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // AGREGADO
import lombok.*;

@Entity
@Table(name = "clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String nombreClase;

    @NotBlank
    @Column(length = 20, nullable = false) 
    private String horario; 

    @NotNull 
    @Column( nullable = false) 
    private Integer cuposMaximos;

    @NotBlank
    @Column( length = 13, nullable = false)
    private String runEntrenador;
}