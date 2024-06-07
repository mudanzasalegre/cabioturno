package org.mudanzasalegre.cabioTurno.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String email;
    private String username;

    @Column(name = "pass")
    private String pass;

    private Integer estatus;

    private Integer online;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultima_conexion")
    private Date ultimaConexion;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarioperfil", joinColumns = @JoinColumn(name = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idPerfil"))
    private List<Perfil> perfiles;

    public Usuario() {
        this.perfiles = new LinkedList<>();
    }

    public Usuario(String nombre, String email, String username, String password, Integer estatus, Integer online, Date fechaRegistro) {
        this.nombre = nombre;
        this.email = email;
        this.username = username;
        this.pass = password;
        this.estatus = estatus;
        this.online = online;
        this.fechaRegistro = fechaRegistro;
        this.ultimaConexion = new Date();
        this.perfiles = new LinkedList<>();
    }

    public void agregar(Perfil tempPerf) {
        if (perfiles == null) {
            perfiles = new LinkedList<>();
        }
        perfiles.add(tempPerf);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String password) {
        this.pass = password;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public Date getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(Date ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", username=" + username + ", pass=" + pass
                + ", estatus=" + estatus + ", online=" + online + ", fechaRegistro=" + fechaRegistro + ", ultimaConexion="
                + ultimaConexion + ", perfiles=" + perfiles + "]";
    }

    public String mostrarInfoUsuario() {
        return toString();
    }
}
