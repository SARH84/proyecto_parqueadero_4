package com.devut.proyecto.entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "vehiculos")
public class Vehiculo {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name= "tipo_vehiculo", nullable = false)
	private String tipoVehiculo; // AUTOMOVIL O MOTOCICLETA
	
	@Column(unique = true, nullable = false, length = 6)
	private String placa;
	
	@Column(name = "tipo_servicio", nullable = false, length = 2)
	private String tipoServicio; // PU -> PÚBLICO, PR -> PRIVADO
	
	@Column(name = "tipo_combustible", nullable = false)
	private String tipoCombustible; // GASOLINA, GAS O DIESEL
	
	@Column(name = "capacidad_pasajeros", nullable = false)
	private Integer capacidadPasajeros;
	
	@Column(nullable = false, length = 7)
	private String color; // Hex: #FFFFFF
	
	  @Column(nullable = false)
	    private Integer modelo;

	    @Column(nullable = false)
	    private String marca;

	    @Column(nullable = false)
	    private String linea;

	    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonManagedReference
	    private List<VehiculoDocumento> documentos = new ArrayList<>();
	    
	    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonManagedReference("vehiculo-conductores")
	    private List<VehiculoPersona> conductores = new ArrayList<>();

	    public List<VehiculoPersona> getConductores() { return conductores; }
	    public void setConductores(List<VehiculoPersona> conductores) { this.conductores = conductores; }
	    
	

	    public Integer getId() { return id; }
	    public String getTipoVehiculo() { return tipoVehiculo; }
	    public String getPlaca() { return placa; }
	    public String getTipoServicio() { return tipoServicio; }
	    public String getTipoCombustible() { return tipoCombustible; }
	    public Integer getCapacidadPasajeros() { return capacidadPasajeros; }
	    public String getColor() { return color; }
	    public Integer getModelo() { return modelo; }
	    public String getMarca() { return marca; }
	    public String getLinea() { return linea; }
	    public List<VehiculoDocumento> getDocumentos() { return documentos; }

	
	    public void setId(Integer id) { this.id = id; }
	    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
	    public void setPlaca(String placa) { this.placa = placa; }
	    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
	    public void setTipoCombustible(String tipoCombustible) { this.tipoCombustible = tipoCombustible; }
	    public void setCapacidadPasajeros(Integer capacidadPasajeros) { this.capacidadPasajeros = capacidadPasajeros; }
	    public void setColor(String color) { this.color = color; }
	    public void setModelo(Integer modelo) { this.modelo = modelo; }
	    public void setMarca(String marca) { this.marca = marca; }
	    public void setLinea(String linea) { this.linea = linea; }
	    public void setDocumentos(List<VehiculoDocumento> documentos) { this.documentos = documentos; }
	    
}
