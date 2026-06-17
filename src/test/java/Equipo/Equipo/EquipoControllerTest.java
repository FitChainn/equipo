package Equipo.Equipo;

import Equipo.Equipo.Controller.EquipoController;
import Equipo.Equipo.Dto.EquipoRequestDTO;
import Equipo.Equipo.Dto.EquipoResponseDTO;
import Equipo.Equipo.Service.EquipoService;
import Equipo.Equipo.Config.SecurityConfig;
import Equipo.Equipo.Filter.RolHeaderFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EquipoController.class)
@Import({SecurityConfig.class, RolHeaderFilter.class})
public class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipoService equipoService;

    @Autowired
    private ObjectMapper objectMapper;

    private EquipoResponseDTO eResponse;
    private EquipoRequestDTO eRequest;

    @BeforeEach
    void setUp() {
        eResponse = new EquipoResponseDTO(1L, "TROTADORA", "LIFE FITNESS", LocalDate.of(2024, 1, 1), "OPERATIVO", 1L);
        eRequest = new EquipoRequestDTO("TROTADORA", "LIFE FITNESS", LocalDate.of(2024, 1, 1), "OPERATIVO", 1L);
    }

    @Test
    void Get_obtenerTodos() throws Exception {
        when(equipoService.obtenerTodos()).thenReturn(List.of(eResponse));

        mockMvc.perform(get("/v1/equipos")
                        .header("X-User-Rol", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].tipoMaquina").value("TROTADORA"));
    }

    @Test
    void Post_guardar201() throws Exception {
        when(equipoService.guardar(any(EquipoRequestDTO.class))).thenReturn(eResponse);

        mockMvc.perform(post("/v1/equipos")
                        .header("X-User-Rol", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void Get_obtenerPorId() throws Exception {
        when(equipoService.obtenerPorId(1L)).thenReturn(Optional.of(eResponse));

        mockMvc.perform(get("/v1/equipos/1")
                        .header("X-User-Rol", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoMaquina").value("TROTADORA"));
    }

    @Test
    void Put_actualizar() throws Exception {
        when(equipoService.actualizar(eq(1L), any(EquipoRequestDTO.class))).thenReturn(eResponse);

        mockMvc.perform(put("/v1/equipos/1")
                        .header("X-User-Rol", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoMaquina").value("TROTADORA"));
    }

    @Test
    void Delete_eliminar() throws Exception {
        when(equipoService.obtenerPorId(1L)).thenReturn(Optional.of(eResponse));

        mockMvc.perform(delete("/v1/equipos/1")
                        .header("X-User-Rol", "ADMIN"))
                .andExpect(status().isNoContent());
    }
}
