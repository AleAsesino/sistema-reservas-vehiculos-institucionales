# Diagramas del Sistema de Reservas de Vehículos Institucionales

## Diagrama de Clases

```mermaid
classDiagram
    class Usuario {
        -Long id
        -String nombre
        -String email
        -String password
        -String rol
        +getId() Long
        +getNombre() String
        +getEmail() String
        +getRol() String
    }

    class Vehiculo {
        -Long id
        -String marca
        -String modelo
        -String placa
        -String estado
        -Boolean disponible
        +getId() Long
        +getMarca() String
        +getModelo() String
        +getPlaca() String
        +isDisponible() Boolean
        +setDisponible(Boolean)
    }

    class Reserva {
        -Long id
        -Long usuarioId
        -Long vehiculoId
        -LocalDateTime fechaInicio
        -LocalDateTime fechaFin
        -String estado
        +getId() Long
        +getUsuarioId() Long
        +getVehiculoId() Long
        +getFechaInicio() LocalDateTime
        +getFechaFin() LocalDateTime
        +getEstado() String
        +setEstado(String)
    }

    class ReservaDTO {
        -Long id
        -Long usuarioId
        -Long vehiculoId
        -LocalDateTime fechaInicio
        -LocalDateTime fechaFin
        -String estado
    }

    class ReservaService {
        <<interface>>
        +findAll() List~ReservaDTO~
        +findById(Long) Optional~ReservaDTO~
        +findByUsuarioId(Long) List~ReservaDTO~
        +findByVehiculoId(Long) List~ReservaDTO~
        +findActiveReservas() List~ReservaDTO~
        +save(ReservaDTO) ReservaDTO
        +update(Long, ReservaDTO) ReservaDTO
        +delete(Long) void
        +isVehiculoAvailable(Long, LocalDateTime, LocalDateTime) boolean
        +areAllVehiculosAvailable(List~Long~, LocalDateTime, LocalDateTime) boolean
    }

    class ReservaRepository {
        <<interface>>
        +findAll() List~Reserva~
        +findById(Long) Optional~Reserva~
        +save(Reserva) Reserva
        +deleteById(Long) void
    }

    class ReservaController {
        -ReservaService reservaService
        +getAllReservas() List~ReservaDTO~
        +getReservaById(Long) ReservaDTO
        +createReserva(ReservaDTO) ReservaDTO
        +updateReserva(Long, ReservaDTO) ReservaDTO
        +deleteReserva(Long) void
    }

    Usuario "1" -- "*" Reserva : realiza
    Vehiculo "1" -- "*" Reserva : tiene
    ReservaController --> ReservaService : usa
    ReservaService --> ReservaRepository : usa
    Reserva <--> ReservaDTO : se convierte
```

Este diagrama de clases muestra las principales entidades del sistema y sus relaciones:

1. **Entidades Principales:**
   - `Usuario`: Representa a los usuarios del sistema
   - `Vehiculo`: Representa los vehículos disponibles para reserva
   - `Reserva`: Representa una reserva de vehículo

2. **DTOs y Servicios:**
   - `ReservaDTO`: Objeto de transferencia de datos para las reservas
   - `ReservaService`: Interface que define las operaciones de negocio
   - `ReservaRepository`: Interface para el acceso a datos

3. **Controladores:**
   - `ReservaController`: Gestiona las peticiones HTTP relacionadas con reservas

4. **Relaciones:**
   - Un Usuario puede tener múltiples Reservas
   - Un Vehículo puede tener múltiples Reservas
   - El Controller usa el Service
   - El Service usa el Repository
   - Las Reservas se convierten a/desde DTOs

Este diagrama proporciona una vista clara de la arquitectura en capas del sistema y cómo se relacionan los diferentes componentes entre sí.

## Diagrama de Casos de Uso

```mermaid
graph TB
    subgraph Actores
        Usuario((Usuario))
        Administrador((Administrador))
    end

    subgraph Sistema de Reservas de Vehículos
        Login[Iniciar Sesión]
        ConsultarVehiculos[Consultar Vehículos Disponibles]
        CrearReserva[Crear Reserva]
        ConsultarReservas[Consultar Mis Reservas]
        CancelarReserva[Cancelar Reserva]
        ModificarReserva[Modificar Reserva]
        
        GestionarUsuarios[Gestionar Usuarios]
        GestionarVehiculos[Gestionar Vehículos]
        VerTodasReservas[Ver Todas las Reservas]
        GenerarReportes[Generar Reportes]
        GestionarMantenimiento[Gestionar Mantenimiento]
        ConfigurarSistema[Configurar Sistema]
    end

    %% Relaciones Usuario Normal
    Usuario --> Login
    Usuario --> ConsultarVehiculos
    Usuario --> CrearReserva
    Usuario --> ConsultarReservas
    Usuario --> CancelarReserva
    Usuario --> ModificarReserva

    %% Relaciones Administrador
    Administrador --> Login
    Administrador --> GestionarUsuarios
    Administrador --> GestionarVehiculos
    Administrador --> VerTodasReservas
    Administrador --> GenerarReportes
    Administrador --> GestionarMantenimiento
    Administrador --> ConfigurarSistema

    %% Extensiones y relaciones
    ConsultarVehiculos --> CrearReserva
    CrearReserva --> ConsultarReservas
```

### Descripción de Casos de Uso

#### Usuario Regular
1. **Iniciar Sesión**
   - Autenticarse en el sistema
   - Mantener sesión activa
   - Cerrar sesión

2. **Consultar Vehículos Disponibles**
   - Ver lista de vehículos
   - Filtrar por fecha
   - Ver detalles de vehículos
   - Verificar disponibilidad

3. **Crear Reserva**
   - Seleccionar vehículo
   - Elegir fecha y hora de inicio/fin
   - Confirmar reserva
   - Recibir confirmación

4. **Consultar Mis Reservas**
   - Ver reservas activas
   - Ver historial de reservas
   - Ver detalles de cada reserva

5. **Cancelar Reserva**
   - Seleccionar reserva activa
   - Confirmar cancelación
   - Recibir confirmación

6. **Modificar Reserva**
   - Cambiar fecha/hora
   - Cambiar vehículo
   - Actualizar detalles

#### Administrador
1. **Gestionar Usuarios**
   - Crear usuarios
   - Modificar usuarios
   - Desactivar usuarios
   - Asignar roles

2. **Gestionar Vehículos**
   - Agregar vehículos
   - Modificar información
   - Dar de baja vehículos
   - Actualizar estado

3. **Ver Todas las Reservas**
   - Consultar reservas actuales
   - Ver historial completo
   - Filtrar por criterios
   - Exportar datos

4. **Generar Reportes**
   - Reportes de uso
   - Estadísticas
   - Reportes de mantenimiento
   - Exportar informes

5. **Gestionar Mantenimiento**
   - Programar mantenimiento
   - Registrar servicios
   - Actualizar estado
   - Ver histórico

6. **Configurar Sistema**
   - Ajustar parámetros
   - Gestionar permisos
   - Configurar notificaciones
   - Administrar catálogos
