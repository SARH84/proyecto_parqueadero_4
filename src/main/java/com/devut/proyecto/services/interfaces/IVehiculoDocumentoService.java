package com.devut.proyecto.services.interfaces;

import com.devut.proyecto.config.model.VehiculoDocumentoRequest; 
import com.devut.proyecto.entities.VehiculoDocumento;
import java.util.List;

public interface IVehiculoDocumentoService {

    // Cargar o actualizar uno o varios documentos PDF
	
    List<VehiculoDocumento> cargarDocumentos(List<VehiculoDocumentoRequest> requests);

    // Obtener documentos de un vehículo
    
    List<VehiculoDocumento> findByVehiculoId(Integer vehiculoId);
}