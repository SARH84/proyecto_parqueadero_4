package com.devut.proyecto.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.devut.proyecto.entities.Trayecto;
import com.devut.proyecto.repository.TrayectoRepository;
import com.devut.proyecto.services.interfaces.IGoogleMapsService;

@Component
public class TareasProgramadas {
    
    @Autowired
    private TrayectoRepository trayectoRepo;

    @Autowired
    private IGoogleMapsService googleMapsService; 

    // Tarea: Sincronizar coordenadas cada 90 segundos
    @Scheduled(fixedRate = 90000)
    public void sincronizarCoordenadas() {
        System.out.println(">>> [LOG] Ejecutando sincronización de coordenadas...");
        
        // Buscamos solo los trayectos que tienen latitud NULL
        List<Trayecto> pendientes = trayectoRepo.findByLatitudIsNull();
        
        if (pendientes.isEmpty()) {
            System.out.println(">>> [LOG] No hay trayectos pendientes de geolocalizar.");
            return;
        }

        System.out.println(">>> [LOG] Se encontraron " + pendientes.size() + " trayectos para procesar.");

        for (Trayecto t : pendientes) {
            try {
                // Verificamos que tenga dirección antes de llamar a la API
                if (t.getParadaInicial() != null && !t.getParadaInicial().isEmpty()) {
                    googleMapsService.actualizarCoordenadas(t);
                    System.out.println(">>> [LOG] Trayecto ID " + t.getId() + " geolocalizado con éxito.");
                }
            } catch (Exception e) {
                System.err.println(">>> [ERROR] Falló el trayecto ID " + t.getId() + ": " + e.getMessage());
            }
        }
    }
}