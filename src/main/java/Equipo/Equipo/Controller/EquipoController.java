package Equipo.Equipo.Controller;

import Equipo.Equipo.Dto.EquipoRequestDTO;
import Equipo.Equipo.Dto.EquipoResponseDTO;
import Equipo.Equipo.Service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(equipoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return equipoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/establecimiento/{establecimientoId}")
    public ResponseEntity<List<EquipoResponseDTO>> obtenerPorEstablecimiento(
            @PathVariable Long establecimientoId) {
        List<EquipoResponseDTO> equipos = equipoService.obtenerPorEstablecimiento(establecimientoId);
        if (equipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipos);
    }

    @GetMapping("/establecimiento/{establecimientoId}/total")
    public ResponseEntity<Map<String, Long>> contarPorEstablecimiento(
            @PathVariable Long establecimientoId) {
        long total = equipoService.contarPorEstablecimiento(establecimientoId);
        return ResponseEntity.ok(Map.of("totalMaquinas", total));
    }

    @PostMapping
    public ResponseEntity<EquipoResponseDTO> guardar(@Valid @RequestBody EquipoRequestDTO dto) {
        return ResponseEntity.status(201).body(equipoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequestDTO dto) {
        return ResponseEntity.ok(equipoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (equipoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/establecimiento/{establecimientoId}/resumen")
    public ResponseEntity<Map<String, Long>> resumenPorTipo(@PathVariable Long establecimientoId) {
        return ResponseEntity.ok(equipoService.contarPorTipoEnEstablecimiento(establecimientoId));
    }
}