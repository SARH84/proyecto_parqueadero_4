package com.devut.proyecto.controller;

import com.devut.proyecto.entities.Documento; 
import com.devut.proyecto.services.interfaces.IDocumentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private IDocumentoService documentoService;

    @PostMapping
    public ResponseEntity<Documento> crear(@RequestBody Documento documento) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(documentoService.save(documento));
    }

    @GetMapping
    public ResponseEntity<List<Documento>> obtenerTodos() {
        return ResponseEntity.ok(documentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return documentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> actualizar(@PathVariable Integer id,
                                                @RequestBody Documento documento) {
        return ResponseEntity.ok(documentoService.update(id, documento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        documentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}