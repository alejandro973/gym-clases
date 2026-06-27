package com.gym.clases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaseRequestDto {

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombreClase;

    @NotBlank(message = "El horario es obligatorio")
    private String horario;

    @NotNull(message = "Los cupos máximos no pueden estar vacíos")
    private Integer cuposMaximos;

    @NotBlank(message = "Debe asignar un RUN de entrenador")
    private String runEntrenador;
}