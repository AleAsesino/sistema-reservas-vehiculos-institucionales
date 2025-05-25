# Documentación Técnica - Sistema de Reservas de Vehículos Institucionales

## Especificaciones Técnicas

### Requisitos del Sistema
- Java 11 o superior
- Spring Boot 2.x
- Maven como gestor de dependencias
- Base de datos relacional (MySQL/PostgreSQL)
- IDE recomendado: IntelliJ IDEA o Spring Tool Suite

### Dependencias Principales
```xml
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-validation
```

## Arquitectura Detallada

### Capa de Servicio

#### ReservaService
Interface que define las operaciones principales para la gestión de reservas:

```java
public interface ReservaService {
    // Operaciones CRUD básicas
    List<ReservaDTO> findAll();                    // Obtener todas las reservas
    Optional<ReservaDTO> findById(Long id);        // Buscar reserva por ID
    ReservaDTO save(ReservaDTO reservaDTO);        // Crear nueva reserva
    ReservaDTO update(Long id, ReservaDTO reservaDTO); // Actualizar reserva
    void delete(Long id);                          // Eliminar reserva

    // Operaciones de búsqueda específicas
    List<ReservaDTO> findByUsuarioId(Long usuarioId);    // Reservas por usuario
    List<ReservaDTO> findByVehiculoId(Long vehiculoId);  // Reservas por vehículo
    List<ReservaDTO> findActiveReservas();               // Reservas activas

    // Verificación de disponibilidad
    boolean isVehiculoAvailable(Long vehiculoId, LocalDateTime startDate, LocalDateTime endDate);
    boolean areAllVehiculosAvailable(List<Long> vehiculosIds, LocalDateTime startDate, LocalDateTime endDate);
}
```

### Estructura de Datos

#### ReservaDTO
```java
public class ReservaDTO {
    private Long id;
    private Long usuarioId;
    private Long vehiculoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    // getters y setters
}
```

## Flujos de Datos

### Proceso de Reserva
1. **Verificación de Disponibilidad**
   ```plaintext
   Cliente -> Controller -> ReservaService.isVehiculoAvailable() -> Repository
   ```

2. **Creación de Reserva**
   ```plaintext
   Cliente -> Controller -> ReservaService.save() -> Repository -> Base de Datos
   ```

## Seguridad

### Autenticación y Autorización
- Implementación de Spring Security
- JWT para tokens de autenticación
- Roles y permisos definidos

## Manejo de Errores

### Excepciones Personalizadas
```java
- ReservaNotFoundException
- VehiculoNoDisponibleException
- UsuarioNoAutorizadoException
```

## Pruebas

### Estructura de Pruebas
```plaintext
src/test/java/
├── unit/
│   ├── service/
│   └── controller/
└── integration/
    └── repository/
```

## Configuración y Despliegue

### Perfiles de Spring
- desarrollo
- pruebas
- producción

### Variables de Entorno
```plaintext
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/reservas_db
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=contraseña
```

## Monitoreo y Logs

### Estructura de Logs
```plaintext
- access.log    # Registros de acceso
- error.log     # Errores y excepciones
- audit.log     # Acciones de usuarios
```

## API REST

### Endpoints Principales
```plaintext
GET    /api/reservas
POST   /api/reservas
PUT    /api/reservas/{id}
DELETE /api/reservas/{id}
GET    /api/reservas/usuario/{usuarioId}
GET    /api/reservas/vehiculo/{vehiculoId}
GET    /api/reservas/activas
```

## Rendimiento

### Optimizaciones
- Caché de segundo nivel con Hibernate
- Índices en la base de datos
- Pool de conexiones
