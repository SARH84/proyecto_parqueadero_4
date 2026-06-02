package com.devut.proyecto.services;

import com.devut.proyecto.config.model.PersonaAdminResponse; 
import com.devut.proyecto.entities.Persona;
import com.devut.proyecto.entities.Usuario;
import com.devut.proyecto.entities.UsuarioId;
import com.devut.proyecto.repository.PersonaRepository;
import com.devut.proyecto.repository.UsuarioRepository;
import com.devut.proyecto.services.interfaces.IPersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String generarLogin(Persona persona) {
        String primeraLetraNombre   = persona.getNombres().substring(0, 1).toLowerCase();
        String primeraLetraApellido = persona.getApellidos().substring(0, 1).toLowerCase();
        return primeraLetraNombre + primeraLetraApellido + persona.getIdentificacion();
    }

    // Genera password aleatorio de 10 caracteres
    private String generarPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    // Genera APIKey como UUID
    private String generarApiKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public PersonaAdminResponse saveAdmin(Persona persona) {
        if (persona.getTipoPersona() == null || persona.getTipoPersona().isEmpty()) {
            throw new IllegalArgumentException("El tipo de persona es requerido. Use 'C' o 'A'.");
        }
        if (!persona.getTipoPersona().equals("A")) {
            throw new IllegalArgumentException("Este método solo es para personas tipo Administrativo.");
        }

        Persona personaGuardada = personaRepository.save(persona);

        String login    = generarLogin(personaGuardada);
        String password = generarPassword();
        String apikey   = generarApiKey();

        Usuario usuario = new Usuario();
        usuario.setId(new UsuarioId(personaGuardada.getId(), login));
        usuario.setPersona(personaGuardada);
        usuario.setPassword(password);
        usuario.setApikey(apikey);
        usuarioRepository.save(usuario);

        PersonaAdminResponse response = new PersonaAdminResponse();
        response.setId(personaGuardada.getId());
        response.setIdentificacion(personaGuardada.getIdentificacion());
        response.setTipoIdentificacion(personaGuardada.getTipoIdentificacion());
        response.setNombres(personaGuardada.getNombres());
        response.setApellidos(personaGuardada.getApellidos());
        response.setCorreo(personaGuardada.getCorreo());
        response.setTipoPersona(personaGuardada.getTipoPersona());
        response.setLogin(login);
        response.setPasswordInicial(password);
        response.setApikey(apikey);

        return response;
    }

    @Override
    public Persona save(Persona persona) {
        if (persona.getTipoPersona() == null || persona.getTipoPersona().isEmpty()) {
            throw new IllegalArgumentException("El tipo de persona es requerido. Use 'C' o 'A'.");
        }
        if (!persona.getTipoPersona().equals("C")) {
            throw new IllegalArgumentException(
                    "Use el endpoint POST /api/personas/admin para crear personas Administrativas.");
        }
        return personaRepository.save(persona);
    }
    @Override
    public List<Persona> findAll() {
        List<Persona> personas = personaRepository.findAll();
        if (personas.isEmpty()) {
            throw new RuntimeException("No se encontraron personas registradas.");
        }
        return personas;
    }

    @Override
    public Optional<Persona> findById(Integer id) {
        return Optional.of(personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id)));
    }

    @Override
    public Persona update(Integer id, Persona datos) {
        Persona existente = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        existente.setIdentificacion(datos.getIdentificacion());
        existente.setTipoIdentificacion(datos.getTipoIdentificacion());
        existente.setNombres(datos.getNombres());
        existente.setApellidos(datos.getApellidos());
        existente.setCorreo(datos.getCorreo());
        existente.setTipoPersona(datos.getTipoPersona());

        return personaRepository.save(existente);
    }

    @Override
    public Optional<Persona> findByIdentificacion(String identificacion) {
        return Optional.of(personaRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new RuntimeException(
                        "Persona no encontrada con identificación: " + identificacion)));
    }

    @Override
    public List<Persona> findByTipoPersona(String tipo) {
        List<Persona> personas = personaRepository.findByTipoPersona(tipo);
        if (personas.isEmpty()) {
            throw new RuntimeException("No se encontraron personas con tipo: " + tipo);
        }
        return personas;
    }

    @Override
    public List<Map<String, Object>> countByTipoPersona() {
        List<Object[]> resultados = personaRepository.countByTipoPersona();
        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Object[] fila : resultados) {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("tipoPersona", fila[0]);
            mapa.put("total",       fila[1]);
            respuesta.add(mapa);
        }
        return respuesta;
    }
}