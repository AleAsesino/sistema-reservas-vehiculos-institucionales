package com.reservas.vehiculos.institucionales.controller;


import com.reservas.vehiculos.institucionales.model.Vehiculo;
import com.reservas.vehiculos.institucionales.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // Listar todos los vehículos - accesible para ADMIN e INSPECTOR
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSPECTOR')")
    public List<Vehiculo> getVehiculos() {
        return vehiculoService.getAllVehiculos();
    }

    // Crear un nuevo vehículo - solo ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Vehiculo saveVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.saveVehiculo(vehiculo);
    }

    // Actualizar un vehículo - solo ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Vehiculo updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoDetalles) {
        return vehiculoService.updateVehiculo(id, vehiculoDetalles);
    }

    // Eliminar un vehículo - solo ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVehiculo(@PathVariable Long id) {
        vehiculoService.deleteVehiculo(id);
    }
}
