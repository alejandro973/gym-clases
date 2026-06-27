package com.gym.clases;

import com.gym.clases.dto.ClaseRequestDto;
import com.gym.clases.dto.ClaseResponseDto;
import com.gym.clases.dto.EntrenadorDto; 
import com.gym.clases.client.EntrenadorClient; 
import com.gym.clases.model.Clase;
import com.gym.clases.repository.ClaseRepository;
import com.gym.clases.service.ClaseService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ClaseServiceTest {

    @Autowired
    private ClaseService claseService;

    @MockBean
    private ClaseRepository claseRepository;

    @MockBean
    private EntrenadorClient entrenadorClient;

    
    @Test
    public void testObtenerTodas() {
      
        Clase claseFake = new Clase(1L, "Spinning", "09:00", 25, "12345678-9");
        when(claseRepository.findAll()).thenReturn(List.of(claseFake));

       
        EntrenadorDto entrenadorFake = new EntrenadorDto();
        entrenadorFake.setNombre("Carlos Silva");
        entrenadorFake.setEspecialidad("Cardio / HIIT");

       
        when(entrenadorClient.obtenerEntrenadorPorRun("12345678-9")).thenReturn(entrenadorFake);

    
        List<ClaseResponseDto> resultado = claseService.obtenerTodas();

        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos Silva", resultado.get(0).getNombre());
    }

 
    @Test
    public void testObtenerPorId() {
        Long id = 1L;
        Clase claseFake = new Clase(id, "Spinning", "09:00", 25, "12345678-9");
        when(claseRepository.findById(id)).thenReturn(Optional.of(claseFake));

        EntrenadorDto entrenadorFake = new EntrenadorDto();
        entrenadorFake.setNombre("Carlos Silva");
        entrenadorFake.setEspecialidad("Cardio / HIIT");
        when(entrenadorClient.obtenerEntrenadorPorRun("12345678-9")).thenReturn(entrenadorFake);

        Optional<ClaseResponseDto> found = claseService.obtenerPorId(id);

        assertTrue(found.isPresent());
        assertEquals("Spinning", found.get().getNombreClase());
    }

   
    @Test
    public void testEliminarClase() {
        Long id = 1L;

        when(claseRepository.existsById(id)).thenReturn(true);
        doNothing().when(claseRepository).deleteById(id);

 
        claseService.eliminarClase(id);

        verify(claseRepository, times(1)).deleteById(id);
    }
}