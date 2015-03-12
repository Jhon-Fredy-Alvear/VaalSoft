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
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Creditoemprendimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class CreditoemprendimientoJpaController implements Serializable {

    public CreditoemprendimientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Creditoemprendimiento creditoemprendimiento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (creditoemprendimiento.getClienteList() == null) {
            creditoemprendimiento.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : creditoemprendimiento.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            creditoemprendimiento.setClienteList(attachedClienteList);
            em.persist(creditoemprendimiento);
            for (Cliente clienteListCliente : creditoemprendimiento.getClienteList()) {
                Creditoemprendimiento oldIdCreditoEmprendimientoOfClienteListCliente = clienteListCliente.getIdCreditoEmprendimiento();
                clienteListCliente.setIdCreditoEmprendimiento(creditoemprendimiento);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdCreditoEmprendimientoOfClienteListCliente != null) {
                    oldIdCreditoEmprendimientoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdCreditoEmprendimientoOfClienteListCliente = em.merge(oldIdCreditoEmprendimientoOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCreditoemprendimiento(creditoemprendimiento.getIdCreditoEmprendimiento()) != null) {
                throw new PreexistingEntityException("Creditoemprendimiento " + creditoemprendimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Creditoemprendimiento creditoemprendimiento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Creditoemprendimiento persistentCreditoemprendimiento = em.find(Creditoemprendimiento.class, creditoemprendimiento.getIdCreditoEmprendimiento());
            List<Cliente> clienteListOld = persistentCreditoemprendimiento.getClienteList();
            List<Cliente> clienteListNew = creditoemprendimiento.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            creditoemprendimiento.setClienteList(clienteListNew);
            creditoemprendimiento = em.merge(creditoemprendimiento);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdCreditoEmprendimiento(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Creditoemprendimiento oldIdCreditoEmprendimientoOfClienteListNewCliente = clienteListNewCliente.getIdCreditoEmprendimiento();
                    clienteListNewCliente.setIdCreditoEmprendimiento(creditoemprendimiento);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdCreditoEmprendimientoOfClienteListNewCliente != null && !oldIdCreditoEmprendimientoOfClienteListNewCliente.equals(creditoemprendimiento)) {
                        oldIdCreditoEmprendimientoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdCreditoEmprendimientoOfClienteListNewCliente = em.merge(oldIdCreditoEmprendimientoOfClienteListNewCliente);
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
                Long id = creditoemprendimiento.getIdCreditoEmprendimiento();
                if (findCreditoemprendimiento(id) == null) {
                    throw new NonexistentEntityException("The creditoemprendimiento with id " + id + " no longer exists.");
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
            Creditoemprendimiento creditoemprendimiento;
            try {
                creditoemprendimiento = em.getReference(Creditoemprendimiento.class, id);
                creditoemprendimiento.getIdCreditoEmprendimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The creditoemprendimiento with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = creditoemprendimiento.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdCreditoEmprendimiento(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(creditoemprendimiento);
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

    public List<Creditoemprendimiento> findCreditoemprendimientoEntities() {
        return findCreditoemprendimientoEntities(true, -1, -1);
    }

    public List<Creditoemprendimiento> findCreditoemprendimientoEntities(int maxResults, int firstResult) {
        return findCreditoemprendimientoEntities(false, maxResults, firstResult);
    }

    private List<Creditoemprendimiento> findCreditoemprendimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Creditoemprendimiento.class));
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

    public Creditoemprendimiento findCreditoemprendimiento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Creditoemprendimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCreditoemprendimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Creditoemprendimiento> rt = cq.from(Creditoemprendimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
