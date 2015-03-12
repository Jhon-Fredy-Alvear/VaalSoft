/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GHOST
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByNombreCliente", query = "SELECT c FROM Cliente c WHERE c.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "Cliente.findByNombreCliente2", query = "SELECT c FROM Cliente c WHERE c.nombreCliente2 = :nombreCliente2"),
    @NamedQuery(name = "Cliente.findByApellidoCliente", query = "SELECT c FROM Cliente c WHERE c.apellidoCliente = :apellidoCliente"),
    @NamedQuery(name = "Cliente.findByApellidoCliente2", query = "SELECT c FROM Cliente c WHERE c.apellidoCliente2 = :apellidoCliente2"),
    @NamedQuery(name = "Cliente.findByFechaNacimientoCliente", query = "SELECT c FROM Cliente c WHERE c.fechaNacimientoCliente = :fechaNacimientoCliente"),
    @NamedQuery(name = "Cliente.findByCedulaCliente", query = "SELECT c FROM Cliente c WHERE c.cedulaCliente = :cedulaCliente"),
    @NamedQuery(name = "Cliente.findByTelefonoCliente", query = "SELECT c FROM Cliente c WHERE c.telefonoCliente = :telefonoCliente"),
    @NamedQuery(name = "Cliente.findByCelularCliente", query = "SELECT c FROM Cliente c WHERE c.celularCliente = :celularCliente"),
    @NamedQuery(name = "Cliente.findByDireccionCliente", query = "SELECT c FROM Cliente c WHERE c.direccionCliente = :direccionCliente"),
    @NamedQuery(name = "Cliente.findByEmailCliente", query = "SELECT c FROM Cliente c WHERE c.emailCliente = :emailCliente"),
    @NamedQuery(name = "Cliente.findByCargoEmprendimiento", query = "SELECT c FROM Cliente c WHERE c.cargoEmprendimiento = :cargoEmprendimiento"),
    @NamedQuery(name = "Cliente.findByAsociadoCoomeva", query = "SELECT c FROM Cliente c WHERE c.asociadoCoomeva = :asociadoCoomeva")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCliente")
    private Long idCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nombreCliente")
    private String nombreCliente;
    @Size(max = 25)
    @Column(name = "nombreCliente2")
    private String nombreCliente2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "apellidoCliente")
    private String apellidoCliente;
    @Size(max = 25)
    @Column(name = "apellidoCliente2")
    private String apellidoCliente2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaNacimientoCliente")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimientoCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedulaCliente")
    private long cedulaCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefonoCliente")
    private long telefonoCliente;
    @Column(name = "celularCliente")
    private BigInteger celularCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "direccionCliente")
    private String direccionCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "emailCliente")
    private String emailCliente;
    @Size(max = 25)
    @Column(name = "cargoEmprendimiento")
    private String cargoEmprendimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asociadoCoomeva")
    private boolean asociadoCoomeva;
    @JoinColumn(name = "idDepartamento", referencedColumnName = "idDepartamento")
    @ManyToOne(optional = false)
    private Departamento idDepartamento;
    @JoinColumn(name = "Barrio_idBarrio", referencedColumnName = "idBarrio")
    @ManyToOne(optional = false)
    private Barrio barrioidBarrio;
    @JoinColumn(name = "idCiudad", referencedColumnName = "idCiudad")
    @ManyToOne(optional = false)
    private Ciudad idCiudad;
    @JoinColumn(name = "idCreditoEmprendimiento", referencedColumnName = "idCreditoEmprendimiento")
    @ManyToOne
    private Creditoemprendimiento idCreditoEmprendimiento;
    @JoinColumn(name = "idCreditoFortalecimiento", referencedColumnName = "idCreditoFortalecimiento")
    @ManyToOne
    private Creditofortalecimiento idCreditoFortalecimiento;
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    @ManyToOne
    private Empresa idEmpresa;
    @JoinColumn(name = "idRegistro_Cliente", referencedColumnName = "idRegistro_Cliente")
    @ManyToOne(optional = false)
    private RegistroCliente idRegistroCliente;
    @JoinColumn(name = "idFiador", referencedColumnName = "idFiador")
    @ManyToOne(optional = false)
    private Fiador idFiador;
    @JoinColumn(name = "idGenero", referencedColumnName = "idGenero")
    @ManyToOne(optional = false)
    private Genero idGenero;
    @JoinColumn(name = "idObservacion", referencedColumnName = "idObservacion")
    @ManyToOne
    private Observacion idObservacion;
    @JoinColumn(name = "idRango_Monto", referencedColumnName = "idRango_Monto")
    @ManyToOne(optional = false)
    private RangoMonto idRangoMonto;
    @JoinColumn(name = "idRegional", referencedColumnName = "idRegional")
    @ManyToOne(optional = false)
    private Regional idRegional;
    @JoinColumn(name = "idSalario", referencedColumnName = "idSalario")
    @ManyToOne
    private Salario idSalario;
    @JoinColumn(name = "idTipo_De_Credito", referencedColumnName = "idTipo_De_Credito")
    @ManyToOne(optional = false)
    private TipoDeCredito idTipoDeCredito;

    public Cliente() {
    }

    public Cliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Long idCliente, String nombreCliente, String apellidoCliente, Date fechaNacimientoCliente, long cedulaCliente, long telefonoCliente, String direccionCliente, String emailCliente, boolean asociadoCoomeva) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.fechaNacimientoCliente = fechaNacimientoCliente;
        this.cedulaCliente = cedulaCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.emailCliente = emailCliente;
        this.asociadoCoomeva = asociadoCoomeva;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreCliente2() {
        return nombreCliente2;
    }

    public void setNombreCliente2(String nombreCliente2) {
        this.nombreCliente2 = nombreCliente2;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getApellidoCliente2() {
        return apellidoCliente2;
    }

    public void setApellidoCliente2(String apellidoCliente2) {
        this.apellidoCliente2 = apellidoCliente2;
    }

    public Date getFechaNacimientoCliente() {
        return fechaNacimientoCliente;
    }

    public void setFechaNacimientoCliente(Date fechaNacimientoCliente) {
        this.fechaNacimientoCliente = fechaNacimientoCliente;
    }

    public long getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(long cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public long getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(long telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public BigInteger getCelularCliente() {
        return celularCliente;
    }

    public void setCelularCliente(BigInteger celularCliente) {
        this.celularCliente = celularCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getCargoEmprendimiento() {
        return cargoEmprendimiento;
    }

    public void setCargoEmprendimiento(String cargoEmprendimiento) {
        this.cargoEmprendimiento = cargoEmprendimiento;
    }

    public boolean getAsociadoCoomeva() {
        return asociadoCoomeva;
    }

    public void setAsociadoCoomeva(boolean asociadoCoomeva) {
        this.asociadoCoomeva = asociadoCoomeva;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Barrio getBarrioidBarrio() {
        return barrioidBarrio;
    }

    public void setBarrioidBarrio(Barrio barrioidBarrio) {
        this.barrioidBarrio = barrioidBarrio;
    }

    public Ciudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Creditoemprendimiento getIdCreditoEmprendimiento() {
        return idCreditoEmprendimiento;
    }

    public void setIdCreditoEmprendimiento(Creditoemprendimiento idCreditoEmprendimiento) {
        this.idCreditoEmprendimiento = idCreditoEmprendimiento;
    }

    public Creditofortalecimiento getIdCreditoFortalecimiento() {
        return idCreditoFortalecimiento;
    }

    public void setIdCreditoFortalecimiento(Creditofortalecimiento idCreditoFortalecimiento) {
        this.idCreditoFortalecimiento = idCreditoFortalecimiento;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public RegistroCliente getIdRegistroCliente() {
        return idRegistroCliente;
    }

    public void setIdRegistroCliente(RegistroCliente idRegistroCliente) {
        this.idRegistroCliente = idRegistroCliente;
    }

    public Fiador getIdFiador() {
        return idFiador;
    }

    public void setIdFiador(Fiador idFiador) {
        this.idFiador = idFiador;
    }

    public Genero getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Genero idGenero) {
        this.idGenero = idGenero;
    }

    public Observacion getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Observacion idObservacion) {
        this.idObservacion = idObservacion;
    }

    public RangoMonto getIdRangoMonto() {
        return idRangoMonto;
    }

    public void setIdRangoMonto(RangoMonto idRangoMonto) {
        this.idRangoMonto = idRangoMonto;
    }

    public Regional getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(Regional idRegional) {
        this.idRegional = idRegional;
    }

    public Salario getIdSalario() {
        return idSalario;
    }

    public void setIdSalario(Salario idSalario) {
        this.idSalario = idSalario;
    }

    public TipoDeCredito getIdTipoDeCredito() {
        return idTipoDeCredito;
    }

    public void setIdTipoDeCredito(TipoDeCredito idTipoDeCredito) {
        this.idTipoDeCredito = idTipoDeCredito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coomeva.entities.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
