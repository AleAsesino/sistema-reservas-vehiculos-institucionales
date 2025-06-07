package com.reservas.vehiculos.institucionales.service;
import com.reservas.vehiculos.institucionales.model.Vehiculo;
import com.reservas.vehiculos.institucionales.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;


    @Cacheable("vehiculos")
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    @CacheEvict(value = "vehiculos", allEntries = true)
    public Vehiculo saveVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.findByPlaca(vehiculo.getPlaca()).isPresent()) {
            throw new RuntimeException("La placa ya existe");
        }
        return vehiculoRepository.save(vehiculo);
    }

    @CacheEvict(value = "vehiculos", allEntries = true)
    public Vehiculo updateVehiculo(Long id, Vehiculo vehiculoDetalles) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con id " + id));

        if (!vehiculo.getPlaca().equals(vehiculoDetalles.getPlaca()) &&
            vehiculoRepository.findByPlaca(vehiculoDetalles.getPlaca()).isPresent()) {
            throw new RuntimeException("La placa ya existe");
        }

        vehiculo.setPlaca(vehiculoDetalles.getPlaca());
        vehiculo.setMarca(vehiculoDetalles.getMarca());
        vehiculo.setTipo(vehiculoDetalles.getTipo());
        vehiculo.setColor(vehiculoDetalles.getColor());
        vehiculo.setCapacidad(vehiculoDetalles.getCapacidad());
        vehiculo.setEstado(vehiculoDetalles.getEstado());
        vehiculo.setDescripcion(vehiculoDetalles.getDescripcion());
        vehiculo.setCantAsientos(vehiculoDetalles.getCantAsientos());
        vehiculo.setFechaRegistro(vehiculoDetalles.getFechaRegistro());

        return vehiculoRepository.save(vehiculo);
    }

    @CacheEvict(value = "vehiculos", allEntries = true)
    public void deleteVehiculo(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con id " + id));
        vehiculoRepository.delete(vehiculo);
    }
}
