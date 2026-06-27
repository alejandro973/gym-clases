package com.gym.clases.config; // Ajustado al entorno de tu VS Code

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Le indica a Spring que aplique este ajuste al arrancar el contexto de Clases
public class SwaggerConfig {

    @Bean // Registra el objeto OpenAPI administrado por el framework
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gym API 2026 - Microservicio de Clases") // Título modular
                        .version("1.0") // Versión del artefacto de software
                        .description("Documentación oficial de endpoints REST para la programación de horarios, asignación de salas, control de cupos y oferta de disciplinas académicas dirigidas."));
    }
}