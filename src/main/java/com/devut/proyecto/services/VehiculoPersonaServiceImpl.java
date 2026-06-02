package com.devut.proyecto.services;

import com.devut.proyecto.entities.Persona; 
import com.devut.proyecto.entities.Vehiculo;
import com.devut.proyecto.entities.VehiculoPersona;
import com.devut.proyecto.entities.VehiculoPersonaId;
import com.devut.proyecto.repository.PersonaRepository;
import com.devut.proyecto.repository.VehiculoPersonaRepository;
import com.devut.proyecto.repository.VehiculoRepository;
import com.devut.proyecto.services.interfaces.IVehiculoPersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VehiculoPersonaServiceImpl implements IVehiculoPersonaService {

    @Autowired
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public VehiculoPersona asociarConductor(Integer vehiculoId, Integer personaId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException(
                        "Vehículo no encontrado con id: " + vehiculoId));

        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new RuntimeException(
                        "Persona no encontrada con id: " + personaId));

        // Validar que sea conductor
        if (!persona.getTipoPersona().equals("C")) {
            throw new IllegalArgumentException(
                    "Solo se pueden asociar personas de tipo CONDUCTOR (C) a un vehículo.");
        }

        // Validar que no exista ya la relación
        VehiculoPersona existente = vehiculoPersonaRepository
                .findByVehiculoIdAndPersonaId(vehiculoId, personaId);
        if (existente != null) {
            throw new IllegalArgumentException(
                    "El conductor ya está asociado a este vehículo.");
        }

        VehiculoPersona vp = new VehiculoPersona();
        vp.setId(new VehiculoPersonaId(vehiculoId, personaId));
        vp.setVehiculo(vehiculo);
        vp.setPersona(persona);
        vp.setFechaAsociacion(LocalDate.now());
        vp.setEstado("EA");

        return vehiculoPersonaRepository.save(vp);
    }

    @Override
    public VehiculoPersona cambiarEstado(Integer vehiculoId, Integer personaId, String nuevoEstado) {
        nuevoEstado = nuevoEstado.replace("\"", "").trim();


        if (!nuevoEstado.equals("PO") && !nuevoEstado.equals("EA") && !nuevoEstado.equals("RO")) {
            throw new IllegalArgumentException(
                    "Estado inválido. Use 'PO', 'EA' o 'RO'.");
        }

        VehiculoPersona vp = vehiculoPersonaRepository
                .findByVehiculoIdAndPersonaId(vehiculoId, personaId);
        if (vp == null) {
            throw new RuntimeException(
                    "No se encontró relación entre vehículo " + vehiculoId +
                    " y conductor " + personaId);
        }

        vp.setEstado(nuevoEstado);
        return vehiculoPersonaRepository.save(vp);
    }

    @Override
    public List<VehiculoPersona> findConductoresPuedenOperar() {
        List<VehiculoPersona> lista = vehiculoPersonaRepository.findConductoresPuedenOperar();
        if (lista.isEmpty()) {
            throw new RuntimeException("No se encontraron conductores que puedan operar.");
        }
        return lista;
    }

    @Override
    public List<VehiculoPersona> findByVehiculoId(Integer vehiculoId) {
        List<VehiculoPersona> lista = vehiculoPersonaRepository.findByVehiculoId(vehiculoId);
        if (lista.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron conductores para el vehículo con id: " + vehiculoId);
        }
        return lista;
    }
}