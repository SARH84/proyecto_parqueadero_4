package com.devut.proyecto.controller;

import com.devut.proyecto.entities.VehiculoPersona; 
import com.devut.proyecto.services.interfaces.IVehiculoPersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class VehiculoPersonaController {

    @Autowired
    private IVehiculoPersonaService vehiculoPersonaService;

    // Asociar conductor a vehículo
    @PostMapping("/vehiculo/{vehiculoId}/persona/{personaId}")
    public ResponseEntity<?> asociarConductor(@PathVariable Integer vehiculoId,
                                               @PathVariable Integer personaId) {
        try {
            VehiculoPersona vp = vehiculoPersonaService
                    .asociarConductor(vehiculoId, personaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(vp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Cambiar estado conductor en relación con vehículo
    @PutMapping("/vehiculo/{vehiculoId}/persona/{personaId}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer vehiculoId,
                                            @PathVariable Integer personaId,
                                            @RequestBody String nuevoEstado) {
        try {
            VehiculoPersona vp = vehiculoPersonaService
                    .cambiarEstado(vehiculoId, personaId, nuevoEstado);
            return ResponseEntity.ok(vp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // CONSULTA PÚBLICA - conductores que pueden operar
    @GetMapping("/pueden-operar")
    public ResponseEntity<?> conductoresPuedenOperar() {
        try {
            List<VehiculoPersona> lista = vehiculoPersonaService
                    .findConductoresPuedenOperar();
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Conductores por vehículo
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<?> conductoresPorVehiculo(
            @PathVariable Integer vehiculoId) {
        try {
            List<VehiculoPersona> lista = vehiculoPersonaService
                    .findByVehiculoId(vehiculoId);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}