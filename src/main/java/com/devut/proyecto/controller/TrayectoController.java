package com.devut.proyecto.controller;

import com.devut.proyecto.entities.Trayecto;
import com.devut.proyecto.services.interfaces.ITrayectoService;
import com.devut.proyecto.repository.TrayectoRepository.TrayectoRutaConductorProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trayectos")
@CrossOrigin(origins = "*")
public class TrayectoController {

    @Autowired
    private ITrayectoService trayectoService;

    @GetMapping("/buscar-por-placa")
    public ResponseEntity<List<TrayectoRutaConductorProjection>> buscarPorPlaca(@RequestParam String placa) {
        return ResponseEntity.ok(trayectoService.obtenerRutaYConductorPorPlaca(placa));
    }

    @GetMapping("/restricciones")
    public ResponseEntity<List<Trayecto>> buscarConRestricciones() {
        return ResponseEntity.ok(trayectoService.obtenerTrayectosConRestricciones());
    }

    @GetMapping("/ruta/{codigoRuta}")
    public ResponseEntity<List<Trayecto>> buscarPorCodigoRuta(@PathVariable String codigoRuta) {
        return ResponseEntity.ok(trayectoService.obtenerPorCodigoRuta(codigoRuta));
    }

    
    @GetMapping("/con-coordenadas")
    public ResponseEntity<List<Trayecto>> listarConCoordenadas() {
        return ResponseEntity.ok(trayectoService.obtenerTrayectosConCoordenadas());
    }
}