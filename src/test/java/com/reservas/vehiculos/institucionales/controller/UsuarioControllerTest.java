package com.reservas.vehiculos.institucionales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservas.vehiculos.institucionales.dto.AuthDTO;
import com.reservas.vehiculos.institucionales.dto.UsuarioDTO;
import com.reservas.vehiculos.institucionales.service.UsuarioService; // Solo necesitas mockear este servicio ahora
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // ¡Nueva importación!
import org.springframework.boot.test.context.SpringBootTest; // ¡Cambio importante!
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // Sigue siendo útil para otros tests
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// --- ¡CAMBIO DE ANOTACIONES AQUÍ! ---
@SpringBootTest // Carga el contexto completo de Spring Boot
@AutoConfigureMockMvc // Configura MockMvc para pruebas de controladores
// Ya NO necesitas @Import(SecurityConfig.class) ni mockear JwtAuthenticationEntryPoint, etc.
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Solo necesitas mockear el servicio que tu controlador usa y quieres simular
    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    // --- ¡Elimina estos @MockBean! Spring Boot Test los cargará automáticamente. ---
    // @MockBean
    // private JwtAuthenticationEntryPoint unauthorizedHandler;
    // @MockBean
    // private JwtAuthenticationFilter jwtAuthenticationFilter;
    // @MockBean
    // private UserDetailsServiceImpl userDetailsService;
    // @MockBean
    // private PasswordEncoder passwordEncoder;
    // ---------------------------------------------------------------------------------


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void obtenerTodosLosUsuarios() throws Exception {
        UsuarioDTO.UsuariosLista user = new UsuarioDTO.UsuariosLista();
        user.setNombre("Juan");

        Mockito.when(usuarioService.obtenerTodosLosUsuarios())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    @WithMockUser(roles = {"USUARIO"}) // Asegúrate de que este rol tenga permiso para crear usuarios
    void crearUsuario() throws Exception {
        UsuarioDTO.UsuarioCrearDTO crearDTO = new UsuarioDTO.UsuarioCrearDTO();
        crearDTO.setNombre("Maria");

        Mockito.when(usuarioService.crearUsuario(Mockito.any()))
                .thenReturn(crearDTO);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearDTO))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Maria"));
    }

    @Test
    void probarIniciarSesion() throws Exception {
        AuthDTO.LoginRequest login = new AuthDTO.LoginRequest();
        login.setUsername("usuario");
        login.setPassword("password");

        AuthDTO.JwtResponse token = new AuthDTO.JwtResponse(
                "fake-jwt-token",
                1L,
                "usuario",
                "usuario@example.com",
                Set.of("ROLE_USUARIO")
        );

        Mockito.when(usuarioService.devolverToken(Mockito.any()))
                .thenReturn(token);

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void asignarRolUsuario() throws Exception {
        mockMvc.perform(put("/api/usuarios/asignarRole/1/INSPECTOR"))
                .andExpect(status().isOk());

        Mockito.verify(usuarioService).asignarRolUsuario(1L, "INSPECTOR");
    }
}