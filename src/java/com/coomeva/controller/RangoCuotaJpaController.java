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
import com.coomeva.entities.RangoCuota;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class RangoCuotaJpaController implements Serializable {

    public RangoCuotaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RangoCuota rangoCuota) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (rangoCuota.getRangoMontoList() == null) {
            rangoCuota.setRangoMontoList(new ArrayList<RangoMonto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RangoMonto> attachedRangoMontoList = new ArrayList<RangoMonto>();
            for (RangoMonto rangoMontoListRangoMontoToAttach : rangoCuota.getRangoMontoList()) {
                rangoMontoListRangoMontoToAttach = em.getReference(rangoMontoListRangoMontoToAttach.getClass(), rangoMontoListRangoMontoToAttach.getIdRangoMonto());
                attachedRangoMontoList.add(rangoMontoListRangoMontoToAttach);
            }
            rangoCuota.setRangoMontoList(attachedRangoMontoList);
            em.persist(rangoCuota);
            for (RangoMonto rangoMontoListRangoMonto : rangoCuota.getRangoMontoList()) {
                RangoCuota oldIdRangoCuotaOfRangoMontoListRangoMonto = rangoMontoListRangoMonto.getIdRangoCuota();
                rangoMontoListRangoMonto.setIdRangoCuota(rangoCuota);
                rangoMontoListRangoMonto = em.merge(rangoMontoListRangoMonto);
                if (oldIdRangoCuotaOfRangoMontoListRangoMonto != null) {
                    oldIdRangoCuotaOfRangoMontoListRangoMonto.getRangoMontoList().remove(rangoMontoListRangoMonto);
                    oldIdRangoCuotaOfRangoMontoListRangoMonto = em.merge(oldIdRangoCuotaOfRangoMontoListRangoMonto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRangoCuota(rangoCuota.getIdRangoCuota()) != null) {
                throw new PreexistingEntityException("RangoCuota " + rangoCuota + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RangoCuota rangoCuota) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RangoCuota persistentRangoCuota = em.find(RangoCuota.class, rangoCuota.getIdRangoCuota());
            List<RangoMonto> rangoMontoListOld = persistentRangoCuota.getRangoMontoList();
            List<RangoMonto> rangoMontoListNew = rangoCuota.getRangoMontoList();
            List<String> illegalOrphanMessages = null;
            for (RangoMonto rangoMontoListOldRangoMonto : rangoMontoListOld) {
                if (!rangoMontoListNew.contains(rangoMontoListOldRangoMonto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RangoMonto " + rangoMontoListOldRangoMonto + " since its idRangoCuota field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RangoMonto> attachedRangoMontoListNew = new ArrayList<RangoMonto>();
            for (RangoMonto rangoMontoListNewRangoMontoToAttach : rangoMontoListNew) {
                rangoMontoListNewRangoMontoToAttach = em.getReference(rangoMontoListNewRangoMontoToAttach.getClass(), rangoMontoListNewRangoMontoToAttach.getIdRangoMonto());
                attachedRangoMontoListNew.add(rangoMontoListNewRangoMontoToAttach);
            }
            rangoMontoListNew = attachedRangoMontoListNew;
            rangoCuota.setRangoMontoList(rangoMontoListNew);
            rangoCuota = em.merge(rangoCuota);
            for (RangoMonto rangoMontoListNewRangoMonto : rangoMontoListNew) {
                if (!rangoMontoListOld.contains(rangoMontoListNewRangoMonto)) {
                    RangoCuota oldIdRangoCuotaOfRangoMontoListNewRangoMonto = rangoMontoListNewRangoMonto.getIdRangoCuota();
                    rangoMontoListNewRangoMonto.setIdRangoCuota(rangoCuota);
                    rangoMontoListNewRangoMonto = em.merge(rangoMontoListNewRangoMonto);
                    if (oldIdRangoCuotaOfRangoMontoListNewRangoMonto != null && !oldIdRangoCuotaOfRangoMontoListNewRangoMonto.equals(rangoCuota)) {
                        oldIdRangoCuotaOfRangoMontoListNewRangoMonto.getRangoMontoList().remove(rangoMontoListNewRangoMonto);
                        oldIdRangoCuotaOfRangoMontoListNewRangoMonto = em.merge(oldIdRangoCuotaOfRangoMontoListNewRangoMonto);
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
                Integer id = rangoCuota.getIdRangoCuota();
                if (findRangoCuota(id) == null) {
                    throw new NonexistentEntityException("The rangoCuota with id " + id + " no longer exists.");
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
            RangoCuota rangoCuota;
            try {
                rangoCuota = em.getReference(RangoCuota.class, id);
                rangoCuota.getIdRangoCuota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rangoCuota with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RangoMonto> rangoMontoListOrphanCheck = rangoCuota.getRangoMontoList();
            for (RangoMonto rangoMontoListOrphanCheckRangoMonto : rangoMontoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RangoCuota (" + rangoCuota + ") cannot be destroyed since the RangoMonto " + rangoMontoListOrphanCheckRangoMonto + " in its rangoMontoList field has a non-nullable idRangoCuota field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rangoCuota);
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

    public List<RangoCuota> findRangoCuotaEntities() {
        return findRangoCuotaEntities(true, -1, -1);
    }

    public List<RangoCuota> findRangoCuotaEntities(int maxResults, int firstResult) {
        return findRangoCuotaEntities(false, maxResults, firstResult);
    }

    private List<RangoCuota> findRangoCuotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RangoCuota.class));
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

    public RangoCuota findRangoCuota(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RangoCuota.class, id);
        } finally {
            em.close();
        }
    }

    public int getRangoCuotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RangoCuota> rt = cq.from(RangoCuota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
