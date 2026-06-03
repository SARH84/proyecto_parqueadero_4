package com.devut.proyecto.repository;

import com.devut.proyecto.entities.Trayecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrayectoRepository extends JpaRepository<Trayecto, Integer> {
	

    List<Trayecto> findByLatitudIsNotNullAndLongitudIsNotNull();
    List<Trayecto> findByLatitudIsNull();
    List<Trayecto> findByCodigoRuta(String codigoRuta);

    
    interface TrayectoRutaConductorProjection {
        String getCodigoRuta();
        String getConductorNombres();
        String getConductorApellidos();
    }

    // Consulta JPQL (mejor que nativeQuery para evitar problemas de tipos)
    @Query("SELECT t.codigoRuta as codigoRuta, c.nombres as conductorNombres, c.apellidos as conductorApellidos " +
            "FROM Trayecto t JOIN t.conductor c JOIN t.vehiculo v " +
            "WHERE v.placa = :placa " +
            "GROUP BY t.codigoRuta, c.nombres, c.apellidos")
     List<TrayectoRutaConductorProjection> findRutaYConductorPorPlaca(@Param("placa") String placa);

   
    @Query(value = "SELECT DISTINCT t.* FROM trayectos t " +
            "JOIN vehiculo_documentos doc ON t.idvehiculo = doc.vehiculo_id " +
            "WHERE doc.estado != 'APROBADO'", 
    nativeQuery = true)
    List<Trayecto> findTrayectosConRestricciones();
}