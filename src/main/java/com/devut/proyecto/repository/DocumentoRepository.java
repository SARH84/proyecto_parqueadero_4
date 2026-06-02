package com.devut.proyecto.repository;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.devut.proyecto.entities.Documento;
import java.util.List;

@Repository

public interface DocumentoRepository extends JpaRepository <Documento,Integer> {
	
	
	
	Documento findByCodigo(String codigo);
	
	Documento findByNombre(String nombre);
	
	List<Documento>
	findByTipoVehiculo(String tipoVehiculo);
	
	List<Documento>
	findByObligatorio(String obligatorio);
	

}
