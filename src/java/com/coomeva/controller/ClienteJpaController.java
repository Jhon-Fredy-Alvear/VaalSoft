/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.controller;

import com.coomeva.controller.exceptions.NonexistentEntityException;
import com.coomeva.controller.exceptions.PreexistingEntityException;
import com.coomeva.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coomeva.entities.Departamento;
import com.coomeva.entities.Barrio;
import com.coomeva.entities.Ciudad;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Creditoemprendimiento;
import com.coomeva.entities.Creditofortalecimiento;
import com.coomeva.entities.Empresa;
import com.coomeva.entities.RegistroCliente;
import com.coomeva.entities.Fiador;
import com.coomeva.entities.Genero;
import com.coomeva.entities.Observacion;
import com.coomeva.entities.RangoMonto;
import com.coomeva.entities.Regional;
import com.coomeva.entities.Salario;
import com.coomeva.entities.TipoDeCredito;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Departamento idDepartamento = cliente.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                cliente.setIdDepartamento(idDepartamento);
            }
            Barrio barrioidBarrio = cliente.getBarrioidBarrio();
            if (barrioidBarrio != null) {
                barrioidBarrio = em.getReference(barrioidBarrio.getClass(), barrioidBarrio.getIdBarrio());
                cliente.setBarrioidBarrio(barrioidBarrio);
            }
            Ciudad idCiudad = cliente.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                cliente.setIdCiudad(idCiudad);
            }
            Creditoemprendimiento idCreditoEmprendimiento = cliente.getIdCreditoEmprendimiento();
            if (idCreditoEmprendimiento != null) {
                idCreditoEmprendimiento = em.getReference(idCreditoEmprendimiento.getClass(), idCreditoEmprendimiento.getIdCreditoEmprendimiento());
                cliente.setIdCreditoEmprendimiento(idCreditoEmprendimiento);
            }
            Creditofortalecimiento idCreditoFortalecimiento = cliente.getIdCreditoFortalecimiento();
            if (idCreditoFortalecimiento != null) {
                idCreditoFortalecimiento = em.getReference(idCreditoFortalecimiento.getClass(), idCreditoFortalecimiento.getIdCreditoFortalecimiento());
                cliente.setIdCreditoFortalecimiento(idCreditoFortalecimiento);
            }
            Empresa idEmpresa = cliente.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa = em.getReference(idEmpresa.getClass(), idEmpresa.getIdEmpresa());
                cliente.setIdEmpresa(idEmpresa);
            }
            RegistroCliente idRegistroCliente = cliente.getIdRegistroCliente();
            if (idRegistroCliente != null) {
                idRegistroCliente = em.getReference(idRegistroCliente.getClass(), idRegistroCliente.getIdRegistroCliente());
                cliente.setIdRegistroCliente(idRegistroCliente);
            }
            Fiador idFiador = cliente.getIdFiador();
            if (idFiador != null) {
                idFiador = em.getReference(idFiador.getClass(), idFiador.getIdFiador());
                cliente.setIdFiador(idFiador);
            }
            Genero idGenero = cliente.getIdGenero();
            if (idGenero != null) {
                idGenero = em.getReference(idGenero.getClass(), idGenero.getIdGenero());
                cliente.setIdGenero(idGenero);
            }
            Observacion idObservacion = cliente.getIdObservacion();
            if (idObservacion != null) {
                idObservacion = em.getReference(idObservacion.getClass(), idObservacion.getIdObservacion());
                cliente.setIdObservacion(idObservacion);
            }
            RangoMonto idRangoMonto = cliente.getIdRangoMonto();
            if (idRangoMonto != null) {
                idRangoMonto = em.getReference(idRangoMonto.getClass(), idRangoMonto.getIdRangoMonto());
                cliente.setIdRangoMonto(idRangoMonto);
            }
            Regional idRegional = cliente.getIdRegional();
            if (idRegional != null) {
                idRegional = em.getReference(idRegional.getClass(), idRegional.getIdRegional());
                cliente.setIdRegional(idRegional);
            }
            Salario idSalario = cliente.getIdSalario();
            if (idSalario != null) {
                idSalario = em.getReference(idSalario.getClass(), idSalario.getIdSalario());
                cliente.setIdSalario(idSalario);
            }
            TipoDeCredito idTipoDeCredito = cliente.getIdTipoDeCredito();
            if (idTipoDeCredito != null) {
                idTipoDeCredito = em.getReference(idTipoDeCredito.getClass(), idTipoDeCredito.getIdTipoDeCredito());
                cliente.setIdTipoDeCredito(idTipoDeCredito);
            }
            em.persist(cliente);
            if (idDepartamento != null) {
                idDepartamento.getClienteList().add(cliente);
                idDepartamento = em.merge(idDepartamento);
            }
            if (barrioidBarrio != null) {
                barrioidBarrio.getClienteList().add(cliente);
                barrioidBarrio = em.merge(barrioidBarrio);
            }
            if (idCiudad != null) {
                idCiudad.getClienteList().add(cliente);
                idCiudad = em.merge(idCiudad);
            }
            if (idCreditoEmprendimiento != null) {
                idCreditoEmprendimiento.getClienteList().add(cliente);
                idCreditoEmprendimiento = em.merge(idCreditoEmprendimiento);
            }
            if (idCreditoFortalecimiento != null) {
                idCreditoFortalecimiento.getClienteList().add(cliente);
                idCreditoFortalecimiento = em.merge(idCreditoFortalecimiento);
            }
            if (idEmpresa != null) {
                idEmpresa.getClienteList().add(cliente);
                idEmpresa = em.merge(idEmpresa);
            }
            if (idRegistroCliente != null) {
                idRegistroCliente.getClienteList().add(cliente);
                idRegistroCliente = em.merge(idRegistroCliente);
            }
            if (idFiador != null) {
                idFiador.getClienteList().add(cliente);
                idFiador = em.merge(idFiador);
            }
            if (idGenero != null) {
                idGenero.getClienteList().add(cliente);
                idGenero = em.merge(idGenero);
            }
            if (idObservacion != null) {
                idObservacion.getClienteList().add(cliente);
                idObservacion = em.merge(idObservacion);
            }
            if (idRangoMonto != null) {
                idRangoMonto.getClienteList().add(cliente);
                idRangoMonto = em.merge(idRangoMonto);
            }
            if (idRegional != null) {
                idRegional.getClienteList().add(cliente);
                idRegional = em.merge(idRegional);
            }
            if (idSalario != null) {
                idSalario.getClienteList().add(cliente);
                idSalario = em.merge(idSalario);
            }
            if (idTipoDeCredito != null) {
                idTipoDeCredito.getClienteList().add(cliente);
                idTipoDeCredito = em.merge(idTipoDeCredito);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCliente(cliente.getIdCliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            Departamento idDepartamentoOld = persistentCliente.getIdDepartamento();
            Departamento idDepartamentoNew = cliente.getIdDepartamento();
            Barrio barrioidBarrioOld = persistentCliente.getBarrioidBarrio();
            Barrio barrioidBarrioNew = cliente.getBarrioidBarrio();
            Ciudad idCiudadOld = persistentCliente.getIdCiudad();
            Ciudad idCiudadNew = cliente.getIdCiudad();
            Creditoemprendimiento idCreditoEmprendimientoOld = persistentCliente.getIdCreditoEmprendimiento();
            Creditoemprendimiento idCreditoEmprendimientoNew = cliente.getIdCreditoEmprendimiento();
            Creditofortalecimiento idCreditoFortalecimientoOld = persistentCliente.getIdCreditoFortalecimiento();
            Creditofortalecimiento idCreditoFortalecimientoNew = cliente.getIdCreditoFortalecimiento();
            Empresa idEmpresaOld = persistentCliente.getIdEmpresa();
            Empresa idEmpresaNew = cliente.getIdEmpresa();
            RegistroCliente idRegistroClienteOld = persistentCliente.getIdRegistroCliente();
            RegistroCliente idRegistroClienteNew = cliente.getIdRegistroCliente();
            Fiador idFiadorOld = persistentCliente.getIdFiador();
            Fiador idFiadorNew = cliente.getIdFiador();
            Genero idGeneroOld = persistentCliente.getIdGenero();
            Genero idGeneroNew = cliente.getIdGenero();
            Observacion idObservacionOld = persistentCliente.getIdObservacion();
            Observacion idObservacionNew = cliente.getIdObservacion();
            RangoMonto idRangoMontoOld = persistentCliente.getIdRangoMonto();
            RangoMonto idRangoMontoNew = cliente.getIdRangoMonto();
            Regional idRegionalOld = persistentCliente.getIdRegional();
            Regional idRegionalNew = cliente.getIdRegional();
            Salario idSalarioOld = persistentCliente.getIdSalario();
            Salario idSalarioNew = cliente.getIdSalario();
            TipoDeCredito idTipoDeCreditoOld = persistentCliente.getIdTipoDeCredito();
            TipoDeCredito idTipoDeCreditoNew = cliente.getIdTipoDeCredito();
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                cliente.setIdDepartamento(idDepartamentoNew);
            }
            if (barrioidBarrioNew != null) {
                barrioidBarrioNew = em.getReference(barrioidBarrioNew.getClass(), barrioidBarrioNew.getIdBarrio());
                cliente.setBarrioidBarrio(barrioidBarrioNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                cliente.setIdCiudad(idCiudadNew);
            }
            if (idCreditoEmprendimientoNew != null) {
                idCreditoEmprendimientoNew = em.getReference(idCreditoEmprendimientoNew.getClass(), idCreditoEmprendimientoNew.getIdCreditoEmprendimiento());
                cliente.setIdCreditoEmprendimiento(idCreditoEmprendimientoNew);
            }
            if (idCreditoFortalecimientoNew != null) {
                idCreditoFortalecimientoNew = em.getReference(idCreditoFortalecimientoNew.getClass(), idCreditoFortalecimientoNew.getIdCreditoFortalecimiento());
                cliente.setIdCreditoFortalecimiento(idCreditoFortalecimientoNew);
            }
            if (idEmpresaNew != null) {
                idEmpresaNew = em.getReference(idEmpresaNew.getClass(), idEmpresaNew.getIdEmpresa());
                cliente.setIdEmpresa(idEmpresaNew);
            }
            if (idRegistroClienteNew != null) {
                idRegistroClienteNew = em.getReference(idRegistroClienteNew.getClass(), idRegistroClienteNew.getIdRegistroCliente());
                cliente.setIdRegistroCliente(idRegistroClienteNew);
            }
            if (idFiadorNew != null) {
                idFiadorNew = em.getReference(idFiadorNew.getClass(), idFiadorNew.getIdFiador());
                cliente.setIdFiador(idFiadorNew);
            }
            if (idGeneroNew != null) {
                idGeneroNew = em.getReference(idGeneroNew.getClass(), idGeneroNew.getIdGenero());
                cliente.setIdGenero(idGeneroNew);
            }
            if (idObservacionNew != null) {
                idObservacionNew = em.getReference(idObservacionNew.getClass(), idObservacionNew.getIdObservacion());
                cliente.setIdObservacion(idObservacionNew);
            }
            if (idRangoMontoNew != null) {
                idRangoMontoNew = em.getReference(idRangoMontoNew.getClass(), idRangoMontoNew.getIdRangoMonto());
                cliente.setIdRangoMonto(idRangoMontoNew);
            }
            if (idRegionalNew != null) {
                idRegionalNew = em.getReference(idRegionalNew.getClass(), idRegionalNew.getIdRegional());
                cliente.setIdRegional(idRegionalNew);
            }
            if (idSalarioNew != null) {
                idSalarioNew = em.getReference(idSalarioNew.getClass(), idSalarioNew.getIdSalario());
                cliente.setIdSalario(idSalarioNew);
            }
            if (idTipoDeCreditoNew != null) {
                idTipoDeCreditoNew = em.getReference(idTipoDeCreditoNew.getClass(), idTipoDeCreditoNew.getIdTipoDeCredito());
                cliente.setIdTipoDeCredito(idTipoDeCreditoNew);
            }
            cliente = em.merge(cliente);
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getClienteList().remove(cliente);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getClienteList().add(cliente);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            if (barrioidBarrioOld != null && !barrioidBarrioOld.equals(barrioidBarrioNew)) {
                barrioidBarrioOld.getClienteList().remove(cliente);
                barrioidBarrioOld = em.merge(barrioidBarrioOld);
            }
            if (barrioidBarrioNew != null && !barrioidBarrioNew.equals(barrioidBarrioOld)) {
                barrioidBarrioNew.getClienteList().add(cliente);
                barrioidBarrioNew = em.merge(barrioidBarrioNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getClienteList().remove(cliente);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getClienteList().add(cliente);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idCreditoEmprendimientoOld != null && !idCreditoEmprendimientoOld.equals(idCreditoEmprendimientoNew)) {
                idCreditoEmprendimientoOld.getClienteList().remove(cliente);
                idCreditoEmprendimientoOld = em.merge(idCreditoEmprendimientoOld);
            }
            if (idCreditoEmprendimientoNew != null && !idCreditoEmprendimientoNew.equals(idCreditoEmprendimientoOld)) {
                idCreditoEmprendimientoNew.getClienteList().add(cliente);
                idCreditoEmprendimientoNew = em.merge(idCreditoEmprendimientoNew);
            }
            if (idCreditoFortalecimientoOld != null && !idCreditoFortalecimientoOld.equals(idCreditoFortalecimientoNew)) {
                idCreditoFortalecimientoOld.getClienteList().remove(cliente);
                idCreditoFortalecimientoOld = em.merge(idCreditoFortalecimientoOld);
            }
            if (idCreditoFortalecimientoNew != null && !idCreditoFortalecimientoNew.equals(idCreditoFortalecimientoOld)) {
                idCreditoFortalecimientoNew.getClienteList().add(cliente);
                idCreditoFortalecimientoNew = em.merge(idCreditoFortalecimientoNew);
            }
            if (idEmpresaOld != null && !idEmpresaOld.equals(idEmpresaNew)) {
                idEmpresaOld.getClienteList().remove(cliente);
                idEmpresaOld = em.merge(idEmpresaOld);
            }
            if (idEmpresaNew != null && !idEmpresaNew.equals(idEmpresaOld)) {
                idEmpresaNew.getClienteList().add(cliente);
                idEmpresaNew = em.merge(idEmpresaNew);
            }
            if (idRegistroClienteOld != null && !idRegistroClienteOld.equals(idRegistroClienteNew)) {
                idRegistroClienteOld.getClienteList().remove(cliente);
                idRegistroClienteOld = em.merge(idRegistroClienteOld);
            }
            if (idRegistroClienteNew != null && !idRegistroClienteNew.equals(idRegistroClienteOld)) {
                idRegistroClienteNew.getClienteList().add(cliente);
                idRegistroClienteNew = em.merge(idRegistroClienteNew);
            }
            if (idFiadorOld != null && !idFiadorOld.equals(idFiadorNew)) {
                idFiadorOld.getClienteList().remove(cliente);
                idFiadorOld = em.merge(idFiadorOld);
            }
            if (idFiadorNew != null && !idFiadorNew.equals(idFiadorOld)) {
                idFiadorNew.getClienteList().add(cliente);
                idFiadorNew = em.merge(idFiadorNew);
            }
            if (idGeneroOld != null && !idGeneroOld.equals(idGeneroNew)) {
                idGeneroOld.getClienteList().remove(cliente);
                idGeneroOld = em.merge(idGeneroOld);
            }
            if (idGeneroNew != null && !idGeneroNew.equals(idGeneroOld)) {
                idGeneroNew.getClienteList().add(cliente);
                idGeneroNew = em.merge(idGeneroNew);
            }
            if (idObservacionOld != null && !idObservacionOld.equals(idObservacionNew)) {
                idObservacionOld.getClienteList().remove(cliente);
                idObservacionOld = em.merge(idObservacionOld);
            }
            if (idObservacionNew != null && !idObservacionNew.equals(idObservacionOld)) {
                idObservacionNew.getClienteList().add(cliente);
                idObservacionNew = em.merge(idObservacionNew);
            }
            if (idRangoMontoOld != null && !idRangoMontoOld.equals(idRangoMontoNew)) {
                idRangoMontoOld.getClienteList().remove(cliente);
                idRangoMontoOld = em.merge(idRangoMontoOld);
            }
            if (idRangoMontoNew != null && !idRangoMontoNew.equals(idRangoMontoOld)) {
                idRangoMontoNew.getClienteList().add(cliente);
                idRangoMontoNew = em.merge(idRangoMontoNew);
            }
            if (idRegionalOld != null && !idRegionalOld.equals(idRegionalNew)) {
                idRegionalOld.getClienteList().remove(cliente);
                idRegionalOld = em.merge(idRegionalOld);
            }
            if (idRegionalNew != null && !idRegionalNew.equals(idRegionalOld)) {
                idRegionalNew.getClienteList().add(cliente);
                idRegionalNew = em.merge(idRegionalNew);
            }
            if (idSalarioOld != null && !idSalarioOld.equals(idSalarioNew)) {
                idSalarioOld.getClienteList().remove(cliente);
                idSalarioOld = em.merge(idSalarioOld);
            }
            if (idSalarioNew != null && !idSalarioNew.equals(idSalarioOld)) {
                idSalarioNew.getClienteList().add(cliente);
                idSalarioNew = em.merge(idSalarioNew);
            }
            if (idTipoDeCreditoOld != null && !idTipoDeCreditoOld.equals(idTipoDeCreditoNew)) {
                idTipoDeCreditoOld.getClienteList().remove(cliente);
                idTipoDeCreditoOld = em.merge(idTipoDeCreditoOld);
            }
            if (idTipoDeCreditoNew != null && !idTipoDeCreditoNew.equals(idTipoDeCreditoOld)) {
                idTipoDeCreditoNew.getClienteList().add(cliente);
                idTipoDeCreditoNew = em.merge(idTipoDeCreditoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Departamento idDepartamento = cliente.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getClienteList().remove(cliente);
                idDepartamento = em.merge(idDepartamento);
            }
            Barrio barrioidBarrio = cliente.getBarrioidBarrio();
            if (barrioidBarrio != null) {
                barrioidBarrio.getClienteList().remove(cliente);
                barrioidBarrio = em.merge(barrioidBarrio);
            }
            Ciudad idCiudad = cliente.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getClienteList().remove(cliente);
                idCiudad = em.merge(idCiudad);
            }
            Creditoemprendimiento idCreditoEmprendimiento = cliente.getIdCreditoEmprendimiento();
            if (idCreditoEmprendimiento != null) {
                idCreditoEmprendimiento.getClienteList().remove(cliente);
                idCreditoEmprendimiento = em.merge(idCreditoEmprendimiento);
            }
            Creditofortalecimiento idCreditoFortalecimiento = cliente.getIdCreditoFortalecimiento();
            if (idCreditoFortalecimiento != null) {
                idCreditoFortalecimiento.getClienteList().remove(cliente);
                idCreditoFortalecimiento = em.merge(idCreditoFortalecimiento);
            }
            Empresa idEmpresa = cliente.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa.getClienteList().remove(cliente);
                idEmpresa = em.merge(idEmpresa);
            }
            RegistroCliente idRegistroCliente = cliente.getIdRegistroCliente();
            if (idRegistroCliente != null) {
                idRegistroCliente.getClienteList().remove(cliente);
                idRegistroCliente = em.merge(idRegistroCliente);
            }
            Fiador idFiador = cliente.getIdFiador();
            if (idFiador != null) {
                idFiador.getClienteList().remove(cliente);
                idFiador = em.merge(idFiador);
            }
            Genero idGenero = cliente.getIdGenero();
            if (idGenero != null) {
                idGenero.getClienteList().remove(cliente);
                idGenero = em.merge(idGenero);
            }
            Observacion idObservacion = cliente.getIdObservacion();
            if (idObservacion != null) {
                idObservacion.getClienteList().remove(cliente);
                idObservacion = em.merge(idObservacion);
            }
            RangoMonto idRangoMonto = cliente.getIdRangoMonto();
            if (idRangoMonto != null) {
                idRangoMonto.getClienteList().remove(cliente);
                idRangoMonto = em.merge(idRangoMonto);
            }
            Regional idRegional = cliente.getIdRegional();
            if (idRegional != null) {
                idRegional.getClienteList().remove(cliente);
                idRegional = em.merge(idRegional);
            }
            Salario idSalario = cliente.getIdSalario();
            if (idSalario != null) {
                idSalario.getClienteList().remove(cliente);
                idSalario = em.merge(idSalario);
            }
            TipoDeCredito idTipoDeCredito = cliente.getIdTipoDeCredito();
            if (idTipoDeCredito != null) {
                idTipoDeCredito.getClienteList().remove(cliente);
                idTipoDeCredito = em.merge(idTipoDeCredito);
            }
            em.remove(cliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
