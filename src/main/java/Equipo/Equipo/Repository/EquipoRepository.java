package Equipo.Equipo.Repository;

import Equipo.Equipo.Model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByEstablecimientoId(Long establecimientoId);

    @Query("SELECT e.tipoMaquina, COUNT(e) FROM Equipo e WHERE e.establecimientoId = :establecimientoId GROUP BY e.tipoMaquina")
    List<Object[]> contarPorTipoYEstablecimiento(@Param("establecimientoId") Long establecimientoId);

}
