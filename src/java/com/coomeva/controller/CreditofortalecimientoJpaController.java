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
import com.coomeva.entities.Creditofortalecimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class CreditofortalecimientoJpaController implements Serializable {

    public CreditofortalecimientoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Creditofortalecimiento creditofortalecimiento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (creditofortalecimiento.getClienteList() == null) {
            creditofortalecimiento.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : creditofortalecimiento.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            creditofortalecimiento.setClienteList(attachedClienteList);
            em.persist(creditofortalecimiento);
            for (Cliente clienteListCliente : creditofortalecimiento.getClienteList()) {
                Creditofortalecimiento oldIdCreditoFortalecimientoOfClienteListCliente = clienteListCliente.getIdCreditoFortalecimiento();
                clienteListCliente.setIdCreditoFortalecimiento(creditofortalecimiento);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdCreditoFortalecimientoOfClienteListCliente != null) {
                    oldIdCreditoFortalecimientoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdCreditoFortalecimientoOfClienteListCliente = em.merge(oldIdCreditoFortalecimientoOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCreditofortalecimiento(creditofortalecimiento.getIdCreditoFortalecimiento()) != null) {
                throw new PreexistingEntityException("Creditofortalecimiento " + creditofortalecimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Creditofortalecimiento creditofortalecimiento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Creditofortalecimiento persistentCreditofortalecimiento = em.find(Creditofortalecimiento.class, creditofortalecimiento.getIdCreditoFortalecimiento());
            List<Cliente> clienteListOld = persistentCreditofortalecimiento.getClienteList();
            List<Cliente> clienteListNew = creditofortalecimiento.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            creditofortalecimiento.setClienteList(clienteListNew);
            creditofortalecimiento = em.merge(creditofortalecimiento);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdCreditoFortalecimiento(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Creditofortalecimiento oldIdCreditoFortalecimientoOfClienteListNewCliente = clienteListNewCliente.getIdCreditoFortalecimiento();
                    clienteListNewCliente.setIdCreditoFortalecimiento(creditofortalecimiento);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdCreditoFortalecimientoOfClienteListNewCliente != null && !oldIdCreditoFortalecimientoOfClienteListNewCliente.equals(creditofortalecimiento)) {
                        oldIdCreditoFortalecimientoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdCreditoFortalecimientoOfClienteListNewCliente = em.merge(oldIdCreditoFortalecimientoOfClienteListNewCliente);
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
                Long id = creditofortalecimiento.getIdCreditoFortalecimiento();
                if (findCreditofortalecimiento(id) == null) {
                    throw new NonexistentEntityException("The creditofortalecimiento with id " + id + " no longer exists.");
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
            Creditofortalecimiento creditofortalecimiento;
            try {
                creditofortalecimiento = em.getReference(Creditofortalecimiento.class, id);
                creditofortalecimiento.getIdCreditoFortalecimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The creditofortalecimiento with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = creditofortalecimiento.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdCreditoFortalecimiento(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(creditofortalecimiento);
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

    public List<Creditofortalecimiento> findCreditofortalecimientoEntities() {
        return findCreditofortalecimientoEntities(true, -1, -1);
    }

    public List<Creditofortalecimiento> findCreditofortalecimientoEntities(int maxResults, int firstResult) {
        return findCreditofortalecimientoEntities(false, maxResults, firstResult);
    }

    private List<Creditofortalecimiento> findCreditofortalecimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Creditofortalecimiento.class));
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

    public Creditofortalecimiento findCreditofortalecimiento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Creditofortalecimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCreditofortalecimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Creditofortalecimiento> rt = cq.from(Creditofortalecimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
