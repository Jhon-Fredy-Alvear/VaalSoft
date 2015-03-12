/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.controller;

import com.coomeva.controller.exceptions.IllegalOrphanException;
import com.coomeva.controller.exceptions.NonexistentEntityException;
import com.coomeva.controller.exceptions.PreexistingEntityException;
import com.coomeva.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.RegistroCliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class RegistroClienteJpaController implements Serializable {

    public RegistroClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroCliente registroCliente) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (registroCliente.getClienteList() == null) {
            registroCliente.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : registroCliente.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            registroCliente.setClienteList(attachedClienteList);
            em.persist(registroCliente);
            for (Cliente clienteListCliente : registroCliente.getClienteList()) {
                RegistroCliente oldIdRegistroClienteOfClienteListCliente = clienteListCliente.getIdRegistroCliente();
                clienteListCliente.setIdRegistroCliente(registroCliente);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdRegistroClienteOfClienteListCliente != null) {
                    oldIdRegistroClienteOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdRegistroClienteOfClienteListCliente = em.merge(oldIdRegistroClienteOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegistroCliente(registroCliente.getIdRegistroCliente()) != null) {
                throw new PreexistingEntityException("RegistroCliente " + registroCliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroCliente registroCliente) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegistroCliente persistentRegistroCliente = em.find(RegistroCliente.class, registroCliente.getIdRegistroCliente());
            List<Cliente> clienteListOld = persistentRegistroCliente.getClienteList();
            List<Cliente> clienteListNew = registroCliente.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idRegistroCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            registroCliente.setClienteList(clienteListNew);
            registroCliente = em.merge(registroCliente);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    RegistroCliente oldIdRegistroClienteOfClienteListNewCliente = clienteListNewCliente.getIdRegistroCliente();
                    clienteListNewCliente.setIdRegistroCliente(registroCliente);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdRegistroClienteOfClienteListNewCliente != null && !oldIdRegistroClienteOfClienteListNewCliente.equals(registroCliente)) {
                        oldIdRegistroClienteOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdRegistroClienteOfClienteListNewCliente = em.merge(oldIdRegistroClienteOfClienteListNewCliente);
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
                Long id = registroCliente.getIdRegistroCliente();
                if (findRegistroCliente(id) == null) {
                    throw new NonexistentEntityException("The registroCliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegistroCliente registroCliente;
            try {
                registroCliente = em.getReference(RegistroCliente.class, id);
                registroCliente.getIdRegistroCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroCliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = registroCliente.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RegistroCliente (" + registroCliente + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idRegistroCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(registroCliente);
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

    public List<RegistroCliente> findRegistroClienteEntities() {
        return findRegistroClienteEntities(true, -1, -1);
    }

    public List<RegistroCliente> findRegistroClienteEntities(int maxResults, int firstResult) {
        return findRegistroClienteEntities(false, maxResults, firstResult);
    }

    private List<RegistroCliente> findRegistroClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroCliente.class));
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

    public RegistroCliente findRegistroCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroCliente> rt = cq.from(RegistroCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
