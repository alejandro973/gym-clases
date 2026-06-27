package com.gym.clases.controller;

import com.gym.clases.dto.ClaseRequestDto;
import com.gym.clases.dto.ClaseResponseDto;
import com.gym.clases.service.ClaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Imports oficiales exigidos por la especificación OpenAPI 3
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
@RequiredArgsConstructor
@Tag(name = "Clase", description = "Endpoints REST para la gestión, programación y asignación de bloques horarios y entrenadores a las clases dirigidas")
public class ClaseController {

    private final ClaseService claseService;

    // 1. Listar todas las clases
    @GetMapping
    @Operation(summary = "Listar todas las clases", description = "Retorna un listado completo con la planificación de clases vigentes en la cadena de gimnasios.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa. Devuelve la lista de clases encontradas."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la consulta.")
    })
    public ResponseEntity<List<ClaseResponseDto>> listarClases() {
        List<ClaseResponseDto> clases = claseService.obtenerTodas();
        return ResponseEntity.ok(clases);
    }

    // 2. Obtener una clase por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una clase por su ID", description = "Permite recuperar los detalles específicos de una clase programada mediante su identificador único numérico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clase encontrada de forma exitosa."),
        @ApiResponse(responseCode = "404", description = "El identificador (ID) proporcionado no coincide con ningún registro en la base de datos.")
    })
    public ResponseEntity<ClaseResponseDto> buscarPorId(
        @Parameter(description = "Identificador único de la clase (ID numérico)", example = "1", required = true)
        @PathVariable Long id
    ) {
        return claseService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear una nueva clase
    @PostMapping
    @Operation(summary = "Registrar una nueva clase", description = "Permite dar de alta una nueva clase dirigida dentro del sistema, validando los datos del DTO de entrada de acuerdo a las reglas del negocio.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Clase creada e incorporada al catálogo correctamente."),
        @ApiResponse(responseCode = "400", description = "La solicitud es inválida (Bad Request). Faltan campos obligatorios o el DTO no cumple con las validaciones.")
    })
    public ResponseEntity<ClaseResponseDto> crearClase(@Valid @RequestBody ClaseRequestDto dto) {
        ClaseResponseDto nuevaClase = claseService.guardarClase(dto);
        return new ResponseEntity<>(nuevaClase, HttpStatus.CREATED);
    }

    // 4. Actualizar una clase existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una clase por ID", description = "Modifica integralmente los atributos de una clase programada basándose en su ID y la estructura del cuerpo de la petición.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clase modificada y guardada con éxito."),
        @ApiResponse(responseCode = "400", description = "Estructura del JSON incorrecta o falló la validación del DTO."),
        @ApiResponse(responseCode = "404", description = "No se encontró el registro con el ID especificado para aplicar la actualización.")
    })
    public ResponseEntity<ClaseResponseDto> actualizar(
        @Parameter(description = "ID de la clase a modificar", example = "1", required = true)
        @PathVariable Long id, 
        @Valid @RequestBody ClaseRequestDto dto
    ) {
        return claseService.actualizarClase(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar una clase por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una clase por ID", description = "Da de baja física o lógicamente una clase del ecosistema del gimnasio utilizando su clave primaria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "244", description = "Clase removida con éxito de la base de datos (No Content)."),
        @ApiResponse(responseCode = "404", description = "Operación fallida. El ID suministrado no corresponde a ninguna clase registrada.")
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID de la clase que se desea remover del sistema", example = "3", required = true)
        @PathVariable Long id
    ) {
        try {
            claseService.eliminarClase(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Filtrar por horario
    @GetMapping("/buscar")
    @Operation(summary = "Filtrar clases por criterio horario", description = "Filtra y retorna un listado de las clases que coinciden exactamente con la cadena de texto de la agenda.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta procesada correctamente. Retorna las coincidencias encontradas o una lista vacía.")
    })
    public ResponseEntity<List<ClaseResponseDto>> buscarPorHorario(
        @Parameter(description = "Formato de texto para el bloque de tiempo (Ej: Lunes 10:00)", example = "Lunes 10:00", required = true)
        @RequestParam String horario
    ) {
        List<ClaseResponseDto> filtradas = claseService.buscarPorHorario(horario);
        return ResponseEntity.ok(filtradas);
    }
}