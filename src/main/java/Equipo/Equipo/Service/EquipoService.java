package Equipo.Equipo.Service;

import Equipo.Equipo.Dto.EquipoRequestDTO;
import Equipo.Equipo.Dto.EquipoResponseDTO;
import Equipo.Equipo.Model.Equipo;
import Equipo.Equipo.Repository.EquipoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    private EquipoResponseDTO mapToDTO(Equipo equipo) {
        return new EquipoResponseDTO(
                equipo.getId(),
                equipo.getTipoMaquina(),
                equipo.getMarca(),
                equipo.getFechaCompra(),
                equipo.getEstado(),
                equipo.getEstablecimientoId()
        );
    }

    public List<EquipoResponseDTO> obtenerTodos() {
        log.info("OBTENIENDO TODOS LOS EQUIPOS");
        return equipoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EquipoResponseDTO> obtenerPorId(Long id) {
        log.info("BUSCANDO EQUIPO CON ID: {}", id);
        return equipoRepository.findById(id).map(this::mapToDTO);
    }

    public List<EquipoResponseDTO> obtenerPorEstablecimiento(Long establecimientoId) {
        log.info("BUSCANDO EQUIPOS DEL ESTABLECIMIENTO ID: {}", establecimientoId);
        return equipoRepository.findByEstablecimientoId(establecimientoId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EquipoResponseDTO guardar(EquipoRequestDTO dto) {
        log.info("GUARDANDO EQUIPO: {} DE MARCA: {}", dto.getTipoMaquina(), dto.getMarca());
        Equipo equipo = new Equipo();
        equipo.setTipoMaquina(dto.getTipoMaquina());
        equipo.setMarca(dto.getMarca());
        equipo.setFechaCompra(dto.getFechaCompra());
        equipo.setEstado(dto.getEstado());
        equipo.setEstablecimientoId(dto.getEstablecimientoId());
        Equipo guardado = equipoRepository.save(equipo);
        log.info("EQUIPO GUARDADO CON ID: {}", guardado.getId());
        return mapToDTO(guardado);
    }

    public EquipoResponseDTO actualizar(Long id, EquipoRequestDTO dto) {
        log.info("ACTUALIZANDO EQUIPO CON ID: {}", id);
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        equipo.setTipoMaquina(dto.getTipoMaquina());
        equipo.setMarca(dto.getMarca());
        equipo.setFechaCompra(dto.getFechaCompra());
        equipo.setEstado(dto.getEstado());
        equipo.setEstablecimientoId(dto.getEstablecimientoId());
        return mapToDTO(equipoRepository.save(equipo));
    }

    public void eliminarPorId(Long id) {
        log.info("ELIMINANDO EQUIPO CON ID: {}", id);
        equipoRepository.deleteById(id);
    }

    public long contarPorEstablecimiento(Long establecimientoId) {
        return equipoRepository.findByEstablecimientoId(establecimientoId).size();
    }
    public Map<String, Long> contarPorTipoEnEstablecimiento(Long establecimientoId) {
        log.info("CONTANDO EQUIPOS POR TIPO EN ESTABLECIMIENTO ID: {}", establecimientoId);
        List<Object[]> resultados = equipoRepository.contarPorTipoYEstablecimiento(establecimientoId);
        Map<String, Long> resumen = new LinkedHashMap<>();
        for (Object[] fila : resultados) {
            resumen.put((String) fila[0], (Long) fila[1]);
        }
        return resumen;
    }
}