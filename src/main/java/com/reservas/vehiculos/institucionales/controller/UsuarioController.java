package com.reservas.vehiculos.institucionales.controller;


import com.reservas.vehiculos.institucionales.dto.AuthDTO;
import com.reservas.vehiculos.institucionales.dto.UsuarioDTO;
import com.reservas.vehiculos.institucionales.model.Usuario;
import com.reservas.vehiculos.institucionales.service.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'INSPECTOR')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO.UsuariosLista>> obtenerTodosLosUsuarios(){
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'INSPECTOR', 'USUARIO')")
    @GetMapping("/info/{id}")
    public ResponseEntity<UsuarioDTO.UsuarioMostrarPerfil> obtenerInformacionPersonalDeUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerUsuarioDatos(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
    @PostMapping
    public ResponseEntity<UsuarioDTO.UsuarioCrearDTO> crearUsuario(@RequestBody UsuarioDTO.UsuarioCrearDTO usuarioDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(usuarioDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
    @PutMapping
    public ResponseEntity<UsuarioDTO.UsuarioModificarDTO> modificarUsuario(@RequestBody UsuarioDTO.UsuarioModificarDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.modificarUsuario(usuarioDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
    @DeleteMapping
    public ResponseEntity<?> darBajaUsuario(@PathVariable Long id){
        usuarioService.DarBajaUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<AuthDTO.JwtResponse> iniciarSesion(@Valid @RequestBody AuthDTO.LoginRequest loginRequest){
        return ResponseEntity.ok(usuarioService.devolverToken(loginRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/asignarRole/{id_usuario}/{rol}")
    public ResponseEntity<?> asignarRoleUsuario(@Valid @PathVariable Long id_usuario, @PathVariable String rol){
        usuarioService.asignarRolUsuario(id_usuario,rol);
        return ResponseEntity.ok().build();
    }

}
