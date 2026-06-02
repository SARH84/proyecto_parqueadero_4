package com.devut.proyecto.repository;

import com.devut.proyecto.entities.VehiculoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoDocumentoRepository extends JpaRepository<VehiculoDocumento, Integer> {

    @Query("SELECT vd FROM VehiculoDocumento vd WHERE vd.vehiculo.id = :vehiculoId")
    List<VehiculoDocumento> findByVehiculoId(@Param("vehiculoId") Integer vehiculoId);

    @Query("SELECT vd FROM VehiculoDocumento vd WHERE vd.vehiculo.id = :vehiculoId AND vd.documento.id = :documentoId")
    Optional<VehiculoDocumento> findByVehiculoIdAndDocumentoId(
            @Param("vehiculoId") Integer vehiculoId,
            @Param("documentoId") Integer documentoId);
}