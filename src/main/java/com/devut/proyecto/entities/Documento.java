package com.devut.proyecto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity 
@Table(name = "documentos")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String codigo;
	
	@Column(nullable = false)
    private String nombre;

    @Column(name = "tipo_vehiculo", nullable = false)
    private String tipoVehiculo; // A (ES AUTO), M (ES MOTO), AM (ES AMBOS) !!!
    
    @Column(nullable = false)
    private String obligatorio; // RA, RM, RR

    private String descripcion;

    public Integer getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getTipoVehiculo() { return tipoVehiculo; }
    public String getObligatorio() { return obligatorio; }
    public String getDescripcion() { return descripcion; }

 
    public void setId(Integer id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
    public void setObligatorio(String obligatorio) { this.obligatorio = obligatorio; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

