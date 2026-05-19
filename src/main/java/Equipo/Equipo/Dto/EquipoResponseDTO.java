package Equipo.Equipo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoResponseDTO {
    private Long id;
    private String tipoMaquina;
    private String marca;
    private LocalDate fechaCompra;
    private String estado;
    private Long establecimientoId;
}
