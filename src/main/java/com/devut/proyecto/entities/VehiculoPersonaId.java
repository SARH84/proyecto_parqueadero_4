package com.devut.proyecto.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VehiculoPersonaId implements Serializable {

    private Integer vehiculoId;
    private Integer personaId;

    public VehiculoPersonaId() {}

    public VehiculoPersonaId(Integer vehiculoId, Integer personaId) {
        this.vehiculoId = vehiculoId;
        this.personaId = personaId;
    }

    public Integer getVehiculoId() { return vehiculoId; }
    public Integer getPersonaId() { return personaId; }

    public void setVehiculoId(Integer vehiculoId) { this.vehiculoId = vehiculoId; }
    public void setPersonaId(Integer personaId) { this.personaId = personaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehiculoPersonaId)) return false;
        VehiculoPersonaId that = (VehiculoPersonaId) o;
        return Objects.equals(vehiculoId, that.vehiculoId) &&
               Objects.equals(personaId, that.personaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehiculoId, personaId);
    }
}