package org.mudanzasalegre.cabioTurno.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cambios_turno")
public class CambioTurno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @Column(nullable = false, length = 255)
    private String aceptante;

    @Column(name = "fecha_cambio", nullable = false)
    private String fechaCambio;
    
    @Column(name = "turno_a_cambiar", nullable = false, length = 255)
    private String turnoACambiar;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    public CambioTurno() {
        // Constructor por defecto
    }

    public CambioTurno(Long id, Usuario solicitante, String aceptante, String fechaCambio, String turnoACambiar,
                       String estado, LocalDateTime fechaSolicitud, LocalDateTime fechaResolucion) {
        this.id = id;
        this.solicitante = solicitante;
        this.aceptante = aceptante;
        this.fechaCambio = fechaCambio;
        this.turnoACambiar = turnoACambiar;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaResolucion = fechaResolucion;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public String getAceptante() {
        return aceptante;
    }

    public void setAceptante(String aceptante) {
        this.aceptante = aceptante;
    }

    public String getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(String fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getTurnoACambiar() {
        return turnoACambiar;
    }

    public void setTurnoACambiar(String turnoACambiar) {
        this.turnoACambiar = turnoACambiar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}
