package Equipo.Equipo.Controller;

import Equipo.Equipo.Dto.EquipoRequestDTO;
import Equipo.Equipo.Dto.EquipoResponseDTO;
import Equipo.Equipo.Service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "EQUIPOS", description = "GESTIÓN DE EQUIPOS")
@RestController
@RequestMapping("/v1/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @Operation(summary = "OBTENER TODOS LOS EQUIPOS", description = "Retorna la lista de todos los equipos. Acceso: ADMIN, ENTRENADOR, CLIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LISTA OBTENIDA CON ÉXITO"),
            @ApiResponse(responseCode = "403", description = "SIN PERMISOS SUFICIENTES")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'CLIENTE')")
    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(equipoService.obtenerTodos());
    }

    @Operation(summary = "OBTENER EQUIPO POR ID", description = "Retorna un equipo específico por su ID. Acceso: ADMIN, ENTRENADOR, CLIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "EQUIPO ENCONTRADO"),
            @ApiResponse(responseCode = "404", description = "EQUIPO NO ENCONTRADO")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return equipoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "OBTENER EQUIPOS POR ESTABLECIMIENTO", description = "Retorna todos los equipos de un establecimiento. Acceso: ADMIN, ENTRENADOR, CLIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LISTA OBTENIDA CON ÉXITO"),
            @ApiResponse(responseCode = "204", description = "NO HAY EQUIPOS PARA ESE ESTABLECIMIENTO")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'CLIENTE')")
    @GetMapping("/establecimiento/{establecimientoId}")
    public ResponseEntity<List<EquipoResponseDTO>> obtenerPorEstablecimiento(
            @PathVariable Long establecimientoId) {
        List<EquipoResponseDTO> equipos = equipoService.obtenerPorEstablecimiento(establecimientoId);
        if (equipos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(equipos);
    }

    @Operation(summary = "CONTAR EQUIPOS POR ESTABLECIMIENTO", description = "Retorna el total de equipos de un establecimiento. Acceso: ADMIN, ENTRENADOR, CLIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "TOTAL OBTENIDO CON ÉXITO"),
            @ApiResponse(responseCode = "404", description = "ESTABLECIMIENTO NO ENCONTRADO")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'CLIENTE')")
    @GetMapping("/establecimiento/{establecimientoId}/total")
    public ResponseEntity<Map<String, Long>> contarPorEstablecimiento(
            @PathVariable Long establecimientoId) {
        long total = equipoService.contarPorEstablecimiento(establecimientoId);
        return ResponseEntity.ok(Map.of("totalMaquinas", total));
    }

    @Operation(summary = "RESUMEN DE EQUIPOS POR TIPO", description = "Retorna un resumen de equipos agrupados por tipo en un establecimiento. Acceso: ADMIN, ENTRENADOR, CLIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "RESUMEN OBTENIDO CON ÉXITO"),
            @ApiResponse(responseCode = "404", description = "ESTABLECIMIENTO NO ENCONTRADO")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'CLIENTE')")
    @GetMapping("/establecimiento/{establecimientoId}/resumen")
    public ResponseEntity<Map<String, Long>> resumenPorTipo(@PathVariable Long establecimientoId) {
        return ResponseEntity.ok(equipoService.contarPorTipoEnEstablecimiento(establecimientoId));
    }

    @Operation(summary = "CREAR EQUIPO", description = "Registra un nuevo equipo. Acceso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "EQUIPO CREADO EXITOSAMENTE"),
            @ApiResponse(responseCode = "400", description = "DATOS INVÁLIDOS"),
            @ApiResponse(responseCode = "404", description = "ESTABLECIMIENTO NO ENCONTRADO"),
            @ApiResponse(responseCode = "503", description = "MICROSERVICIO NO DISPONIBLE")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EquipoResponseDTO> guardar(@Valid @RequestBody EquipoRequestDTO dto) {
        return ResponseEntity.status(201).body(equipoService.guardar(dto));
    }

    @Operation(summary = "ACTUALIZAR EQUIPO", description = "Actualiza un equipo existente. Acceso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "EQUIPO ACTUALIZADO EXITOSAMENTE"),
            @ApiResponse(responseCode = "400", description = "DATOS INVÁLIDOS"),
            @ApiResponse(responseCode = "404", description = "EQUIPO NO ENCONTRADO")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequestDTO dto) {
        return ResponseEntity.ok(equipoService.actualizar(id, dto));
    }

    @Operation(summary = "ELIMINAR EQUIPO", description = "Elimina un equipo por su ID. Acceso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "EQUIPO ELIMINADO EXITOSAMENTE"),
            @ApiResponse(responseCode = "404", description = "EQUIPO NO ENCONTRADO")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (equipoService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        equipoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}