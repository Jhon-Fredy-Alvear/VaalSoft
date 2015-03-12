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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GHOST
 */
@Entity
@Table(name = "rango_cuota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RangoCuota.findAll", query = "SELECT r FROM RangoCuota r"),
    @NamedQuery(name = "RangoCuota.findByIdRangoCuota", query = "SELECT r FROM RangoCuota r WHERE r.idRangoCuota = :idRangoCuota"),
    @NamedQuery(name = "RangoCuota.findByCuotas", query = "SELECT r FROM RangoCuota r WHERE r.cuotas = :cuotas")})
public class RangoCuota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idRango_Cuota")
    private Integer idRangoCuota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cuotas")
    private int cuotas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRangoCuota")
    private List<RangoMonto> rangoMontoList;

    public RangoCuota() {
    }

    public RangoCuota(Integer idRangoCuota) {
        this.idRangoCuota = idRangoCuota;
    }

    public RangoCuota(Integer idRangoCuota, int cuotas) {
        this.idRangoCuota = idRangoCuota;
        this.cuotas = cuotas;
    }

    public Integer getIdRangoCuota() {
        return idRangoCuota;
    }

    public void setIdRangoCuota(Integer idRangoCuota) {
        this.idRangoCuota = idRangoCuota;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    @XmlTransient
    public List<RangoMonto> getRangoMontoList() {
        return rangoMontoList;
    }

    public void setRangoMontoList(List<RangoMonto> rangoMontoList) {
        this.rangoMontoList = rangoMontoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRangoCuota != null ? idRangoCuota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RangoCuota)) {
            return false;
        }
        RangoCuota other = (RangoCuota) object;
        if ((this.idRangoCuota == null && other.idRangoCuota != null) || (this.idRangoCuota != null && !this.idRangoCuota.equals(other.idRangoCuota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getCuotas();
    }
    
}
