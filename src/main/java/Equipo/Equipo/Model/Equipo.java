package Equipo.Equipo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoMaquina;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private String estado;

    @Column(name = "establecimiento_id", nullable = false)
    private Long establecimientoId;
}