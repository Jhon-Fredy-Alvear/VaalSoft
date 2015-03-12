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
import com.coomeva.entities.Empleado;
import com.coomeva.entities.FlujoVisita;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class FlujoVisitaJpaController implements Serializable {

    public FlujoVisitaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FlujoVisita flujoVisita) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (flujoVisita.getEmpleadoList() == null) {
            flujoVisita.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : flujoVisita.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            flujoVisita.setEmpleadoList(attachedEmpleadoList);
            em.persist(flujoVisita);
            for (Empleado empleadoListEmpleado : flujoVisita.getEmpleadoList()) {
                empleadoListEmpleado.getFlujoVisitaList().add(flujoVisita);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFlujoVisita(flujoVisita.getIdFlujoVisita()) != null) {
                throw new PreexistingEntityException("FlujoVisita " + flujoVisita + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FlujoVisita flujoVisita) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FlujoVisita persistentFlujoVisita = em.find(FlujoVisita.class, flujoVisita.getIdFlujoVisita());
            List<Empleado> empleadoListOld = persistentFlujoVisita.getEmpleadoList();
            List<Empleado> empleadoListNew = flujoVisita.getEmpleadoList();
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            flujoVisita.setEmpleadoList(empleadoListNew);
            flujoVisita = em.merge(flujoVisita);
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.getFlujoVisitaList().remove(flujoVisita);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    empleadoListNewEmpleado.getFlujoVisitaList().add(flujoVisita);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
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
                Long id = flujoVisita.getIdFlujoVisita();
                if (findFlujoVisita(id) == null) {
                    throw new NonexistentEntityException("The flujoVisita with id " + id + " no longer exists.");
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
            FlujoVisita flujoVisita;
            try {
                flujoVisita = em.getReference(FlujoVisita.class, id);
                flujoVisita.getIdFlujoVisita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The flujoVisita with id " + id + " no longer exists.", enfe);
            }
            List<Empleado> empleadoList = flujoVisita.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.getFlujoVisitaList().remove(flujoVisita);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(flujoVisita);
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

    public List<FlujoVisita> findFlujoVisitaEntities() {
        return findFlujoVisitaEntities(true, -1, -1);
    }

    public List<FlujoVisita> findFlujoVisitaEntities(int maxResults, int firstResult) {
        return findFlujoVisitaEntities(false, maxResults, firstResult);
    }

    private List<FlujoVisita> findFlujoVisitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FlujoVisita.class));
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

    public FlujoVisita findFlujoVisita(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FlujoVisita.class, id);
        } finally {
            em.close();
        }
    }

    public int getFlujoVisitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FlujoVisita> rt = cq.from(FlujoVisita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
