package com.devut.proyecto.services.interfaces;

import com.devut.proyecto.config.model.PersonaAdminResponse;
import com.devut.proyecto.entities.Persona;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPersonaService {

    Persona save(Persona persona);
    List<Persona> findAll();
    Optional<Persona> findById(Integer id);
    Persona update(Integer id, Persona persona);

    Optional<Persona> findByIdentificacion(String identificacion);
    List<Persona> findByTipoPersona(String tipo);
    List<Map<String, Object>> countByTipoPersona();
    
    PersonaAdminResponse saveAdmin(Persona persona);
}