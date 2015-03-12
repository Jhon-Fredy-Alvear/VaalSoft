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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "fiador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fiador.findAll", query = "SELECT f FROM Fiador f"),
    @NamedQuery(name = "Fiador.findByIdFiador", query = "SELECT f FROM Fiador f WHERE f.idFiador = :idFiador"),
    @NamedQuery(name = "Fiador.findByNombreFiador", query = "SELECT f FROM Fiador f WHERE f.nombreFiador = :nombreFiador"),
    @NamedQuery(name = "Fiador.findByNombreFiador2", query = "SELECT f FROM Fiador f WHERE f.nombreFiador2 = :nombreFiador2"),
    @NamedQuery(name = "Fiador.findByApellidoFiador", query = "SELECT f FROM Fiador f WHERE f.apellidoFiador = :apellidoFiador"),
    @NamedQuery(name = "Fiador.findByApellidoFiador2", query = "SELECT f FROM Fiador f WHERE f.apellidoFiador2 = :apellidoFiador2"),
    @NamedQuery(name = "Fiador.findByFechaNacimientoFiador", query = "SELECT f FROM Fiador f WHERE f.fechaNacimientoFiador = :fechaNacimientoFiador"),
    @NamedQuery(name = "Fiador.findByCedulaFiador", query = "SELECT f FROM Fiador f WHERE f.cedulaFiador = :cedulaFiador"),
    @NamedQuery(name = "Fiador.findByTelefonoFiador", query = "SELECT f FROM Fiador f WHERE f.telefonoFiador = :telefonoFiador"),
    @NamedQuery(name = "Fiador.findByCelularFiador", query = "SELECT f FROM Fiador f WHERE f.celularFiador = :celularFiador"),
    @NamedQuery(name = "Fiador.findByDireccionFiador", query = "SELECT f FROM Fiador f WHERE f.direccionFiador = :direccionFiador"),
    @NamedQuery(name = "Fiador.findByAsociadocoomevaFiador", query = "SELECT f FROM Fiador f WHERE f.asociadocoomevaFiador = :asociadocoomevaFiador")})
public class Fiador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFiador")
    private Long idFiador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nombreFiador")
    private String nombreFiador;
    @Size(max = 25)
    @Column(name = "nombreFiador2")
    private String nombreFiador2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "apellidoFiador")
    private String apellidoFiador;
    @Size(max = 25)
    @Column(name = "apellidoFiador2")
    private String apellidoFiador2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaNacimientoFiador")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimientoFiador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedulaFiador")
    private long cedulaFiador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefonoFiador")
    private long telefonoFiador;
    @Column(name = "celularFiador")
    private BigInteger celularFiador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "direccionFiador")
    private String direccionFiador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asociadocoomevaFiador")
    private boolean asociadocoomevaFiador;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFiador")
    private List<Cliente> clienteList;

    public Fiador() {
    }

    public Fiador(Long idFiador) {
        this.idFiador = idFiador;
    }

    public Fiador(Long idFiador, String nombreFiador, String apellidoFiador, Date fechaNacimientoFiador, long cedulaFiador, long telefonoFiador, String direccionFiador, boolean asociadocoomevaFiador) {
        this.idFiador = idFiador;
        this.nombreFiador = nombreFiador;
        this.apellidoFiador = apellidoFiador;
        this.fechaNacimientoFiador = fechaNacimientoFiador;
        this.cedulaFiador = cedulaFiador;
        this.telefonoFiador = telefonoFiador;
        this.direccionFiador = direccionFiador;
        this.asociadocoomevaFiador = asociadocoomevaFiador;
    }

    public Long getIdFiador() {
        return idFiador;
    }

    public void setIdFiador(Long idFiador) {
        this.idFiador = idFiador;
    }

    public String getNombreFiador() {
        return nombreFiador;
    }

    public void setNombreFiador(String nombreFiador) {
        this.nombreFiador = nombreFiador;
    }

    public String getNombreFiador2() {
        return nombreFiador2;
    }

    public void setNombreFiador2(String nombreFiador2) {
        this.nombreFiador2 = nombreFiador2;
    }

    public String getApellidoFiador() {
        return apellidoFiador;
    }

    public void setApellidoFiador(String apellidoFiador) {
        this.apellidoFiador = apellidoFiador;
    }

    public String getApellidoFiador2() {
        return apellidoFiador2;
    }

    public void setApellidoFiador2(String apellidoFiador2) {
        this.apellidoFiador2 = apellidoFiador2;
    }

    public Date getFechaNacimientoFiador() {
        return fechaNacimientoFiador;
    }

    public void setFechaNacimientoFiador(Date fechaNacimientoFiador) {
        this.fechaNacimientoFiador = fechaNacimientoFiador;
    }

    public long getCedulaFiador() {
        return cedulaFiador;
    }

    public void setCedulaFiador(long cedulaFiador) {
        this.cedulaFiador = cedulaFiador;
    }

    public long getTelefonoFiador() {
        return telefonoFiador;
    }

    public void setTelefonoFiador(long telefonoFiador) {
        this.telefonoFiador = telefonoFiador;
    }

    public BigInteger getCelularFiador() {
        return celularFiador;
    }

    public void setCelularFiador(BigInteger celularFiador) {
        this.celularFiador = celularFiador;
    }

    public String getDireccionFiador() {
        return direccionFiador;
    }

    public void setDireccionFiador(String direccionFiador) {
        this.direccionFiador = direccionFiador;
    }

    public boolean getAsociadocoomevaFiador() {
        return asociadocoomevaFiador;
    }

    public void setAsociadocoomevaFiador(boolean asociadocoomevaFiador) {
        this.asociadocoomevaFiador = asociadocoomevaFiador;
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

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFiador != null ? idFiador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fiador)) {
            return false;
        }
        Fiador other = (Fiador) object;
        if ((this.idFiador == null && other.idFiador != null) || (this.idFiador != null && !this.idFiador.equals(other.idFiador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getIdFiador();
    }
    
}
