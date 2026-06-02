package com.devut.proyecto.controller;

import com.devut.proyecto.entities.Vehiculo; 
import com.devut.proyecto.services.interfaces.IVehiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;

    // CRUD
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Vehiculo vehiculo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(vehiculoService.save(vehiculo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el vehículo: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Vehiculo> lista = vehiculoService.findAll();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron vehículos registrados.");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener vehículos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            return vehiculoService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @RequestBody Vehiculo vehiculo) {
        try {
            return ResponseEntity.ok(vehiculoService.update(id, vehiculo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            vehiculoService.delete(id);
            return ResponseEntity.ok("Vehículo con id " + id +
                    " eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // BÚSQUEDAS
    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        try {
            return vehiculoService.findByPlacaConDetalles(placa)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/tipo/{tipoVehiculo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipoVehiculo) {
        try {
            List<Vehiculo> lista = vehiculoService
                    .findByTipoVehiculo(tipoVehiculo);
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron vehículos de tipo: "
                                + tipoVehiculo);
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar por tipo: " + e.getMessage());
        }
    }

    @GetMapping("/documento/{codigo}")
    public ResponseEntity<?> buscarPorDocumento(@PathVariable String codigo) {
        try {
            List<Vehiculo> lista = vehiculoService.findByTipoDocumento(codigo);
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron vehículos con documento: "
                                + codigo);
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar por documento: " + e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(@PathVariable String estado) {
        try {
            List<Vehiculo> lista = vehiculoService.findByEstadoDocumento(estado);
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron vehículos con estado: "
                                + estado);
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar por estado: " + e.getMessage());
        }
    }

    // CONSULTAS PÚBLICAS
    @GetMapping("/vencidos")
    public ResponseEntity<?> vehiculosConDocumentosVencidos() {
        try {
            List<Vehiculo> lista = vehiculoService
                    .findVehiculosConDocumentosVencidos();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron vehículos con documentos vencidos.");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar vehículos vencidos: " + e.getMessage());
        }
    }

    // dias es el parámetro que especifica cuántos días hacia adelante buscar
    @GetMapping("/por-vencer")
    public ResponseEntity<?> vehiculosConDocumentosPorVencer(
            @RequestParam Integer dias) {
        try {
            LocalDate fechaLimite = LocalDate.now().plusDays(dias);
            List<Vehiculo> lista = vehiculoService
                    .findVehiculosConDocumentosPorVencer(fechaLimite);
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron vehículos con documentos " +
                                "por vencer en los próximos " + dias + " días.");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar vehículos por vencer: "
                            + e.getMessage());
        }
    }

    // Agregar documento a vehículo existente
    @PostMapping("/{vehiculoId}/documentos/{documentoId}")
    public ResponseEntity<?> agregarDocumento(@PathVariable Integer vehiculoId,
                                               @PathVariable Integer documentoId) {
        try {
            vehiculoService.agregarDocumentoAVehiculo(vehiculoId, documentoId);
            return ResponseEntity.ok("Documento agregado correctamente " +
                    "al vehículo con id: " + vehiculoId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}

