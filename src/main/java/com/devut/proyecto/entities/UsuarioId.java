package com.devut.proyecto.entities;

import jakarta.persistence.Embeddable; 
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioId implements Serializable {

	private static final long serialVersionUID = 1L;
    private Integer idpersona;
    private String login;

    public UsuarioId() {}

    public UsuarioId(Integer idpersona, String login) {
        this.idpersona = idpersona;
        this.login = login;
    }

    public Integer getIdpersona() { return idpersona; }
    public String getLogin() { return login; }

    public void setIdpersona(Integer idpersona) { this.idpersona = idpersona; }
    public void setLogin(String login) { this.login = login; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioId)) return false;
        UsuarioId that = (UsuarioId) o;
        return Objects.equals(idpersona, that.idpersona) &&
               Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpersona, login);
    }
}