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
import com.coomeva.entities.RangoCuota;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.RangoMonto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class RangoMontoJpaController implements Serializable {

    public RangoMontoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RangoMonto rangoMonto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (rangoMonto.getClienteList() == null) {
            rangoMonto.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RangoCuota idRangoCuota = rangoMonto.getIdRangoCuota();
            if (idRangoCuota != null) {
                idRangoCuota = em.getReference(idRangoCuota.getClass(), idRangoCuota.getIdRangoCuota());
                rangoMonto.setIdRangoCuota(idRangoCuota);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : rangoMonto.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            rangoMonto.setClienteList(attachedClienteList);
            em.persist(rangoMonto);
            if (idRangoCuota != null) {
                idRangoCuota.getRangoMontoList().add(rangoMonto);
                idRangoCuota = em.merge(idRangoCuota);
            }
            for (Cliente clienteListCliente : rangoMonto.getClienteList()) {
                RangoMonto oldIdRangoMontoOfClienteListCliente = clienteListCliente.getIdRangoMonto();
                clienteListCliente.setIdRangoMonto(rangoMonto);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdRangoMontoOfClienteListCliente != null) {
                    oldIdRangoMontoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdRangoMontoOfClienteListCliente = em.merge(oldIdRangoMontoOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRangoMonto(rangoMonto.getIdRangoMonto()) != null) {
                throw new PreexistingEntityException("RangoMonto " + rangoMonto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RangoMonto rangoMonto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RangoMonto persistentRangoMonto = em.find(RangoMonto.class, rangoMonto.getIdRangoMonto());
            RangoCuota idRangoCuotaOld = persistentRangoMonto.getIdRangoCuota();
            RangoCuota idRangoCuotaNew = rangoMonto.getIdRangoCuota();
            List<Cliente> clienteListOld = persistentRangoMonto.getClienteList();
            List<Cliente> clienteListNew = rangoMonto.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idRangoMonto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idRangoCuotaNew != null) {
                idRangoCuotaNew = em.getReference(idRangoCuotaNew.getClass(), idRangoCuotaNew.getIdRangoCuota());
                rangoMonto.setIdRangoCuota(idRangoCuotaNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            rangoMonto.setClienteList(clienteListNew);
            rangoMonto = em.merge(rangoMonto);
            if (idRangoCuotaOld != null && !idRangoCuotaOld.equals(idRangoCuotaNew)) {
                idRangoCuotaOld.getRangoMontoList().remove(rangoMonto);
                idRangoCuotaOld = em.merge(idRangoCuotaOld);
            }
            if (idRangoCuotaNew != null && !idRangoCuotaNew.equals(idRangoCuotaOld)) {
                idRangoCuotaNew.getRangoMontoList().add(rangoMonto);
                idRangoCuotaNew = em.merge(idRangoCuotaNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    RangoMonto oldIdRangoMontoOfClienteListNewCliente = clienteListNewCliente.getIdRangoMonto();
                    clienteListNewCliente.setIdRangoMonto(rangoMonto);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdRangoMontoOfClienteListNewCliente != null && !oldIdRangoMontoOfClienteListNewCliente.equals(rangoMonto)) {
                        oldIdRangoMontoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdRangoMontoOfClienteListNewCliente = em.merge(oldIdRangoMontoOfClienteListNewCliente);
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
                Long id = rangoMonto.getIdRangoMonto();
                if (findRangoMonto(id) == null) {
                    throw new NonexistentEntityException("The rangoMonto with id " + id + " no longer exists.");
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
            RangoMonto rangoMonto;
            try {
                rangoMonto = em.getReference(RangoMonto.class, id);
                rangoMonto.getIdRangoMonto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rangoMonto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = rangoMonto.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RangoMonto (" + rangoMonto + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idRangoMonto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RangoCuota idRangoCuota = rangoMonto.getIdRangoCuota();
            if (idRangoCuota != null) {
                idRangoCuota.getRangoMontoList().remove(rangoMonto);
                idRangoCuota = em.merge(idRangoCuota);
            }
            em.remove(rangoMonto);
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

    public List<RangoMonto> findRangoMontoEntities() {
        return findRangoMontoEntities(true, -1, -1);
    }

    public List<RangoMonto> findRangoMontoEntities(int maxResults, int firstResult) {
        return findRangoMontoEntities(false, maxResults, firstResult);
    }

    private List<RangoMonto> findRangoMontoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RangoMonto.class));
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

    public RangoMonto findRangoMonto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RangoMonto.class, id);
        } finally {
            em.close();
        }
    }

    public int getRangoMontoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RangoMonto> rt = cq.from(RangoMonto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
