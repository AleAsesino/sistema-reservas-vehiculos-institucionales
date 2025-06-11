package com.reservas.vehiculos.institucionales.mapper;

import com.reservas.vehiculos.institucionales.dto.UsuarioDTO;
import com.reservas.vehiculos.institucionales.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO.UsuarioMostrarPerfil toUsuarioMostrarPerfil(Usuario usuario);

    UsuarioDTO.UsuariosLista toUsuarioLista(Usuario usuario);


    //@Mapping(target = "estadoCuenta", constant = "true")
    //@Mapping(target = "reservas", ignore = true)
    //@Mapping(target = "roles", ignore = true)


    @Mapping(target = "estadoCuenta", ignore = true) // 'estadoCuenta' se setea en el servicio
    @Mapping(target = "reservas", ignore = true)     // 'reservas' no viene en UsuarioCrearDTO
    @Mapping(target = "roles", ignore = true)        // 'roles' se setea en el servicio
    @Mapping(target = "fechaRegistro", ignore = true) // 'fechaRegistro' se setea en el servicio
    @Mapping(target = "fechaUltimaModificacion", ignore = true) // No viene en crear, se setea en modificar
    @Mapping(target = "id", ignore = true)           // ID es autogenerado
    @Mapping(target = "password", ignore = true)
    Usuario toEntity(UsuarioDTO.UsuarioCrearDTO usuarioDTO);

    UsuarioDTO.UsuarioCrearDTO toUsuarioCrear(Usuario usuario);
    UsuarioDTO.UsuarioModificarDTO toUsuarioModificar(Usuario usuario);
}
