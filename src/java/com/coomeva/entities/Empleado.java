/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GHOST
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdEmpleado", query = "SELECT e FROM Empleado e WHERE e.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Empleado.findByEmailEmpleado", query = "SELECT e FROM Empleado e WHERE e.emailEmpleado = :emailEmpleado"),
    @NamedQuery(name = "Empleado.findByContraseniaEmpleado", query = "SELECT e FROM Empleado e WHERE e.contraseniaEmpleado = :contraseniaEmpleado"),
    @NamedQuery(name = "Empleado.findByNombreEmpleado", query = "SELECT e FROM Empleado e WHERE e.nombreEmpleado = :nombreEmpleado"),
    @NamedQuery(name = "Empleado.findByNombreEmpleado2", query = "SELECT e FROM Empleado e WHERE e.nombreEmpleado2 = :nombreEmpleado2"),
    @NamedQuery(name = "Empleado.findByApellidoEmpleado", query = "SELECT e FROM Empleado e WHERE e.apellidoEmpleado = :apellidoEmpleado"),
    @NamedQuery(name = "Empleado.findByApellidoEmpleado2", query = "SELECT e FROM Empleado e WHERE e.apellidoEmpleado2 = :apellidoEmpleado2"),
    @NamedQuery(name = "Empleado.findByCedulaEmpleado", query = "SELECT e FROM Empleado e WHERE e.cedulaEmpleado = :cedulaEmpleado"),
    @NamedQuery(name = "Empleado.findByTelefonoEmpleado", query = "SELECT e FROM Empleado e WHERE e.telefonoEmpleado = :telefonoEmpleado"),
    @NamedQuery(name = "Empleado.findByCelularEmpleado", query = "SELECT e FROM Empleado e WHERE e.celularEmpleado = :celularEmpleado"),
    @NamedQuery(name = "Empleado.findByFechaNacimientoEmpleado", query = "SELECT e FROM Empleado e WHERE e.fechaNacimientoEmpleado = :fechaNacimientoEmpleado"),
    @NamedQuery(name = "Empleado.findByDireccionEmpleado", query = "SELECT e FROM Empleado e WHERE e.direccionEmpleado = :direccionEmpleado")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEmpleado")
    private Long idEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "emailEmpleado")
    private String emailEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "contraseniaEmpleado")
    private String contraseniaEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nombreEmpleado")
    private String nombreEmpleado;
    @Size(max = 15)
    @Column(name = "nombreEmpleado2")
    private String nombreEmpleado2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "apellidoEmpleado")
    private String apellidoEmpleado;
    @Size(max = 15)
    @Column(name = "apellidoEmpleado2")
    private String apellidoEmpleado2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedulaEmpleado")
    private long cedulaEmpleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefonoEmpleado")
    private long telefonoEmpleado;
    @Column(name = "celularEmpleado")
    private BigInteger celularEmpleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaNacimientoEmpleado")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimientoEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "direccionEmpleado")
    private String direccionEmpleado;
    @JoinTable(name = "empleado_has_flujo_visita", joinColumns = {
        @JoinColumn(name = "idEmpleado", referencedColumnName = "idEmpleado")}, inverseJoinColumns = {
        @JoinColumn(name = "idFlujo_Visita", referencedColumnName = "idFlujo_Visita")})
    @ManyToMany
    private List<FlujoVisita> flujoVisitaList;
    @JoinColumn(name = "idBarrio", referencedColumnName = "idBarrio")
    @ManyToOne(optional = false)
    private Barrio idBarrio;
    @JoinColumn(name = "idCiudad", referencedColumnName = "idCiudad")
    @ManyToOne(optional = false)
    private Ciudad idCiudad;
    @JoinColumn(name = "idDepartamento", referencedColumnName = "idDepartamento")
    @ManyToOne(optional = false)
    private Departamento idDepartamento;
    @JoinColumn(name = "idGenero", referencedColumnName = "idGenero")
    @ManyToOne(optional = false)
    private Genero idGenero;
    @JoinColumn(name = "idRegional", referencedColumnName = "idRegional")
    @ManyToOne(optional = false)
    private Regional idRegional;
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    @ManyToOne(optional = false)
    private Rol idRol;

    public Empleado() {
    }

    public Empleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado(Long idEmpleado, String emailEmpleado, String contraseniaEmpleado, String nombreEmpleado, String apellidoEmpleado, long cedulaEmpleado, long telefonoEmpleado, Date fechaNacimientoEmpleado, String direccionEmpleado) {
        this.idEmpleado = idEmpleado;
        this.emailEmpleado = emailEmpleado;
        this.contraseniaEmpleado = contraseniaEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.cedulaEmpleado = cedulaEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.fechaNacimientoEmpleado = fechaNacimientoEmpleado;
        this.direccionEmpleado = direccionEmpleado;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public String getContraseniaEmpleado() {
        return contraseniaEmpleado;
    }

    public void setContraseniaEmpleado(String contraseniaEmpleado) {
        this.contraseniaEmpleado = contraseniaEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getNombreEmpleado2() {
        return nombreEmpleado2;
    }

    public void setNombreEmpleado2(String nombreEmpleado2) {
        this.nombreEmpleado2 = nombreEmpleado2;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getApellidoEmpleado2() {
        return apellidoEmpleado2;
    }

    public void setApellidoEmpleado2(String apellidoEmpleado2) {
        this.apellidoEmpleado2 = apellidoEmpleado2;
    }

    public long getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(long cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public long getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(long telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public BigInteger getCelularEmpleado() {
        return celularEmpleado;
    }

    public void setCelularEmpleado(BigInteger celularEmpleado) {
        this.celularEmpleado = celularEmpleado;
    }

    public Date getFechaNacimientoEmpleado() {
        return fechaNacimientoEmpleado;
    }

    public void setFechaNacimientoEmpleado(Date fechaNacimientoEmpleado) {
        this.fechaNacimientoEmpleado = fechaNacimientoEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    @XmlTransient
    public List<FlujoVisita> getFlujoVisitaList() {
        return flujoVisitaList;
    }

    public void setFlujoVisitaList(List<FlujoVisita> flujoVisitaList) {
        this.flujoVisitaList = flujoVisitaList;
    }

    public Barrio getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(Barrio idBarrio) {
        this.idBarrio = idBarrio;
    }

    public Ciudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Genero getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Genero idGenero) {
        this.idGenero = idGenero;
    }

    public Regional getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(Regional idRegional) {
        this.idRegional = idRegional;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coomeva.entities.Empleado[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
