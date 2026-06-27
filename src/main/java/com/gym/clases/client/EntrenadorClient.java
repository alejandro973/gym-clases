package com.gym.clases.client;
import com.gym.clases.dto.EntrenadorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entrenadores", url = "http://localhost:8082")
public interface EntrenadorClient {

@GetMapping("/api/entrenadores/buscar/{run}")
    EntrenadorDto obtenerEntrenadorPorRun(@PathVariable("run") String run);

}
