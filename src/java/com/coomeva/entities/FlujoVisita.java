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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "flujo_visita")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FlujoVisita.findAll", query = "SELECT f FROM FlujoVisita f"),
    @NamedQuery(name = "FlujoVisita.findByIdFlujoVisita", query = "SELECT f FROM FlujoVisita f WHERE f.idFlujoVisita = :idFlujoVisita"),
    @NamedQuery(name = "FlujoVisita.findByNumeroVisitas", query = "SELECT f FROM FlujoVisita f WHERE f.numeroVisitas = :numeroVisitas"),
    @NamedQuery(name = "FlujoVisita.findByFechaVisita", query = "SELECT f FROM FlujoVisita f WHERE f.fechaVisita = :fechaVisita")})
public class FlujoVisita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFlujo_Visita")
    private Long idFlujoVisita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroVisitas")
    private long numeroVisitas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaVisita")
    @Temporal(TemporalType.DATE)
    private Date fechaVisita;
    @ManyToMany(mappedBy = "flujoVisitaList")
    private List<Empleado> empleadoList;

    public FlujoVisita() {
    }

    public FlujoVisita(Long idFlujoVisita) {
        this.idFlujoVisita = idFlujoVisita;
    }

    public FlujoVisita(Long idFlujoVisita, long numeroVisitas, Date fechaVisita) {
        this.idFlujoVisita = idFlujoVisita;
        this.numeroVisitas = numeroVisitas;
        this.fechaVisita = fechaVisita;
    }

    public Long getIdFlujoVisita() {
        return idFlujoVisita;
    }

    public void setIdFlujoVisita(Long idFlujoVisita) {
        this.idFlujoVisita = idFlujoVisita;
    }

    public long getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(long numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFlujoVisita != null ? idFlujoVisita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlujoVisita)) {
            return false;
        }
        FlujoVisita other = (FlujoVisita) object;
        if ((this.idFlujoVisita == null && other.idFlujoVisita != null) || (this.idFlujoVisita != null && !this.idFlujoVisita.equals(other.idFlujoVisita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coomeva.entities.FlujoVisita[ idFlujoVisita=" + idFlujoVisita + " ]";
    }
    
}
