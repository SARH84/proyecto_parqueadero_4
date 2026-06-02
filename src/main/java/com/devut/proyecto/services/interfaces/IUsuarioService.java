package com.devut.proyecto.services.interfaces;

public interface IUsuarioService {

    void cambiarPassword(String login, String nuevaPassword);
    String regenerarApikey(String login);
}