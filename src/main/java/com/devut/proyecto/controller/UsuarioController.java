package com.devut.proyecto.controller;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.devut.proyecto.services.interfaces.IUsuarioService;



@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController  {
    
    @Autowired
    private IUsuarioService usuarioService;

    @PutMapping("/{login}/password")
    public ResponseEntity<?> cambiarPassword(@PathVariable String login,
                                             @RequestParam String nuevaPassword) {
    	try {
    		usuarioService.cambiarPassword(login, nuevaPassword);
    		return ResponseEntity.ok("Password actualizado correctamente " + "para el usuario: " + login);
    	} catch (RuntimeException e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND)
    				.body(e.getMessage());
    	}
    }
    
    
    // REGENERA APIKEY - GET PARA GENERAR Y DEVOLVER
    @GetMapping("/{login}/apikey")
    public ResponseEntity<?> regenerarApiKey(@PathVariable String login) {
    	try {
    		String nuevoApikey = usuarioService.regenerarApikey(login);
    		return ResponseEntity.ok("APIKey regenerada correctamente: " 
    				+ nuevoApikey);
    	} catch (RuntimeException e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND)
    				.body(e.getMessage());
    	}
    }




}

