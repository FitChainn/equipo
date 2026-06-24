package Equipo.Equipo.Assembler;

import Equipo.Equipo.Controller.EquipoController;
import Equipo.Equipo.Dto.EquipoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EquipoModelAssembler implements RepresentationModelAssembler<EquipoResponseDTO, EntityModel<EquipoResponseDTO>> {
    @Override
    public EntityModel<EquipoResponseDTO> toModel(EquipoResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(EquipoController.class).obtenerPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(EquipoController.class).eliminar(dto.getId())).withRel("delete"),
                linkTo(methodOn(EquipoController.class).actualizar(dto.getId(), null)).withRel("update")
        );
    }
}
