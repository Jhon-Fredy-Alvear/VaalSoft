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
import com.coomeva.entities.Observacion;
import com.coomeva.entities.TipoDeObservacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class TipoDeObservacionJpaController implements Serializable {

    public TipoDeObservacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDeObservacion tipoDeObservacion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoDeObservacion.getObservacionList() == null) {
            tipoDeObservacion.setObservacionList(new ArrayList<Observacion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Observacion> attachedObservacionList = new ArrayList<Observacion>();
            for (Observacion observacionListObservacionToAttach : tipoDeObservacion.getObservacionList()) {
                observacionListObservacionToAttach = em.getReference(observacionListObservacionToAttach.getClass(), observacionListObservacionToAttach.getIdObservacion());
                attachedObservacionList.add(observacionListObservacionToAttach);
            }
            tipoDeObservacion.setObservacionList(attachedObservacionList);
            em.persist(tipoDeObservacion);
            for (Observacion observacionListObservacion : tipoDeObservacion.getObservacionList()) {
                TipoDeObservacion oldIdTipodeObservacionOfObservacionListObservacion = observacionListObservacion.getIdTipodeObservacion();
                observacionListObservacion.setIdTipodeObservacion(tipoDeObservacion);
                observacionListObservacion = em.merge(observacionListObservacion);
                if (oldIdTipodeObservacionOfObservacionListObservacion != null) {
                    oldIdTipodeObservacionOfObservacionListObservacion.getObservacionList().remove(observacionListObservacion);
                    oldIdTipodeObservacionOfObservacionListObservacion = em.merge(oldIdTipodeObservacionOfObservacionListObservacion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoDeObservacion(tipoDeObservacion.getIdTipodeObservacion()) != null) {
                throw new PreexistingEntityException("TipoDeObservacion " + tipoDeObservacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDeObservacion tipoDeObservacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoDeObservacion persistentTipoDeObservacion = em.find(TipoDeObservacion.class, tipoDeObservacion.getIdTipodeObservacion());
            List<Observacion> observacionListOld = persistentTipoDeObservacion.getObservacionList();
            List<Observacion> observacionListNew = tipoDeObservacion.getObservacionList();
            List<String> illegalOrphanMessages = null;
            for (Observacion observacionListOldObservacion : observacionListOld) {
                if (!observacionListNew.contains(observacionListOldObservacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Observacion " + observacionListOldObservacion + " since its idTipodeObservacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Observacion> attachedObservacionListNew = new ArrayList<Observacion>();
            for (Observacion observacionListNewObservacionToAttach : observacionListNew) {
                observacionListNewObservacionToAttach = em.getReference(observacionListNewObservacionToAttach.getClass(), observacionListNewObservacionToAttach.getIdObservacion());
                attachedObservacionListNew.add(observacionListNewObservacionToAttach);
            }
            observacionListNew = attachedObservacionListNew;
            tipoDeObservacion.setObservacionList(observacionListNew);
            tipoDeObservacion = em.merge(tipoDeObservacion);
            for (Observacion observacionListNewObservacion : observacionListNew) {
                if (!observacionListOld.contains(observacionListNewObservacion)) {
                    TipoDeObservacion oldIdTipodeObservacionOfObservacionListNewObservacion = observacionListNewObservacion.getIdTipodeObservacion();
                    observacionListNewObservacion.setIdTipodeObservacion(tipoDeObservacion);
                    observacionListNewObservacion = em.merge(observacionListNewObservacion);
                    if (oldIdTipodeObservacionOfObservacionListNewObservacion != null && !oldIdTipodeObservacionOfObservacionListNewObservacion.equals(tipoDeObservacion)) {
                        oldIdTipodeObservacionOfObservacionListNewObservacion.getObservacionList().remove(observacionListNewObservacion);
                        oldIdTipodeObservacionOfObservacionListNewObservacion = em.merge(oldIdTipodeObservacionOfObservacionListNewObservacion);
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
                Integer id = tipoDeObservacion.getIdTipodeObservacion();
                if (findTipoDeObservacion(id) == null) {
                    throw new NonexistentEntityException("The tipoDeObservacion with id " + id + " no longer exists.");
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
            TipoDeObservacion tipoDeObservacion;
            try {
                tipoDeObservacion = em.getReference(TipoDeObservacion.class, id);
                tipoDeObservacion.getIdTipodeObservacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDeObservacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Observacion> observacionListOrphanCheck = tipoDeObservacion.getObservacionList();
            for (Observacion observacionListOrphanCheckObservacion : observacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoDeObservacion (" + tipoDeObservacion + ") cannot be destroyed since the Observacion " + observacionListOrphanCheckObservacion + " in its observacionList field has a non-nullable idTipodeObservacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoDeObservacion);
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

    public List<TipoDeObservacion> findTipoDeObservacionEntities() {
        return findTipoDeObservacionEntities(true, -1, -1);
    }

    public List<TipoDeObservacion> findTipoDeObservacionEntities(int maxResults, int firstResult) {
        return findTipoDeObservacionEntities(false, maxResults, firstResult);
    }

    private List<TipoDeObservacion> findTipoDeObservacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDeObservacion.class));
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

    public TipoDeObservacion findTipoDeObservacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDeObservacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDeObservacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDeObservacion> rt = cq.from(TipoDeObservacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
