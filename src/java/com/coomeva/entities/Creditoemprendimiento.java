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
@Table(name = "creditoemprendimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Creditoemprendimiento.findAll", query = "SELECT c FROM Creditoemprendimiento c"),
    @NamedQuery(name = "Creditoemprendimiento.findByIdCreditoEmprendimiento", query = "SELECT c FROM Creditoemprendimiento c WHERE c.idCreditoEmprendimiento = :idCreditoEmprendimiento"),
    @NamedQuery(name = "Creditoemprendimiento.findByIdeaNegocio", query = "SELECT c FROM Creditoemprendimiento c WHERE c.ideaNegocio = :ideaNegocio"),
    @NamedQuery(name = "Creditoemprendimiento.findByFechaEmprendimiento", query = "SELECT c FROM Creditoemprendimiento c WHERE c.fechaEmprendimiento = :fechaEmprendimiento")})
public class Creditoemprendimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCreditoEmprendimiento")
    private Long idCreditoEmprendimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "ideaNegocio")
    private String ideaNegocio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaEmprendimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaEmprendimiento;
    @OneToMany(mappedBy = "idCreditoEmprendimiento")
    private List<Cliente> clienteList;

    public Creditoemprendimiento() {
    }

    public Creditoemprendimiento(Long idCreditoEmprendimiento) {
        this.idCreditoEmprendimiento = idCreditoEmprendimiento;
    }

    public Creditoemprendimiento(Long idCreditoEmprendimiento, String ideaNegocio, Date fechaEmprendimiento) {
        this.idCreditoEmprendimiento = idCreditoEmprendimiento;
        this.ideaNegocio = ideaNegocio;
        this.fechaEmprendimiento = fechaEmprendimiento;
    }

    public Long getIdCreditoEmprendimiento() {
        return idCreditoEmprendimiento;
    }

    public void setIdCreditoEmprendimiento(Long idCreditoEmprendimiento) {
        this.idCreditoEmprendimiento = idCreditoEmprendimiento;
    }

    public String getIdeaNegocio() {
        return ideaNegocio;
    }

    public void setIdeaNegocio(String ideaNegocio) {
        this.ideaNegocio = ideaNegocio;
    }

    public Date getFechaEmprendimiento() {
        return fechaEmprendimiento;
    }

    public void setFechaEmprendimiento(Date fechaEmprendimiento) {
        this.fechaEmprendimiento = fechaEmprendimiento;
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
        hash += (idCreditoEmprendimiento != null ? idCreditoEmprendimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Creditoemprendimiento)) {
            return false;
        }
        Creditoemprendimiento other = (Creditoemprendimiento) object;
        if ((this.idCreditoEmprendimiento == null && other.idCreditoEmprendimiento != null) || (this.idCreditoEmprendimiento != null && !this.idCreditoEmprendimiento.equals(other.idCreditoEmprendimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getIdCreditoEmprendimiento();
    }
    
}
