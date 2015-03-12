/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GHOST
 */
@Entity
@Table(name = "salario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salario.findAll", query = "SELECT s FROM Salario s"),
    @NamedQuery(name = "Salario.findByIdSalario", query = "SELECT s FROM Salario s WHERE s.idSalario = :idSalario"),
    @NamedQuery(name = "Salario.findBySalario", query = "SELECT s FROM Salario s WHERE s.salario = :salario")})
public class Salario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSalario")
    private Integer idSalario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "salario")
    private BigDecimal salario;
    @OneToMany(mappedBy = "idSalario")
    private List<Cliente> clienteList;

    public Salario() {
    }

    public Salario(Integer idSalario) {
        this.idSalario = idSalario;
    }

    public Salario(Integer idSalario, BigDecimal salario) {
        this.idSalario = idSalario;
        this.salario = salario;
    }

    public Integer getIdSalario() {
        return idSalario;
    }

    public void setIdSalario(Integer idSalario) {
        this.idSalario = idSalario;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
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
        hash += (idSalario != null ? idSalario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salario)) {
            return false;
        }
        Salario other = (Salario) object;
        if ((this.idSalario == null && other.idSalario != null) || (this.idSalario != null && !this.idSalario.equals(other.idSalario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getSalario();
    }
    
}
