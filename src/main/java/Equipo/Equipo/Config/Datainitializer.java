package Equipo.Equipo.Config;

import Equipo.Equipo.Model.Equipo;
import Equipo.Equipo.Repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class Datainitializer implements CommandLineRunner {

    private final EquipoRepository equipoRepository;

    @Override
    public void run(String... args) {
        if (equipoRepository.count() > 0) {
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        // Equipos del establecimiento 1
        equipoRepository.save(new Equipo(null, "Cinta de Correr", "Life Fitness", LocalDate.of(2022, 3, 15), "Operativo", 1L));
        equipoRepository.save(new Equipo(null, "Bicicleta Estática", "Technogym", LocalDate.of(2021, 6, 20), "Operativo", 1L));
        equipoRepository.save(new Equipo(null, "Elíptica", "Precor", LocalDate.of(2020, 1, 10), "En Mantención", 1L));
        equipoRepository.save(new Equipo(null, "Press de Banca", "Hammer Strength", LocalDate.of(2019, 8, 5), "Operativo", 1L));
        equipoRepository.save(new Equipo(null, "Máquina de Remo", "Concept2", LocalDate.of(2023, 2, 28), "Operativo", 1L));

        // Equipos del establecimiento 2
        equipoRepository.save(new Equipo(null, "Cinta de Correr", "Technogym", LocalDate.of(2021, 4, 12), "Operativo", 2L));
        equipoRepository.save(new Equipo(null, "Pesas Rusas", "Eleiko", LocalDate.of(2020, 9, 3), "Operativo", 2L));
        equipoRepository.save(new Equipo(null, "Máquina Multipower", "BH Fitness", LocalDate.of(2022, 7, 18), "Fuera de Servicio", 2L));
        equipoRepository.save(new Equipo(null, "Bicicleta Spinning", "Keiser", LocalDate.of(2023, 1, 5), "Operativo", 2L));
        equipoRepository.save(new Equipo(null, "TRX", "TRX Training", LocalDate.of(2021, 11, 22), "Operativo", 2L));

        log.info(">>> DataInitializer: {} equipos insertados correctamente.", equipoRepository.count());
    }
}