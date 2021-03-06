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
import com.coomeva.entities.Empleado;
import com.coomeva.entities.Genero;
import com.coomeva.entities.Regional;
import com.coomeva.entities.Rol;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empleado.getFlujoVisitaList() == null) {
            empleado.setFlujoVisitaList(new ArrayList<FlujoVisita>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barrio idBarrio = empleado.getIdBarrio();
            if (idBarrio != null) {
                idBarrio = em.getReference(idBarrio.getClass(), idBarrio.getIdBarrio());
                empleado.setIdBarrio(idBarrio);
            }
            Ciudad idCiudad = empleado.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                empleado.setIdCiudad(idCiudad);
            }
            Departamento idDepartamento = empleado.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                empleado.setIdDepartamento(idDepartamento);
            }
            Genero idGenero = empleado.getIdGenero();
            if (idGenero != null) {
                idGenero = em.getReference(idGenero.getClass(), idGenero.getIdGenero());
                empleado.setIdGenero(idGenero);
            }
            Regional idRegional = empleado.getIdRegional();
            if (idRegional != null) {
                idRegional = em.getReference(idRegional.getClass(), idRegional.getIdRegional());
                empleado.setIdRegional(idRegional);
            }
            Rol idRol = empleado.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                empleado.setIdRol(idRol);
            }
            List<FlujoVisita> attachedFlujoVisitaList = new ArrayList<FlujoVisita>();
            for (FlujoVisita flujoVisitaListFlujoVisitaToAttach : empleado.getFlujoVisitaList()) {
                flujoVisitaListFlujoVisitaToAttach = em.getReference(flujoVisitaListFlujoVisitaToAttach.getClass(), flujoVisitaListFlujoVisitaToAttach.getIdFlujoVisita());
                attachedFlujoVisitaList.add(flujoVisitaListFlujoVisitaToAttach);
            }
            empleado.setFlujoVisitaList(attachedFlujoVisitaList);
            em.persist(empleado);
            if (idBarrio != null) {
                idBarrio.getEmpleadoList().add(empleado);
                idBarrio = em.merge(idBarrio);
            }
            if (idCiudad != null) {
                idCiudad.getEmpleadoList().add(empleado);
                idCiudad = em.merge(idCiudad);
            }
            if (idDepartamento != null) {
                idDepartamento.getEmpleadoList().add(empleado);
                idDepartamento = em.merge(idDepartamento);
            }
            if (idGenero != null) {
                idGenero.getEmpleadoList().add(empleado);
                idGenero = em.merge(idGenero);
            }
            if (idRegional != null) {
                idRegional.getEmpleadoList().add(empleado);
                idRegional = em.merge(idRegional);
            }
            if (idRol != null) {
                idRol.getEmpleadoList().add(empleado);
                idRol = em.merge(idRol);
            }
            for (FlujoVisita flujoVisitaListFlujoVisita : empleado.getFlujoVisitaList()) {
                flujoVisitaListFlujoVisita.getEmpleadoList().add(empleado);
                flujoVisitaListFlujoVisita = em.merge(flujoVisitaListFlujoVisita);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpleado(empleado.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            Barrio idBarrioOld = persistentEmpleado.getIdBarrio();
            Barrio idBarrioNew = empleado.getIdBarrio();
            Ciudad idCiudadOld = persistentEmpleado.getIdCiudad();
            Ciudad idCiudadNew = empleado.getIdCiudad();
            Departamento idDepartamentoOld = persistentEmpleado.getIdDepartamento();
            Departamento idDepartamentoNew = empleado.getIdDepartamento();
            Genero idGeneroOld = persistentEmpleado.getIdGenero();
            Genero idGeneroNew = empleado.getIdGenero();
            Regional idRegionalOld = persistentEmpleado.getIdRegional();
            Regional idRegionalNew = empleado.getIdRegional();
            Rol idRolOld = persistentEmpleado.getIdRol();
            Rol idRolNew = empleado.getIdRol();
            List<FlujoVisita> flujoVisitaListOld = persistentEmpleado.getFlujoVisitaList();
            List<FlujoVisita> flujoVisitaListNew = empleado.getFlujoVisitaList();
            if (idBarrioNew != null) {
                idBarrioNew = em.getReference(idBarrioNew.getClass(), idBarrioNew.getIdBarrio());
                empleado.setIdBarrio(idBarrioNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                empleado.setIdCiudad(idCiudadNew);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                empleado.setIdDepartamento(idDepartamentoNew);
            }
            if (idGeneroNew != null) {
                idGeneroNew = em.getReference(idGeneroNew.getClass(), idGeneroNew.getIdGenero());
                empleado.setIdGenero(idGeneroNew);
            }
            if (idRegionalNew != null) {
                idRegionalNew = em.getReference(idRegionalNew.getClass(), idRegionalNew.getIdRegional());
                empleado.setIdRegional(idRegionalNew);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                empleado.setIdRol(idRolNew);
            }
            List<FlujoVisita> attachedFlujoVisitaListNew = new ArrayList<FlujoVisita>();
            for (FlujoVisita flujoVisitaListNewFlujoVisitaToAttach : flujoVisitaListNew) {
                flujoVisitaListNewFlujoVisitaToAttach = em.getReference(flujoVisitaListNewFlujoVisitaToAttach.getClass(), flujoVisitaListNewFlujoVisitaToAttach.getIdFlujoVisita());
                attachedFlujoVisitaListNew.add(flujoVisitaListNewFlujoVisitaToAttach);
            }
            flujoVisitaListNew = attachedFlujoVisitaListNew;
            empleado.setFlujoVisitaList(flujoVisitaListNew);
            empleado = em.merge(empleado);
            if (idBarrioOld != null && !idBarrioOld.equals(idBarrioNew)) {
                idBarrioOld.getEmpleadoList().remove(empleado);
                idBarrioOld = em.merge(idBarrioOld);
            }
            if (idBarrioNew != null && !idBarrioNew.equals(idBarrioOld)) {
                idBarrioNew.getEmpleadoList().add(empleado);
                idBarrioNew = em.merge(idBarrioNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getEmpleadoList().remove(empleado);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getEmpleadoList().add(empleado);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getEmpleadoList().remove(empleado);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getEmpleadoList().add(empleado);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            if (idGeneroOld != null && !idGeneroOld.equals(idGeneroNew)) {
                idGeneroOld.getEmpleadoList().remove(empleado);
                idGeneroOld = em.merge(idGeneroOld);
            }
            if (idGeneroNew != null && !idGeneroNew.equals(idGeneroOld)) {
                idGeneroNew.getEmpleadoList().add(empleado);
                idGeneroNew = em.merge(idGeneroNew);
            }
            if (idRegionalOld != null && !idRegionalOld.equals(idRegionalNew)) {
                idRegionalOld.getEmpleadoList().remove(empleado);
                idRegionalOld = em.merge(idRegionalOld);
            }
            if (idRegionalNew != null && !idRegionalNew.equals(idRegionalOld)) {
                idRegionalNew.getEmpleadoList().add(empleado);
                idRegionalNew = em.merge(idRegionalNew);
            }
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getEmpleadoList().remove(empleado);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getEmpleadoList().add(empleado);
                idRolNew = em.merge(idRolNew);
            }
            for (FlujoVisita flujoVisitaListOldFlujoVisita : flujoVisitaListOld) {
                if (!flujoVisitaListNew.contains(flujoVisitaListOldFlujoVisita)) {
                    flujoVisitaListOldFlujoVisita.getEmpleadoList().remove(empleado);
                    flujoVisitaListOldFlujoVisita = em.merge(flujoVisitaListOldFlujoVisita);
                }
            }
            for (FlujoVisita flujoVisitaListNewFlujoVisita : flujoVisitaListNew) {
                if (!flujoVisitaListOld.contains(flujoVisitaListNewFlujoVisita)) {
                    flujoVisitaListNewFlujoVisita.getEmpleadoList().add(empleado);
                    flujoVisitaListNewFlujoVisita = em.merge(flujoVisitaListNewFlujoVisita);
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
                Long id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Barrio idBarrio = empleado.getIdBarrio();
            if (idBarrio != null) {
                idBarrio.getEmpleadoList().remove(empleado);
                idBarrio = em.merge(idBarrio);
            }
            Ciudad idCiudad = empleado.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getEmpleadoList().remove(empleado);
                idCiudad = em.merge(idCiudad);
            }
            Departamento idDepartamento = empleado.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getEmpleadoList().remove(empleado);
                idDepartamento = em.merge(idDepartamento);
            }
            Genero idGenero = empleado.getIdGenero();
            if (idGenero != null) {
                idGenero.getEmpleadoList().remove(empleado);
                idGenero = em.merge(idGenero);
            }
            Regional idRegional = empleado.getIdRegional();
            if (idRegional != null) {
                idRegional.getEmpleadoList().remove(empleado);
                idRegional = em.merge(idRegional);
            }
            Rol idRol = empleado.getIdRol();
            if (idRol != null) {
                idRol.getEmpleadoList().remove(empleado);
                idRol = em.merge(idRol);
            }
            List<FlujoVisita> flujoVisitaList = empleado.getFlujoVisitaList();
            for (FlujoVisita flujoVisitaListFlujoVisita : flujoVisitaList) {
                flujoVisitaListFlujoVisita.getEmpleadoList().remove(empleado);
                flujoVisitaListFlujoVisita = em.merge(flujoVisitaListFlujoVisita);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
