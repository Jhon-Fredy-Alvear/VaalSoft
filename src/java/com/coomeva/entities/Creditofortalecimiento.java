/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "creditofortalecimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Creditofortalecimiento.findAll", query = "SELECT c FROM Creditofortalecimiento c"),
    @NamedQuery(name = "Creditofortalecimiento.findByIdCreditoFortalecimiento", query = "SELECT c FROM Creditofortalecimiento c WHERE c.idCreditoFortalecimiento = :idCreditoFortalecimiento"),
    @NamedQuery(name = "Creditofortalecimiento.findByIdeaFortalecimiento", query = "SELECT c FROM Creditofortalecimiento c WHERE c.ideaFortalecimiento = :ideaFortalecimiento"),
    @NamedQuery(name = "Creditofortalecimiento.findByAntiguedadNegocio", query = "SELECT c FROM Creditofortalecimiento c WHERE c.antiguedadNegocio = :antiguedadNegocio")})
public class Creditofortalecimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCreditoFortalecimiento")
    private Long idCreditoFortalecimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "ideaFortalecimiento")
    private String ideaFortalecimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "antiguedadNegocio")
    private String antiguedadNegocio;
    @OneToMany(mappedBy = "idCreditoFortalecimiento")
    private List<Cliente> clienteList;

    public Creditofortalecimiento() {
    }

    public Creditofortalecimiento(Long idCreditoFortalecimiento) {
        this.idCreditoFortalecimiento = idCreditoFortalecimiento;
    }

    public Creditofortalecimiento(Long idCreditoFortalecimiento, String ideaFortalecimiento, String antiguedadNegocio) {
        this.idCreditoFortalecimiento = idCreditoFortalecimiento;
        this.ideaFortalecimiento = ideaFortalecimiento;
        this.antiguedadNegocio = antiguedadNegocio;
    }

    public Long getIdCreditoFortalecimiento() {
        return idCreditoFortalecimiento;
    }

    public void setIdCreditoFortalecimiento(Long idCreditoFortalecimiento) {
        this.idCreditoFortalecimiento = idCreditoFortalecimiento;
    }

    public String getIdeaFortalecimiento() {
        return ideaFortalecimiento;
    }

    public void setIdeaFortalecimiento(String ideaFortalecimiento) {
        this.ideaFortalecimiento = ideaFortalecimiento;
    }

    public String getAntiguedadNegocio() {
        return antiguedadNegocio;
    }

    public void setAntiguedadNegocio(String antiguedadNegocio) {
        this.antiguedadNegocio = antiguedadNegocio;
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
        hash += (idCreditoFortalecimiento != null ? idCreditoFortalecimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Creditofortalecimiento)) {
            return false;
        }
        Creditofortalecimiento other = (Creditofortalecimiento) object;
        if ((this.idCreditoFortalecimiento == null && other.idCreditoFortalecimiento != null) || (this.idCreditoFortalecimiento != null && !this.idCreditoFortalecimiento.equals(other.idCreditoFortalecimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getIdCreditoFortalecimiento();
    }
    
}
