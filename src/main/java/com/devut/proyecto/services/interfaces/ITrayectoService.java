package com.devut.proyecto.services.interfaces;

import com.devut.proyecto.entities.Trayecto;
import com.devut.proyecto.repository.TrayectoRepository.TrayectoRutaConductorProjection;

import java.util.List;

public interface ITrayectoService {
	
	List<Trayecto> obtenerTrayectosConCoordenadas();
	
    // 1. Obtener información agrupada por placa
    List<TrayectoRutaConductorProjection> obtenerRutaYConductorPorPlaca(String placa);

    // 2. Obtener trayectos con restricciones (Vehículos inhabilitados o conductores restringidos)
    List<Trayecto> obtenerTrayectosConRestricciones();

    // 3. Obtener trayectos por código de ruta para la interfaz
    List<Trayecto> obtenerPorCodigoRuta(String codigoRuta);
}