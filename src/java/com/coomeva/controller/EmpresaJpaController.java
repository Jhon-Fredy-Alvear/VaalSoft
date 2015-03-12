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
import com.coomeva.entities.Barrio;
import com.coomeva.entities.Ciudad;
import com.coomeva.entities.Departamento;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Empresa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empresa.getClienteList() == null) {
            empresa.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barrio idBarrio = empresa.getIdBarrio();
            if (idBarrio != null) {
                idBarrio = em.getReference(idBarrio.getClass(), idBarrio.getIdBarrio());
                empresa.setIdBarrio(idBarrio);
            }
            Ciudad idCiudad = empresa.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                empresa.setIdCiudad(idCiudad);
            }
            Departamento idDepartamento = empresa.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                empresa.setIdDepartamento(idDepartamento);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : empresa.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            empresa.setClienteList(attachedClienteList);
            em.persist(empresa);
            if (idBarrio != null) {
                idBarrio.getEmpresaList().add(empresa);
                idBarrio = em.merge(idBarrio);
            }
            if (idCiudad != null) {
                idCiudad.getEmpresaList().add(empresa);
                idCiudad = em.merge(idCiudad);
            }
            if (idDepartamento != null) {
                idDepartamento.getEmpresaList().add(empresa);
                idDepartamento = em.merge(idDepartamento);
            }
            for (Cliente clienteListCliente : empresa.getClienteList()) {
                Empresa oldIdEmpresaOfClienteListCliente = clienteListCliente.getIdEmpresa();
                clienteListCliente.setIdEmpresa(empresa);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdEmpresaOfClienteListCliente != null) {
                    oldIdEmpresaOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdEmpresaOfClienteListCliente = em.merge(oldIdEmpresaOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpresa(empresa.getIdEmpresa()) != null) {
                throw new PreexistingEntityException("Empresa " + empresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getIdEmpresa());
            Barrio idBarrioOld = persistentEmpresa.getIdBarrio();
            Barrio idBarrioNew = empresa.getIdBarrio();
            Ciudad idCiudadOld = persistentEmpresa.getIdCiudad();
            Ciudad idCiudadNew = empresa.getIdCiudad();
            Departamento idDepartamentoOld = persistentEmpresa.getIdDepartamento();
            Departamento idDepartamentoNew = empresa.getIdDepartamento();
            List<Cliente> clienteListOld = persistentEmpresa.getClienteList();
            List<Cliente> clienteListNew = empresa.getClienteList();
            if (idBarrioNew != null) {
                idBarrioNew = em.getReference(idBarrioNew.getClass(), idBarrioNew.getIdBarrio());
                empresa.setIdBarrio(idBarrioNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                empresa.setIdCiudad(idCiudadNew);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                empresa.setIdDepartamento(idDepartamentoNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            empresa.setClienteList(clienteListNew);
            empresa = em.merge(empresa);
            if (idBarrioOld != null && !idBarrioOld.equals(idBarrioNew)) {
                idBarrioOld.getEmpresaList().remove(empresa);
                idBarrioOld = em.merge(idBarrioOld);
            }
            if (idBarrioNew != null && !idBarrioNew.equals(idBarrioOld)) {
                idBarrioNew.getEmpresaList().add(empresa);
                idBarrioNew = em.merge(idBarrioNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getEmpresaList().remove(empresa);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getEmpresaList().add(empresa);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getEmpresaList().remove(empresa);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getEmpresaList().add(empresa);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdEmpresa(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Empresa oldIdEmpresaOfClienteListNewCliente = clienteListNewCliente.getIdEmpresa();
                    clienteListNewCliente.setIdEmpresa(empresa);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdEmpresaOfClienteListNewCliente != null && !oldIdEmpresaOfClienteListNewCliente.equals(empresa)) {
                        oldIdEmpresaOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdEmpresaOfClienteListNewCliente = em.merge(oldIdEmpresaOfClienteListNewCliente);
                    }
                }
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
                Long id = empresa.getIdEmpresa();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getIdEmpresa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            Barrio idBarrio = empresa.getIdBarrio();
            if (idBarrio != null) {
                idBarrio.getEmpresaList().remove(empresa);
                idBarrio = em.merge(idBarrio);
            }
            Ciudad idCiudad = empresa.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getEmpresaList().remove(empresa);
                idCiudad = em.merge(idCiudad);
            }
            Departamento idDepartamento = empresa.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getEmpresaList().remove(empresa);
                idDepartamento = em.merge(idDepartamento);
            }
            List<Cliente> clienteList = empresa.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdEmpresa(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(empresa);
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

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
