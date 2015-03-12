/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GHOST
 */
@Entity
@Table(name = "tipo_de_observacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDeObservacion.findAll", query = "SELECT t FROM TipoDeObservacion t"),
    @NamedQuery(name = "TipoDeObservacion.findByIdTipodeObservacion", query = "SELECT t FROM TipoDeObservacion t WHERE t.idTipodeObservacion = :idTipodeObservacion"),
    @NamedQuery(name = "TipoDeObservacion.findByTipoObservacionCliente", query = "SELECT t FROM TipoDeObservacion t WHERE t.tipoObservacionCliente = :tipoObservacionCliente")})
public class TipoDeObservacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipo_de_Observacion")
    private Integer idTipodeObservacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "tipoObservacionCliente")
    private String tipoObservacionCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipodeObservacion")
    private List<Observacion> observacionList;

    public TipoDeObservacion() {
    }

    public TipoDeObservacion(Integer idTipodeObservacion) {
        this.idTipodeObservacion = idTipodeObservacion;
    }

    public TipoDeObservacion(Integer idTipodeObservacion, String tipoObservacionCliente) {
        this.idTipodeObservacion = idTipodeObservacion;
        this.tipoObservacionCliente = tipoObservacionCliente;
    }

    public Integer getIdTipodeObservacion() {
        return idTipodeObservacion;
    }

    public void setIdTipodeObservacion(Integer idTipodeObservacion) {
        this.idTipodeObservacion = idTipodeObservacion;
    }

    public String getTipoObservacionCliente() {
        return tipoObservacionCliente;
    }

    public void setTipoObservacionCliente(String tipoObservacionCliente) {
        this.tipoObservacionCliente = tipoObservacionCliente;
    }

    @XmlTransient
    public List<Observacion> getObservacionList() {
        return observacionList;
    }

    public void setObservacionList(List<Observacion> observacionList) {
        this.observacionList = observacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipodeObservacion != null ? idTipodeObservacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDeObservacion)) {
            return false;
        }
        TipoDeObservacion other = (TipoDeObservacion) object;
        if ((this.idTipodeObservacion == null && other.idTipodeObservacion != null) || (this.idTipodeObservacion != null && !this.idTipodeObservacion.equals(other.idTipodeObservacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTipoObservacionCliente();
    }
    
}
