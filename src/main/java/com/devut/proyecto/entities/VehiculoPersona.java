package com.devut.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehiculo_personas")
public class VehiculoPersona {

    @EmbeddedId
    private VehiculoPersonaId id;

    @MapsId("vehiculoId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    @JsonBackReference("vehiculo-conductores")
    private Vehiculo vehiculo;

    @MapsId("personaId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    @JsonIgnoreProperties({"vehiculos", "usuario"})
    private Persona persona;
    
    
    @Column(name = "fecha_asociacion", nullable = false)
    private LocalDate fechaAsociacion;

    @Column(nullable = false, length = 2)
    private String estado = "EA"; // PO, EA, RO

    public VehiculoPersonaId getId() { return id; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public Persona getPersona() { return persona; }
    public LocalDate getFechaAsociacion() { return fechaAsociacion; }
    public String getEstado() { return estado; }

    public void setId(VehiculoPersonaId id) { this.id = id; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public void setPersona(Persona persona) { this.persona = persona; }
    public void setFechaAsociacion(LocalDate fechaAsociacion) { this.fechaAsociacion = fechaAsociacion; }
    public void setEstado(String estado) { this.estado = estado; }
}