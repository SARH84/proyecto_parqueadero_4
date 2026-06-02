package com.devut.proyecto.config.model;

public class PersonaAdminResponse {

    private Integer id;
    private String identificacion;
    private String tipoIdentificacion;
    private String nombres;
    private String apellidos;
    private String correo;
    private String tipoPersona;
    private String login;
    private String passwordInicial;
    private String apikey;

    public Integer getId() { return id; }
    public String getIdentificacion() { return identificacion; }
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTipoPersona() { return tipoPersona; }
    public String getLogin() { return login; }
    public String getPasswordInicial() { return passwordInicial; }
    public String getApikey() { return apikey; }

    public void setId(Integer id) { this.id = id; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }
    public void setLogin(String login) { this.login = login; }
    public void setPasswordInicial(String passwordInicial) { this.passwordInicial = passwordInicial; }
    public void setApikey(String apikey) { this.apikey = apikey; }
}