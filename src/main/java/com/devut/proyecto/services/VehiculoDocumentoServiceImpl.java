package com.devut.proyecto.services;

import com.devut.proyecto.config.model.VehiculoDocumentoRequest; 
import com.devut.proyecto.entities.Documento;
import com.devut.proyecto.entities.Vehiculo;
import com.devut.proyecto.entities.VehiculoDocumento;
import com.devut.proyecto.repository.DocumentoRepository;
import com.devut.proyecto.repository.VehiculoDocumentoRepository;
import com.devut.proyecto.repository.VehiculoRepository;
import com.devut.proyecto.services.interfaces.IVehiculoDocumentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoDocumentoServiceImpl implements IVehiculoDocumentoService {

    @Autowired
    private VehiculoDocumentoRepository vehiculoDocumentoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public List<VehiculoDocumento> cargarDocumentos(
            List<VehiculoDocumentoRequest> requests) {

        List<VehiculoDocumento> resultado = new ArrayList<>();

        for (VehiculoDocumentoRequest req : requests) {


            Vehiculo vehiculo = vehiculoRepository.findById(req.getVehiculoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Vehículo no encontrado con id: " + req.getVehiculoId()));

            Documento documento = documentoRepository.findById(req.getDocumentoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Documento no encontrado con id: " + req.getDocumentoId()));

         
            byte[] pdfBytes = null;
            if (req.getArchivoPdfBase64() != null &&
                !req.getArchivoPdfBase64().isEmpty()) {
                try {
                    pdfBytes = Base64.getDecoder().decode(req.getArchivoPdfBase64());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "El archivo PDF no tiene un formato Base64 válido " +
                            "para el documento id: " + req.getDocumentoId());
                }
            }

           
            Optional<VehiculoDocumento> existente = vehiculoDocumentoRepository
                    .findByVehiculoIdAndDocumentoId(
                            req.getVehiculoId(), req.getDocumentoId());

            VehiculoDocumento vd;
            if (existente.isPresent()) {
                // ACTUALIZAR el existente
                vd = existente.get();
                vd.setArchivoPdf(pdfBytes);
                vd.setFechaExpedicion(req.getFechaExpedicion());
                vd.setFechaVencimiento(req.getFechaVencimiento());
                vd.setEstado("En Verificación");
            } else {
           
                vd = new VehiculoDocumento();
                vd.setVehiculo(vehiculo);
                vd.setDocumento(documento);
                vd.setArchivoPdf(pdfBytes);
                vd.setFechaExpedicion(req.getFechaExpedicion());
                vd.setFechaVencimiento(req.getFechaVencimiento());
                vd.setEstado("En Verificación");
            }

            resultado.add(vehiculoDocumentoRepository.save(vd));
        }

        return resultado;
    }

    @Override
    public List<VehiculoDocumento> findByVehiculoId(Integer vehiculoId) {
        List<VehiculoDocumento> lista = vehiculoDocumentoRepository
                .findByVehiculoId(vehiculoId);
        if (lista.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron documentos para el vehículo con id: "
                    + vehiculoId);
        }
        return lista;
    }
}