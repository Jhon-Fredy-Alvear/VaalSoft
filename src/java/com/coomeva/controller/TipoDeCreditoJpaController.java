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
import com.coomeva.entities.TipoDeCredito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class TipoDeCreditoJpaController implements Serializable {

    public TipoDeCreditoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDeCredito tipoDeCredito) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoDeCredito.getClienteList() == null) {
            tipoDeCredito.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : tipoDeCredito.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            tipoDeCredito.setClienteList(attachedClienteList);
            em.persist(tipoDeCredito);
            for (Cliente clienteListCliente : tipoDeCredito.getClienteList()) {
                TipoDeCredito oldIdTipoDeCreditoOfClienteListCliente = clienteListCliente.getIdTipoDeCredito();
                clienteListCliente.setIdTipoDeCredito(tipoDeCredito);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdTipoDeCreditoOfClienteListCliente != null) {
                    oldIdTipoDeCreditoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdTipoDeCreditoOfClienteListCliente = em.merge(oldIdTipoDeCreditoOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoDeCredito(tipoDeCredito.getIdTipoDeCredito()) != null) {
                throw new PreexistingEntityException("TipoDeCredito " + tipoDeCredito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDeCredito tipoDeCredito) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoDeCredito persistentTipoDeCredito = em.find(TipoDeCredito.class, tipoDeCredito.getIdTipoDeCredito());
            List<Cliente> clienteListOld = persistentTipoDeCredito.getClienteList();
            List<Cliente> clienteListNew = tipoDeCredito.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idTipoDeCredito field is not nullable.");
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
            tipoDeCredito.setClienteList(clienteListNew);
            tipoDeCredito = em.merge(tipoDeCredito);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    TipoDeCredito oldIdTipoDeCreditoOfClienteListNewCliente = clienteListNewCliente.getIdTipoDeCredito();
                    clienteListNewCliente.setIdTipoDeCredito(tipoDeCredito);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdTipoDeCreditoOfClienteListNewCliente != null && !oldIdTipoDeCreditoOfClienteListNewCliente.equals(tipoDeCredito)) {
                        oldIdTipoDeCreditoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdTipoDeCreditoOfClienteListNewCliente = em.merge(oldIdTipoDeCreditoOfClienteListNewCliente);
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
                Integer id = tipoDeCredito.getIdTipoDeCredito();
                if (findTipoDeCredito(id) == null) {
                    throw new NonexistentEntityException("The tipoDeCredito with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoDeCredito tipoDeCredito;
            try {
                tipoDeCredito = em.getReference(TipoDeCredito.class, id);
                tipoDeCredito.getIdTipoDeCredito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDeCredito with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = tipoDeCredito.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoDeCredito (" + tipoDeCredito + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idTipoDeCredito field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoDeCredito);
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

    public List<TipoDeCredito> findTipoDeCreditoEntities() {
        return findTipoDeCreditoEntities(true, -1, -1);
    }

    public List<TipoDeCredito> findTipoDeCreditoEntities(int maxResults, int firstResult) {
        return findTipoDeCreditoEntities(false, maxResults, firstResult);
    }

    private List<TipoDeCredito> findTipoDeCreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDeCredito.class));
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

    public TipoDeCredito findTipoDeCredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDeCredito.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDeCreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDeCredito> rt = cq.from(TipoDeCredito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
