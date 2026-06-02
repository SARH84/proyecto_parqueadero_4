package com.devut.proyecto.services;

import com.devut.proyecto.entities.Documento; 
import com.devut.proyecto.repository.DocumentoRepository;
import com.devut.proyecto.services.interfaces.IDocumentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoServiceImpl implements IDocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public Documento save(Documento documento) {
        return documentoRepository.save(documento);
    }

    @Override
    public List<Documento> findAll() {
        return documentoRepository.findAll();
    }

    @Override
    public Optional<Documento> findById(Integer id) {
        return documentoRepository.findById(id);
    }

    @Override
    public Documento update(Integer id, Documento documento) {
        if (documentoRepository.existsById(id)) {
            documento.setId(id);
            return documentoRepository.save(documento);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        documentoRepository.deleteById(id);
    }
}
// Hola, ya quedó bien, le haces el otro controller?

