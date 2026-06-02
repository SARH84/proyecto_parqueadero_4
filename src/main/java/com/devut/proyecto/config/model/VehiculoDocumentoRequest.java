package com.devut.proyecto.config.model;

import java.time.LocalDate;

public class VehiculoDocumentoRequest {

    private Integer vehiculoId;
    private Integer documentoId;
    private String archivoPdfBase64;
    private LocalDate fechaExpedicion;
    private LocalDate fechaVencimiento;

    public Integer getVehiculoId() { return vehiculoId; }
    public Integer getDocumentoId() { return documentoId; }
    public String getArchivoPdfBase64() { return archivoPdfBase64; }
    public LocalDate getFechaExpedicion() { return fechaExpedicion; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }

    public void setVehiculoId(Integer vehiculoId) { this.vehiculoId = vehiculoId; }
    public void setDocumentoId(Integer documentoId) { this.documentoId = documentoId; }
    public void setArchivoPdfBase64(String archivoPdfBase64) { this.archivoPdfBase64 = archivoPdfBase64; }
    public void setFechaExpedicion(LocalDate fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}