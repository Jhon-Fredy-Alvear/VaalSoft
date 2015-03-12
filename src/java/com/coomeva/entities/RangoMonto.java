/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GHOST
 */
@Entity
@Table(name = "rango_monto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RangoMonto.findAll", query = "SELECT r FROM RangoMonto r"),
    @NamedQuery(name = "RangoMonto.findByIdRangoMonto", query = "SELECT r FROM RangoMonto r WHERE r.idRangoMonto = :idRangoMonto"),
    @NamedQuery(name = "RangoMonto.findByMonto", query = "SELECT r FROM RangoMonto r WHERE r.monto = :monto"),
    @NamedQuery(name = "RangoMonto.findByFechaDelMonto", query = "SELECT r FROM RangoMonto r WHERE r.fechaDelMonto = :fechaDelMonto")})
public class RangoMonto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idRango_Monto")
    private Long idRangoMonto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDelMonto")
    @Temporal(TemporalType.DATE)
    private Date fechaDelMonto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRangoMonto")
    private List<Cliente> clienteList;
    @JoinColumn(name = "idRango_Cuota", referencedColumnName = "idRango_Cuota")
    @ManyToOne(optional = false)
    private RangoCuota idRangoCuota;

    public RangoMonto() {
    }

    public RangoMonto(Long idRangoMonto) {
        this.idRangoMonto = idRangoMonto;
    }

    public RangoMonto(Long idRangoMonto, BigDecimal monto, Date fechaDelMonto) {
        this.idRangoMonto = idRangoMonto;
        this.monto = monto;
        this.fechaDelMonto = fechaDelMonto;
    }

    public Long getIdRangoMonto() {
        return idRangoMonto;
    }

    public void setIdRangoMonto(Long idRangoMonto) {
        this.idRangoMonto = idRangoMonto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaDelMonto() {
        return fechaDelMonto;
    }

    public void setFechaDelMonto(Date fechaDelMonto) {
        this.fechaDelMonto = fechaDelMonto;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public RangoCuota getIdRangoCuota() {
        return idRangoCuota;
    }

    public void setIdRangoCuota(RangoCuota idRangoCuota) {
        this.idRangoCuota = idRangoCuota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRangoMonto != null ? idRangoMonto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RangoMonto)) {
            return false;
        }
        RangoMonto other = (RangoMonto) object;
        if ((this.idRangoMonto == null && other.idRangoMonto != null) || (this.idRangoMonto != null && !this.idRangoMonto.equals(other.idRangoMonto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getMonto();
    }
    
}
