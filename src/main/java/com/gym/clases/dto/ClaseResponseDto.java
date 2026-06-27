package com.gym.clases.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaseResponseDto {
    private Long id;
    private String nombreClase;
    private String horario;
    private Integer cuposMaximos;
    private String runEntrenador;
    
 
    private String nombre;
    private String especialidad;
}