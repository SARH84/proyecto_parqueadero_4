package com.devut.proyecto.repository;

import com.devut.proyecto.entities.Persona; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    @Query("SELECT p FROM Persona p WHERE p.identificacion = :identificacion")
    Optional<Persona> findByIdentificacion(@Param("identificacion") String identificacion);

    @Query("SELECT p FROM Persona p WHERE p.tipoPersona = :tipo")
    List<Persona> findByTipoPersona(@Param("tipo") String tipo);

    @Query("SELECT p FROM Persona p WHERE p.correo = :correo")
    Optional<Persona> findByCorreo(@Param("correo") String correo);

    // Consulta pública: total de personas agrupadas por tipo
    @Query("SELECT p.tipoPersona, COUNT(p) FROM Persona p GROUP BY p.tipoPersona")
    List<Object[]> countByTipoPersona();
}
