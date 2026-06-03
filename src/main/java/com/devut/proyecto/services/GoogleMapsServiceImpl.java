package com.devut.proyecto.services;

import com.devut.proyecto.entities.Trayecto;
import com.devut.proyecto.repository.TrayectoRepository;
import com.devut.proyecto.services.interfaces.IGoogleMapsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsServiceImpl implements IGoogleMapsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrayectoRepository trayectoRepository;

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Override
    public void actualizarCoordenadas(Trayecto trayecto) {
        String direccion = trayecto.getParadaInicial();
        if (direccion == null || direccion.isEmpty()) return;

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                     + direccion.replace(" ", "+") + "&key=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            
            if ("OK".equals(root.path("status").asText())) {
                JsonNode location = root.path("results").get(0).path("geometry").path("location");
                trayecto.setLatitud(location.path("lat").asDouble());
                trayecto.setLongitud(location.path("lng").asDouble());
                trayectoRepository.save(trayecto);
            }
        } catch (Exception e) {
            System.err.println("Error en geocodificación: " + e.getMessage());
        }
    }
}