package com.devut.proyecto.repository;

import com.devut.proyecto.entities.VehiculoPersona; 
import com.devut.proyecto.entities.VehiculoPersonaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoPersonaRepository extends JpaRepository<VehiculoPersona, VehiculoPersonaId> {

    // Conductores que pueden operar (consulta pública)
    @Query("SELECT vp FROM VehiculoPersona vp WHERE vp.estado = 'PO'")
    List<VehiculoPersona> findConductoresPuedenOperar();

    // Conductores de un vehículo específico
    @Query("SELECT vp FROM VehiculoPersona vp WHERE vp.vehiculo.id = :vehiculoId")
    List<VehiculoPersona> findByVehiculoId(@Param("vehiculoId") Integer vehiculoId);

    // Vehículos de un conductor específico
    @Query("SELECT vp FROM VehiculoPersona vp WHERE vp.persona.id = :personaId")
    List<VehiculoPersona> findByPersonaId(@Param("personaId") Integer personaId);

    // Buscar relación específica conductor-vehículo
    @Query("SELECT vp FROM VehiculoPersona vp WHERE vp.vehiculo.id = :vehiculoId AND vp.persona.id = :personaId")
    VehiculoPersona findByVehiculoIdAndPersonaId(@Param("vehiculoId") Integer vehiculoId,
                                                  @Param("personaId") Integer personaId);
}