package com.reservas.vehiculos.institucionales.mapper;

import com.reservas.vehiculos.institucionales.dto.UsuarioDTO;
import com.reservas.vehiculos.institucionales.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO.UsuarioMostrarPerfil toUsuarioMostrarPerfil(Usuario usuario);

    UsuarioDTO.UsuariosLista toUsuarioLista(Usuario usuario);



    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "fechaUltimaModificacion", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "estadoCuenta", constant = "true")
    @Mapping(target = "reservas", ignore = true) // opcional si no quieres inicializar
    @Mapping(target = "roles", ignore = true) // los estás configurando manualmente
    Usuario toEntity(UsuarioDTO.UsuarioCrearDTO usuarioDTO);

    UsuarioDTO.UsuarioCrearDTO toUsuarioCrear(Usuario usuario);
    UsuarioDTO.UsuarioModificarDTO toUsuarioModificar(Usuario usuario);
}

