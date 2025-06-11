package com.reservas.vehiculos.institucionales.service.impl;

import com.reservas.vehiculos.institucionales.dto.AuthDTO;
import com.reservas.vehiculos.institucionales.dto.UsuarioDTO;
import com.reservas.vehiculos.institucionales.mapper.UsuarioMapper;
import com.reservas.vehiculos.institucionales.model.Rol;
import com.reservas.vehiculos.institucionales.model.Usuario;
import com.reservas.vehiculos.institucionales.repository.RolRepository;
import com.reservas.vehiculos.institucionales.repository.UsuarioRepository;
import com.reservas.vehiculos.institucionales.security.jwt.JwtUtils;
import com.reservas.vehiculos.institucionales.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RolRepository rolRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;



    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            AuthenticationManager authenticationManager,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.rolRepository = rolRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<UsuarioDTO.UsuariosLista> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toUsuarioLista)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO.UsuarioMostrarPerfil obtenerUsuarioDatos(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toUsuarioMostrarPerfil(usuario);
    }

    @Override
    @CacheEvict(value = { "usuarioPerfil", "usuariosLista" }, key = "#id", allEntries = true)
    public void DarBajaUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstadoCuenta(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @CacheEvict(value = "usuariosLista", allEntries = true)
    public UsuarioDTO.UsuarioCrearDTO crearUsuario(UsuarioDTO.UsuarioCrearDTO usuarioDTO) {
        if (usuarioRepository.existsByUsuario(usuarioDTO.getUsuario())) {
            throw new IllegalArgumentException("Usuario existente, ingrese otro.");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        // Encripta la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        Set<Rol> roles = new HashSet<>();

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            Rol usuarioRol = rolRepository.findByNombre(Rol.NombreRol.ROL_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: No se encontró el rol"));
            roles.add(usuarioRol);
        }
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);

        return usuarioDTO; // Devuelve los mismos datos que se recibieron (sin incluir ID generado ni fecha, etc.)
    }

    @Override
    @CacheEvict(value = { "usuarioPerfil", "usuariosLista" }, key = "#usuarioDTO.id", allEntries = true)
    public UsuarioDTO.UsuarioModificarDTO modificarUsuario(UsuarioDTO.UsuarioModificarDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidoPaterno(usuarioDTO.getApellidoPaterno());
        usuario.setApellidoMaterno(usuarioDTO.getApellidoMaterno());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCiudad(usuarioDTO.getCiudad());
        usuario.setGenero(usuarioDTO.getGenero());
        usuario.setUrlImg(usuarioDTO.getUrlImg());
        usuario.setFechaUltimaModificacion(LocalDateTime.now());
        return usuarioMapper.toUsuarioModificar(usuarioRepository.save(usuario));
    }

    @Override
    public AuthDTO.JwtResponse devolverToken(AuthDTO.LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        Usuario usuario = usuarioRepository.findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado."));
        return new AuthDTO.JwtResponse(jwt,
                usuario.getId(),
                userDetails.getUsername(),
                usuario.getEmail(),
                new HashSet<>(roles));
    }

    @Override
    public void asignarRolUsuario(Long id_usuario, String rolAsignado){
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        if(usuario.getRoles().isEmpty()){
            Set<Rol> roles = new HashSet<>();
            usuario.setRoles(roles);
        }
        if(rolAsignado.equals("administrador")){
            Rol usuarioRol = rolRepository.findByNombre(Rol.NombreRol.ROL_ADMIN)
                    .orElseThrow(()-> new RuntimeException("Error: No se encontro el rol"));
            usuario.getRoles().add(usuarioRol);
        } else if (rolAsignado.equals("inspector")) {
            Rol usuarioRol = rolRepository.findByNombre(Rol.NombreRol.ROL_INSPECTOR)
                    .orElseThrow(()-> new RuntimeException("Error: No se encontro el rol"));
            usuario.getRoles().add(usuarioRol);
        } else{
            Rol usuarioRol = rolRepository.findByNombre(Rol.NombreRol.ROL_USUARIO)
                    .orElseThrow(()-> new RuntimeException("Error: No se encontro el rol"));
            usuario.getRoles().add(usuarioRol);
        }
        usuarioRepository.save(usuario);


    }
}
