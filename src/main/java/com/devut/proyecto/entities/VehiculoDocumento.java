package com.devut.proyecto.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "vehiculo_documentos")
public class VehiculoDocumento {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @ManyToOne
	    @JoinColumn(name = "vehiculo_id", nullable = false)
	    @JsonBackReference
	    private Vehiculo vehiculo;

	    @ManyToOne
	    @JoinColumn(name = "documento_id", nullable = false)
	    private Documento documento;

	    @Column(name = "fecha_expedicion", nullable = false)
	    private LocalDate fechaExpedicion;

	    @Column(name = "fecha_vencimiento", nullable = false)
	    private LocalDate fechaVencimiento;

	    @Column(nullable = false)
	    private String estado = "En Verificación"; // DEFAULT 
	    
	    @Column(name = "archivo_pdf", columnDefinition = "LONGBLOB")
	    private byte[] archivoPdf;

	    public byte[] getArchivoPdf() { return archivoPdf; }
	    public void setArchivoPdf(byte[] archivoPdf) { this.archivoPdf = archivoPdf; }
	    
	    
	    public Integer getId() { return id; }
	    public Vehiculo getVehiculo() { return vehiculo; }
	    public Documento getDocumento() { return documento; }
	    public LocalDate getFechaExpedicion() { return fechaExpedicion; }
	    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
	    public String getEstado() { return estado; }

	   
	    public void setId(Integer id) { this.id = id; }
	    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
	    public void setDocumento(Documento documento) { this.documento = documento; }
	    public void setFechaExpedicion(LocalDate fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }
	    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
	    public void setEstado(String estado) { this.estado = estado; }
	}

