/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "observacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observacion.findAll", query = "SELECT o FROM Observacion o"),
    @NamedQuery(name = "Observacion.findByIdObservacion", query = "SELECT o FROM Observacion o WHERE o.idObservacion = :idObservacion"),
    @NamedQuery(name = "Observacion.findByObservacionCliente", query = "SELECT o FROM Observacion o WHERE o.observacionCliente = :observacionCliente"),
    @NamedQuery(name = "Observacion.findByFechaObservacion", query = "SELECT o FROM Observacion o WHERE o.fechaObservacion = :fechaObservacion")})
public class Observacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idObservacion")
    private Long idObservacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "observacionCliente")
    private String observacionCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaObservacion")
    @Temporal(TemporalType.DATE)
    private Date fechaObservacion;
    @OneToMany(mappedBy = "idObservacion")
    private List<Cliente> clienteList;
    @JoinColumn(name = "idTipo_de_Observacion", referencedColumnName = "idTipo_de_Observacion")
    @ManyToOne(optional = false)
    private TipoDeObservacion idTipodeObservacion;

    public Observacion() {
    }

    public Observacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public Observacion(Long idObservacion, String observacionCliente, Date fechaObservacion) {
        this.idObservacion = idObservacion;
        this.observacionCliente = observacionCliente;
        this.fechaObservacion = fechaObservacion;
    }

    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public Date getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(Date fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public TipoDeObservacion getIdTipodeObservacion() {
        return idTipodeObservacion;
    }

    public void setIdTipodeObservacion(TipoDeObservacion idTipodeObservacion) {
        this.idTipodeObservacion = idTipodeObservacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObservacion != null ? idObservacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observacion)) {
            return false;
        }
        Observacion other = (Observacion) object;
        if ((this.idObservacion == null && other.idObservacion != null) || (this.idObservacion != null && !this.idObservacion.equals(other.idObservacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getObservacionCliente();
    }
    
}
