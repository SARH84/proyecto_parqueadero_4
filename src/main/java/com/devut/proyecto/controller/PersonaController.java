package com.devut.proyecto.controller;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devut.proyecto.entities.Persona;
import com.devut.proyecto.services.interfaces.IPersonaService;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

	
	@Autowired
	private IPersonaService personaService;

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Persona persona) {
	    try {
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(personaService.save(persona));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al crear la persona: " + e.getMessage());
	    }
	}

	@PostMapping("/admin")
	public ResponseEntity<?> crearAdmin(@RequestBody Persona persona) {
	    try {
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(personaService.saveAdmin(persona));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al crear el administrador: " + e.getMessage());
	    }
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		try {
			return ResponseEntity.ok(personaService.findAll());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}

	@GetMapping ("/{id}")
	public ResponseEntity<?> obtenerporId(@PathVariable Integer id){
		try {
			return ResponseEntity.ok(personaService.findAll());			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Persona persona){
	
		 try {
	            return ResponseEntity.ok(personaService.update(id, persona));
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(e.getMessage());
	        }
	    }
	
	// BÚSQUEDAS
	
	@GetMapping("/identificacion/{identificacion}")
	public ResponseEntity<?> buscarPorIdentificacion(
			@PathVariable String identificacion) {
		try {
			return ResponseEntity.ok(
					personaService.findByIdentificacion(identificacion));
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        try {
            return ResponseEntity.ok(personaService.findByTipoPersona(tipo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
	
	 // CONSULTA PÚBLICA 
	
    @GetMapping("/conteo-por-tipo")
    public ResponseEntity<?> contarPorTipo() {
        try {
            List<Map<String, Object>> resultado = personaService.countByTipoPersona();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener conteo: " + e.getMessage());
        }
    }

}
