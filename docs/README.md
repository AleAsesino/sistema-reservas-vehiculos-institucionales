# Sistema de Reservas de Vehículos Institucionales

## Descripción General del Sistema
El Sistema de Reservas de Vehículos Institucionales es una aplicación web desarrollada con Spring Boot que permite gestionar de manera eficiente la flota de vehículos de una institución. Este sistema facilita el proceso de reserva, seguimiento y administración de vehículos institucionales.

### Propósito
El propósito principal del sistema es proporcionar una plataforma centralizada donde los usuarios institucionales puedan:
- Realizar reservas de vehículos
- Consultar disponibilidad en tiempo real
- Gestionar el estado de las reservas
- Administrar la flota de vehículos
- Generar reportes de uso y disponibilidad

## Arquitectura del Sistema

### Tecnologías Utilizadas
- Backend: Spring Boot
- Base de Datos: [Por definir - MySQL/PostgreSQL]
- Frontend: [Por definir - Angular/React/Thymeleaf]

### Componentes Principales
1. **Módulo de Reservas**
   - Gestión de solicitudes de reserva
   - Verificación de disponibilidad
   - Confirmación y cancelación de reservas

2. **Módulo de Vehículos**
   - Registro y actualización de vehículos
   - Control de estado y mantenimiento
   - Gestión de disponibilidad

3. **Módulo de Usuarios**
   - Gestión de perfiles
   - Control de acceso y autenticación
   - Roles y permisos

4. **Módulo de Reportes**
   - Generación de informes
   - Estadísticas de uso
   - Histórico de reservas

## Estructura del Proyecto

### Paquetes Principales
```
com.reservas.vehiculos.institucionales
├── config/         # Configuraciones de Spring
├── controller/     # Controladores REST
├── dto/            # Objetos de transferencia de datos
├── model/          # Entidades
├── repository/     # Repositorios JPA
├── service/        # Lógica de negocio
└── util/           # Utilidades
```

## Funcionalidades Principales

### Gestión de Reservas
- Crear nuevas reservas
- Consultar reservas existentes
- Actualizar estado de reservas
- Cancelar reservas
- Verificar disponibilidad de vehículos

### Gestión de Vehículos
- Registro de nuevos vehículos
- Actualización de estado
- Control de mantenimiento
- Gestión de disponibilidad

### Gestión de Usuarios
- Registro de usuarios
- Autenticación y autorización
- Gestión de roles
- Perfiles de usuario

## Flujos de Trabajo

### Proceso de Reserva
1. Usuario inicia sesión en el sistema
2. Consulta disponibilidad de vehículos
3. Selecciona fecha y hora de reserva
4. Sistema verifica disponibilidad
5. Confirma la reserva
6. Notifica al usuario

### Proceso de Devolución
1. Usuario reporta devolución
2. Sistema actualiza estado
3. Registro de kilometraje y estado
4. Actualización de disponibilidad

## Próximos Pasos
- [ ] Completar diagramas UML
- [ ] Documentar API REST
- [ ] Crear manual de usuario
- [ ] Documentar proceso de deployment

## Contribución
[Instrucciones para contribuir al proyecto]

## Licencia
[Información sobre la licencia del proyecto]
