package com.devut.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @EmbeddedId
    private UsuarioId id;

    @MapsId("idpersona")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersona", nullable = false)
    @JsonBackReference("persona-usuario")
    private Persona persona;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonIgnore
    private String apikey;
    public UsuarioId getId() { return id; }
    public Persona getPersona() { return persona; }
    public String getPassword() { return password; }
    public String getApikey() { return apikey; }

    public void setId(UsuarioId id) { this.id = id; }
    public void setPersona(Persona persona) { this.persona = persona; }
    public void setPassword(String password) { this.password = password; }
    public void setApikey(String apikey) { this.apikey = apikey; }
}