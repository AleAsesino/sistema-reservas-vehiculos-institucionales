package com.reservas.vehiculos.institucionales.service;


import com.reservas.vehiculos.institucionales.model.Vehiculo;
import com.reservas.vehiculos.institucionales.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
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
        return vehiculoRepository.save(vehiculo);
    }

    // Puedes agregar actualizar y eliminar con @CacheEvict también
}

