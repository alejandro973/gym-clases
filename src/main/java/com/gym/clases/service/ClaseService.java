package com.gym.clases.service;

import com.gym.clases.repository.ClaseRepository;
import com.gym.clases.dto.*;
import com.gym.clases.model.Clase;
import com.gym.clases.client.EntrenadorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional; 

@Service
@Transactional
@RequiredArgsConstructor
public class ClaseService {

    private final ClaseRepository claseRepository;
    private final EntrenadorClient entrenadorClient;

    private ClaseResponseDto mapToDto(Clase c) {
        String nombreE = "Entrenador no asignado";
        String especialidadE = "N/A";

        try {
          
            EntrenadorDto entrenadorExterno = entrenadorClient.obtenerEntrenadorPorRun(c.getRunEntrenador());
            
            if (entrenadorExterno != null) {
                nombreE = entrenadorExterno.getNombre();
                especialidadE = entrenadorExterno.getEspecialidad();
            }
        } catch (Exception e) {
            System.err.println("Error consultando entrenadores: " + e.getMessage());
        }

        return new ClaseResponseDto(
            c.getId(),
            c.getNombreClase(),
            c.getHorario(),
            c.getCuposMaximos(),
            c.getRunEntrenador(), 
            nombreE,              
            especialidadE         
        );
    }


    public List<ClaseResponseDto> obtenerTodas() {
        return claseRepository.findAll().stream()
            .map(this::mapToDto) // Transforma cada Clase en un ClaseResponseDto enriquecido
            .collect(Collectors.toList());
    }

    public Optional<ClaseResponseDto> obtenerPorId(Long id) {
        return claseRepository.findById(id).map(this::mapToDto);
    }


    public ClaseResponseDto guardarClase(ClaseRequestDto dto) {
        Clase c = new Clase(
            null, 
            dto.getNombreClase(), 
            dto.getHorario(), 
            dto.getCuposMaximos(), 
            dto.getRunEntrenador()
        );
        return mapToDto(claseRepository.save(c));
    }

    public Optional<ClaseResponseDto> actualizarClase(Long id, ClaseRequestDto dto) {
        return claseRepository.findById(id).map(existente -> {
            existente.setNombreClase(dto.getNombreClase());
            existente.setHorario(dto.getHorario());
            existente.setCuposMaximos(dto.getCuposMaximos());
            existente.setRunEntrenador(dto.getRunEntrenador());
            
            return mapToDto(claseRepository.save(existente));
        });
    }

  
    public void eliminarClase(Long id) {
        if (claseRepository.existsById(id)) {
            claseRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar: La clase con ID " + id + " no existe.");
        }
    }


    public List<ClaseResponseDto> buscarPorHorario(String horario) {
        return claseRepository.findAll().stream()
            .map(this::mapToDto)
            .filter(dto -> dto.getHorario().equalsIgnoreCase(horario))
            .collect(Collectors.toList());
    }
}