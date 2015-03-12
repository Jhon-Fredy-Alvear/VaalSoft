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
import com.coomeva.entities.TipoDeObservacion;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Observacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class ObservacionJpaController implements Serializable {

    public ObservacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Observacion observacion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (observacion.getClienteList() == null) {
            observacion.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoDeObservacion idTipodeObservacion = observacion.getIdTipodeObservacion();
            if (idTipodeObservacion != null) {
                idTipodeObservacion = em.getReference(idTipodeObservacion.getClass(), idTipodeObservacion.getIdTipodeObservacion());
                observacion.setIdTipodeObservacion(idTipodeObservacion);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : observacion.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            observacion.setClienteList(attachedClienteList);
            em.persist(observacion);
            if (idTipodeObservacion != null) {
                idTipodeObservacion.getObservacionList().add(observacion);
                idTipodeObservacion = em.merge(idTipodeObservacion);
            }
            for (Cliente clienteListCliente : observacion.getClienteList()) {
                Observacion oldIdObservacionOfClienteListCliente = clienteListCliente.getIdObservacion();
                clienteListCliente.setIdObservacion(observacion);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdObservacionOfClienteListCliente != null) {
                    oldIdObservacionOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdObservacionOfClienteListCliente = em.merge(oldIdObservacionOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findObservacion(observacion.getIdObservacion()) != null) {
                throw new PreexistingEntityException("Observacion " + observacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Observacion observacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Observacion persistentObservacion = em.find(Observacion.class, observacion.getIdObservacion());
            TipoDeObservacion idTipodeObservacionOld = persistentObservacion.getIdTipodeObservacion();
            TipoDeObservacion idTipodeObservacionNew = observacion.getIdTipodeObservacion();
            List<Cliente> clienteListOld = persistentObservacion.getClienteList();
            List<Cliente> clienteListNew = observacion.getClienteList();
            if (idTipodeObservacionNew != null) {
                idTipodeObservacionNew = em.getReference(idTipodeObservacionNew.getClass(), idTipodeObservacionNew.getIdTipodeObservacion());
                observacion.setIdTipodeObservacion(idTipodeObservacionNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            observacion.setClienteList(clienteListNew);
            observacion = em.merge(observacion);
            if (idTipodeObservacionOld != null && !idTipodeObservacionOld.equals(idTipodeObservacionNew)) {
                idTipodeObservacionOld.getObservacionList().remove(observacion);
                idTipodeObservacionOld = em.merge(idTipodeObservacionOld);
            }
            if (idTipodeObservacionNew != null && !idTipodeObservacionNew.equals(idTipodeObservacionOld)) {
                idTipodeObservacionNew.getObservacionList().add(observacion);
                idTipodeObservacionNew = em.merge(idTipodeObservacionNew);
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdObservacion(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Observacion oldIdObservacionOfClienteListNewCliente = clienteListNewCliente.getIdObservacion();
                    clienteListNewCliente.setIdObservacion(observacion);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdObservacionOfClienteListNewCliente != null && !oldIdObservacionOfClienteListNewCliente.equals(observacion)) {
                        oldIdObservacionOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdObservacionOfClienteListNewCliente = em.merge(oldIdObservacionOfClienteListNewCliente);
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
                Long id = observacion.getIdObservacion();
                if (findObservacion(id) == null) {
                    throw new NonexistentEntityException("The observacion with id " + id + " no longer exists.");
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
            Observacion observacion;
            try {
                observacion = em.getReference(Observacion.class, id);
                observacion.getIdObservacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The observacion with id " + id + " no longer exists.", enfe);
            }
            TipoDeObservacion idTipodeObservacion = observacion.getIdTipodeObservacion();
            if (idTipodeObservacion != null) {
                idTipodeObservacion.getObservacionList().remove(observacion);
                idTipodeObservacion = em.merge(idTipodeObservacion);
            }
            List<Cliente> clienteList = observacion.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdObservacion(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(observacion);
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

    public List<Observacion> findObservacionEntities() {
        return findObservacionEntities(true, -1, -1);
    }

    public List<Observacion> findObservacionEntities(int maxResults, int firstResult) {
        return findObservacionEntities(false, maxResults, firstResult);
    }

    private List<Observacion> findObservacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Observacion.class));
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

    public Observacion findObservacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Observacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getObservacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Observacion> rt = cq.from(Observacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
