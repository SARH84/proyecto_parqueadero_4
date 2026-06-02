package com.devut.proyecto.services.interfaces;

import com.devut.proyecto.entities.Documento; 
import java.util.List;
import java.util.Optional;

public interface IDocumentoService {

    Documento save(Documento documento);
    List<Documento> findAll();
    Optional<Documento> findById(Integer id);
    Documento update(Integer id, Documento documento);
    void delete(Integer id);
    
}