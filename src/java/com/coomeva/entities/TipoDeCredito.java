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
@Table(name = "tipo_de_credito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDeCredito.findAll", query = "SELECT t FROM TipoDeCredito t"),
    @NamedQuery(name = "TipoDeCredito.findByIdTipoDeCredito", query = "SELECT t FROM TipoDeCredito t WHERE t.idTipoDeCredito = :idTipoDeCredito"),
    @NamedQuery(name = "TipoDeCredito.findByNombreTipoDeCredito", query = "SELECT t FROM TipoDeCredito t WHERE t.nombreTipoDeCredito = :nombreTipoDeCredito")})
public class TipoDeCredito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipo_De_Credito")
    private Integer idTipoDeCredito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nombreTipoDeCredito")
    private String nombreTipoDeCredito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoDeCredito")
    private List<Cliente> clienteList;

    public TipoDeCredito() {
    }

    public TipoDeCredito(Integer idTipoDeCredito) {
        this.idTipoDeCredito = idTipoDeCredito;
    }

    public TipoDeCredito(Integer idTipoDeCredito, String nombreTipoDeCredito) {
        this.idTipoDeCredito = idTipoDeCredito;
        this.nombreTipoDeCredito = nombreTipoDeCredito;
    }

    public Integer getIdTipoDeCredito() {
        return idTipoDeCredito;
    }

    public void setIdTipoDeCredito(Integer idTipoDeCredito) {
        this.idTipoDeCredito = idTipoDeCredito;
    }

    public String getNombreTipoDeCredito() {
        return nombreTipoDeCredito;
    }

    public void setNombreTipoDeCredito(String nombreTipoDeCredito) {
        this.nombreTipoDeCredito = nombreTipoDeCredito;
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
        hash += (idTipoDeCredito != null ? idTipoDeCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDeCredito)) {
            return false;
        }
        TipoDeCredito other = (TipoDeCredito) object;
        if ((this.idTipoDeCredito == null && other.idTipoDeCredito != null) || (this.idTipoDeCredito != null && !this.idTipoDeCredito.equals(other.idTipoDeCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreTipoDeCredito();
    }
    
}
