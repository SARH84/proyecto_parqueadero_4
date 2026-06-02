package com.devut.proyecto.services.interfaces;

import com.devut.proyecto.entities.Vehiculo; 

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IVehiculoService {

    Vehiculo save(Vehiculo vehiculo);
    List<Vehiculo> findAll();
    Optional<Vehiculo> findById(Integer id);
    Vehiculo update(Integer id, Vehiculo vehiculo);
    void delete(Integer id);

    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByTipoVehiculo(String tipoVehiculo);
    List<Vehiculo> findByTipoDocumento(String codigoDocumento);
    List<Vehiculo> findByEstadoDocumento(String estado);

    void agregarDocumentoAVehiculo(Integer vehiculoId, Integer documentoId);
    
    Optional<Vehiculo> findByPlacaConDetalles(String placa);
    List<Vehiculo> findVehiculosConDocumentosVencidos();
    List<Vehiculo> findVehiculosConDocumentosPorVencer(LocalDate fechaLimite);
}
