package com.devut.proyecto.services.interfaces;

import com.devut.proyecto.entities.VehiculoPersona; 
import java.util.List;

public interface IVehiculoPersonaService {

    VehiculoPersona asociarConductor(Integer vehiculoId, Integer personaId);
    VehiculoPersona cambiarEstado(Integer vehiculoId, Integer personaId, String nuevoEstado);
    List<VehiculoPersona> findConductoresPuedenOperar();
    List<VehiculoPersona> findByVehiculoId(Integer vehiculoId);
}