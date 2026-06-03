package com.devut.proyecto.services;

import com.devut.proyecto.entities.Trayecto;
import com.devut.proyecto.repository.TrayectoRepository;
import com.devut.proyecto.repository.TrayectoRepository.TrayectoRutaConductorProjection;
import com.devut.proyecto.services.interfaces.ITrayectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrayectoServiceImpl implements ITrayectoService {

    @Autowired
    private TrayectoRepository trayectoRepository;

    @Override
    public List<TrayectoRutaConductorProjection> obtenerRutaYConductorPorPlaca(String placa) {
        return trayectoRepository.findRutaYConductorPorPlaca(placa);
    }

    @Override
    public List<Trayecto> obtenerTrayectosConRestricciones() {
        return trayectoRepository.findTrayectosConRestricciones();
    }

    @Override
    public List<Trayecto> obtenerPorCodigoRuta(String codigoRuta) {
        return trayectoRepository.findByCodigoRuta(codigoRuta);
    }
    
    @Override
    public List<Trayecto> obtenerTrayectosConCoordenadas() {
        return trayectoRepository.findByLatitudIsNotNullAndLongitudIsNotNull();
    }
}