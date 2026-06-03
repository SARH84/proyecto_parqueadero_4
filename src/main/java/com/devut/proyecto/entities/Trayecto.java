package com.devut.proyecto.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trayectos")
public class Trayecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiado a 'id' para mantener la consistencia de tu proyecto

    // Relación con el Conductor (Persona)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpersona", referencedColumnName = "id", nullable = false) // 🌟 Corregido: apunta a 'id' en la entidad Persona
    private Persona conductor;

    // Relación con el Vehículo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idvehiculo", referencedColumnName = "id", nullable = false) // 🌟 Corregido: apunta a 'id' en la entidad Vehiculo
    private Vehiculo vehiculo;

    @Column(name = "codigo_ruta", nullable = false, length = 50)
    private String codigoRuta;

    // Paradas obligatorias
    @Column(name = "parada_inicial", nullable = false, length = 255)
    private String paradaInicial;

    @Column(name = "parada_final", nullable = false, length = 255)
    private String paradaFinal;

    // Las 5 paradas intermedias opcionales
    @Column(name = "parada_intermedia_1", length = 255)
    private String paradaIntermedia1;

    @Column(name = "parada_intermedia_2", length = 255)
    private String paradaIntermedia2;

    @Column(name = "parada_intermedia_3", length = 255)
    private String paradaIntermedia3;

    @Column(name = "parada_intermedia_4", length = 255)
    private String paradaIntermedia4;

    @Column(name = "parada_intermedia_5", length = 255)
    private String paradaIntermedia5;
    
    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // --- CONSTRUCTORES ---
    public Trayecto() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Persona getConductor() { return conductor; }
    public void setConductor(Persona conductor) { this.conductor = conductor; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public String getCodigoRuta() { return codigoRuta; }
    public void setCodigoRuta(String codigoRuta) { this.codigoRuta = codigoRuta; }

    public String getParadaInicial() { return paradaInicial; }
    public void setParadaInicial(String paradaInicial) { this.paradaInicial = paradaInicial; }

    public String getParadaFinal() { return paradaFinal; }
    public void setParadaFinal(String paradaFinal) { this.paradaFinal = paradaFinal; }

    public String getParadaIntermedia1() { return paradaIntermedia1; }
    public void setParadaIntermedia1(String paradaIntermedia1) { this.paradaIntermedia1 = paradaIntermedia1; }

    public String getParadaIntermedia2() { return paradaIntermedia2; }
    public void setParadaIntermedia2(String paradaIntermedia2) { this.paradaIntermedia2 = paradaIntermedia2; }

    public String getParadaIntermedia3() { return paradaIntermedia3; }
    public void setParadaIntermedia3(String paradaIntermedia3) { this.paradaIntermedia3 = paradaIntermedia3; }

    public String getParadaIntermedia4() { return paradaIntermedia4; }
    public void setParadaIntermedia4(String paradaIntermedia4) { this.paradaIntermedia4 = paradaIntermedia4; }

    public String getParadaIntermedia5() { return paradaIntermedia5; }
    public void setParadaIntermedia5(String paradaIntermedia5) { this.paradaIntermedia5 = paradaIntermedia5; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}