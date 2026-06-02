package com.devut.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.devut.proyecto.entities.Vehiculo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    Optional<Vehiculo> findByPlaca(String placa);

    List<Vehiculo> findByTipoVehiculo(String tipoVehiculo);

    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.estado = :estado")
    List<Vehiculo> findByEstadoDocumento(@Param("estado") String estado);

    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.documento.codigo = :codigo")
    List<Vehiculo> findByCodigoDocumento(@Param("codigo") String codigo);
    
    // Vehículos con documentos vencidos (consulta pública)
    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.estado = 'Vencido'")
    List<Vehiculo> findVehiculosConDocumentosVencidos();

    // Vehículos con documentos por vencer en X días (consulta pública)
    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.fechaVencimiento BETWEEN CURRENT_DATE AND :fechaLimite")
    List<Vehiculo> findVehiculosConDocumentosPorVencer(@Param("fechaLimite") LocalDate fechaLimite);

    // Vehículo por placa con conductores y documentos (consulta pública)
    @Query("SELECT DISTINCT v FROM Vehiculo v LEFT JOIN FETCH v.documentos WHERE v.placa = :placa")
    Optional<Vehiculo> findByPlacaConDetalles(@Param("placa") String placa);
}