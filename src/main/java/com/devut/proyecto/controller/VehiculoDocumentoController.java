package com.devut.proyecto.controller;

import com.devut.proyecto.config.model.VehiculoDocumentoRequest; 
import com.devut.proyecto.entities.VehiculoDocumento;
import com.devut.proyecto.services.interfaces.IVehiculoDocumentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculo-documentos")
public class VehiculoDocumentoController {

    @Autowired
    private IVehiculoDocumentoService vehiculoDocumentoService;

    // Cargar uno o varios PDFs - PROTEGIDO
    @PostMapping("/cargar")
    public ResponseEntity<?> cargarDocumentos(
            @RequestBody List<VehiculoDocumentoRequest> requests) {
        try {
            if (requests == null || requests.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Debe enviar al menos un documento.");
            }
            List<VehiculoDocumento> resultado = vehiculoDocumentoService
                    .cargarDocumentos(requests);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Consultar documentos de un vehículo - PROTEGIDO
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<?> obtenerPorVehiculo(
            @PathVariable Integer vehiculoId) {
        try {
            List<VehiculoDocumento> lista = vehiculoDocumentoService
                    .findByVehiculoId(vehiculoId);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}