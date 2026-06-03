package com.devut.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String identificacion;

    @Column(name = "tipo_identificacion", nullable = false, length = 2)
    private String tipoIdentificacion;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(name = "tipo_persona", nullable = false, length = 1)
    private String tipoPersona;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("persona-usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("persona")
    private List<VehiculoPersona> vehiculos = new ArrayList<>();
    
    @Column(name = "licencia_conduccion", columnDefinition = "LONGTEXT") // Usamos LONGTEXT para Base64
    private String licenciaConduccion;

    @Column(name = "fecha_vigencia_licencia")
    private java.time.LocalDate fechaVigenciaLicencia;

    public Integer getId() { return id; }
    public String getIdentificacion() { return identificacion; }
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTipoPersona() { return tipoPersona; }
    public Usuario getUsuario() { return usuario; }
    public List<VehiculoPersona> getVehiculos() { return vehiculos; }
    public String getLicenciaConduccion() { return licenciaConduccion; }
    public java.time.LocalDate getFechaVigenciaLicencia() { return fechaVigenciaLicencia; }

    public void setId(Integer id) { this.id = id; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setVehiculos(List<VehiculoPersona> vehiculos) { this.vehiculos = vehiculos; }
    public void setLicenciaConduccion(String licenciaConduccion) { this.licenciaConduccion = licenciaConduccion; }
    public void setFechaVigenciaLicencia(java.time.LocalDate fechaVigenciaLicencia) { this.fechaVigenciaLicencia = fechaVigenciaLicencia; }
}