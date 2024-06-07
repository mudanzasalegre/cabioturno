package org.mudanzasalegre.cabioTurno.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false)
    private Integer referenciaId;
    
    public Notificacion() {
					// TODO Auto-generated constructor stub
				}

    public Notificacion(Long id, String tipo, String descripcion, Usuario usuario, LocalDateTime fechaHora,
						String estado, Integer referenciaId) {
					super();
					this.id = id;
					this.tipo = tipo;
					this.descripcion = descripcion;
					this.usuario = usuario;
					this.fechaHora = fechaHora;
					this.estado = estado;
					this.referenciaId = referenciaId;
				}



				// Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Integer referenciaId) {
        this.referenciaId = referenciaId;
    }
}
