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
@Table(name = "registro_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroCliente.findAll", query = "SELECT r FROM RegistroCliente r"),
    @NamedQuery(name = "RegistroCliente.findByIdRegistroCliente", query = "SELECT r FROM RegistroCliente r WHERE r.idRegistroCliente = :idRegistroCliente"),
    @NamedQuery(name = "RegistroCliente.findByCartaLaboral", query = "SELECT r FROM RegistroCliente r WHERE r.cartaLaboral = :cartaLaboral"),
    @NamedQuery(name = "RegistroCliente.findByDesprendibleDePago", query = "SELECT r FROM RegistroCliente r WHERE r.desprendibleDePago = :desprendibleDePago"),
    @NamedQuery(name = "RegistroCliente.findByContrato", query = "SELECT r FROM RegistroCliente r WHERE r.contrato = :contrato"),
    @NamedQuery(name = "RegistroCliente.findByDeclaracionRenta", query = "SELECT r FROM RegistroCliente r WHERE r.declaracionRenta = :declaracionRenta"),
    @NamedQuery(name = "RegistroCliente.findByVisitaNegocio", query = "SELECT r FROM RegistroCliente r WHERE r.visitaNegocio = :visitaNegocio")})
public class RegistroCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idRegistro_Cliente")
    private Long idRegistroCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "cartaLaboral")
    private String cartaLaboral;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "desprendibleDePago")
    private String desprendibleDePago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "contrato")
    private String contrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "declaracionRenta")
    private String declaracionRenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "visitaNegocio")
    private String visitaNegocio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRegistroCliente")
    private List<Cliente> clienteList;

    public RegistroCliente() {
    }

    public RegistroCliente(Long idRegistroCliente) {
        this.idRegistroCliente = idRegistroCliente;
    }

    public RegistroCliente(Long idRegistroCliente, String cartaLaboral, String desprendibleDePago, String contrato, String declaracionRenta, String visitaNegocio) {
        this.idRegistroCliente = idRegistroCliente;
        this.cartaLaboral = cartaLaboral;
        this.desprendibleDePago = desprendibleDePago;
        this.contrato = contrato;
        this.declaracionRenta = declaracionRenta;
        this.visitaNegocio = visitaNegocio;
    }

    public Long getIdRegistroCliente() {
        return idRegistroCliente;
    }

    public void setIdRegistroCliente(Long idRegistroCliente) {
        this.idRegistroCliente = idRegistroCliente;
    }

    public String getCartaLaboral() {
        return cartaLaboral;
    }

    public void setCartaLaboral(String cartaLaboral) {
        this.cartaLaboral = cartaLaboral;
    }

    public String getDesprendibleDePago() {
        return desprendibleDePago;
    }

    public void setDesprendibleDePago(String desprendibleDePago) {
        this.desprendibleDePago = desprendibleDePago;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getDeclaracionRenta() {
        return declaracionRenta;
    }

    public void setDeclaracionRenta(String declaracionRenta) {
        this.declaracionRenta = declaracionRenta;
    }

    public String getVisitaNegocio() {
        return visitaNegocio;
    }

    public void setVisitaNegocio(String visitaNegocio) {
        this.visitaNegocio = visitaNegocio;
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
        hash += (idRegistroCliente != null ? idRegistroCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroCliente)) {
            return false;
        }
        RegistroCliente other = (RegistroCliente) object;
        if ((this.idRegistroCliente == null && other.idRegistroCliente != null) || (this.idRegistroCliente != null && !this.idRegistroCliente.equals(other.idRegistroCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+getIdRegistroCliente();
    }
    
}
