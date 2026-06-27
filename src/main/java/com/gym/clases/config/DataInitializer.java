package com.gym.clases.config;

import com.gym.clases.model.Clase;
import com.gym.clases.repository.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClaseRepository claseRepository;

    @Override
    public void run(String... args) throws Exception {
        if (claseRepository.count() == 0) {
            System.out.println("--> Cargando clases de prueba iniciales emparejadas con entrenadores...");

            // Clase 1: Usamos los métodos exactos de tu entidad
            Clase c1 = new Clase();
            c1.setNombreClase("Yoga Principiantes");
            c1.setHorario("08:30 - 09:30");
            c1.setCuposMaximos(15);
            c1.setRunEntrenador("11111111-1"); // Pon un RUN que exista en tu micro de entrenadores
            claseRepository.save(c1);

            // Clase 2: Segunda clase de prueba
            Clase c2 = new Clase();
            c2.setNombreClase("Crossfit Avanzado");
            c2.setHorario("19:00 - 20:00");
            c2.setCuposMaximos(25);
            c2.setRunEntrenador("22222222-2"); // Otro RUN de tus entrenadores de prueba
            claseRepository.save(c2);

            System.out.println("--> ¡Clases iniciales cargadas con éxito en Laragon!");
        } else {
            System.out.println("--> La tabla clases ya contiene registros, omitiendo inicialización.");
        }
    }
}