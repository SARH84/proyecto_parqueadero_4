package com.devut.proyecto.controller;

import com.devut.proyecto.config.JWTAuthtenticationConfig; 
import com.devut.proyecto.config.model.JwtRequest;
import com.devut.proyecto.config.model.JwtResponse;
import com.devut.proyecto.entities.Usuario;
import com.devut.proyecto.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(
        value    = "/authenticate",
        method   = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest,
            @RequestHeader(value = "APIKey", required = false) String apiKey)
            throws Exception {

        System.out.println("*****************************************************");
        System.out.println("username: [" + authenticationRequest.getUsername() + "]");
        System.out.println("password: [" + authenticationRequest.getPassword() + "]");
        System.out.println("*****************************************************");

        // Validar que vengan los datos
        if (authenticationRequest.getUsername() == null ||
            authenticationRequest.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"El username es requerido.\"}");
        }
        if (authenticationRequest.getPassword() == null ||
            authenticationRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"El password es requerido.\"}");
        }

        // Validar que venga el APIKey en el header
        if (apiKey == null || apiKey.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("{\"error\":\"El header APIKey es requerido.\"}");
        }

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByLogin(
                authenticationRequest.getUsername()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\":\"Usuario no encontrado.\"}");
        }

        // Validar password
        if (!usuario.getPassword().equals(authenticationRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\":\"Contraseña incorrecta.\"}");
        }

        // Validar APIKey
        if (!usuario.getApikey().equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("{\"error\":\"APIKey inválida.\"}");
        }

        // Todo correcto, generar token
        final String token = jwtAuthtenticationConfig
                .getJWTToken(authenticationRequest.getUsername());

        System.out.println("*****************************************************");
        System.out.println("token: [" + token + "]");
        System.out.println("*****************************************************");

        return ResponseEntity.ok(new JwtResponse(token));
    }
}