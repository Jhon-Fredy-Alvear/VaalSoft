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
import com.coomeva.entities.Barrio;
import com.coomeva.entities.Ciudad;
import com.coomeva.entities.Departamento;
import com.coomeva.entities.Cliente;
import java.util.ArrayList;
import java.util.List;
import com.coomeva.entities.Empleado;
import com.coomeva.entities.Regional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class RegionalJpaController implements Serializable {

    public RegionalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regional regional) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (regional.getClienteList() == null) {
            regional.setClienteList(new ArrayList<Cliente>());
        }
        if (regional.getEmpleadoList() == null) {
            regional.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barrio idBarrio = regional.getIdBarrio();
            if (idBarrio != null) {
                idBarrio = em.getReference(idBarrio.getClass(), idBarrio.getIdBarrio());
                regional.setIdBarrio(idBarrio);
            }
            Ciudad idCiudad = regional.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                regional.setIdCiudad(idCiudad);
            }
            Departamento idDepartamento = regional.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                regional.setIdDepartamento(idDepartamento);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : regional.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            regional.setClienteList(attachedClienteList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : regional.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            regional.setEmpleadoList(attachedEmpleadoList);
            em.persist(regional);
            if (idBarrio != null) {
                idBarrio.getRegionalList().add(regional);
                idBarrio = em.merge(idBarrio);
            }
            if (idCiudad != null) {
                idCiudad.getRegionalList().add(regional);
                idCiudad = em.merge(idCiudad);
            }
            if (idDepartamento != null) {
                idDepartamento.getRegionalList().add(regional);
                idDepartamento = em.merge(idDepartamento);
            }
            for (Cliente clienteListCliente : regional.getClienteList()) {
                Regional oldIdRegionalOfClienteListCliente = clienteListCliente.getIdRegional();
                clienteListCliente.setIdRegional(regional);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdRegionalOfClienteListCliente != null) {
                    oldIdRegionalOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdRegionalOfClienteListCliente = em.merge(oldIdRegionalOfClienteListCliente);
                }
            }
            for (Empleado empleadoListEmpleado : regional.getEmpleadoList()) {
                Regional oldIdRegionalOfEmpleadoListEmpleado = empleadoListEmpleado.getIdRegional();
                empleadoListEmpleado.setIdRegional(regional);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdRegionalOfEmpleadoListEmpleado != null) {
                    oldIdRegionalOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdRegionalOfEmpleadoListEmpleado = em.merge(oldIdRegionalOfEmpleadoListEmpleado);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegional(regional.getIdRegional()) != null) {
                throw new PreexistingEntityException("Regional " + regional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regional regional) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Regional persistentRegional = em.find(Regional.class, regional.getIdRegional());
            Barrio idBarrioOld = persistentRegional.getIdBarrio();
            Barrio idBarrioNew = regional.getIdBarrio();
            Ciudad idCiudadOld = persistentRegional.getIdCiudad();
            Ciudad idCiudadNew = regional.getIdCiudad();
            Departamento idDepartamentoOld = persistentRegional.getIdDepartamento();
            Departamento idDepartamentoNew = regional.getIdDepartamento();
            List<Cliente> clienteListOld = persistentRegional.getClienteList();
            List<Cliente> clienteListNew = regional.getClienteList();
            List<Empleado> empleadoListOld = persistentRegional.getEmpleadoList();
            List<Empleado> empleadoListNew = regional.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idRegional field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idRegional field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBarrioNew != null) {
                idBarrioNew = em.getReference(idBarrioNew.getClass(), idBarrioNew.getIdBarrio());
                regional.setIdBarrio(idBarrioNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                regional.setIdCiudad(idCiudadNew);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                regional.setIdDepartamento(idDepartamentoNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            regional.setClienteList(clienteListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            regional.setEmpleadoList(empleadoListNew);
            regional = em.merge(regional);
            if (idBarrioOld != null && !idBarrioOld.equals(idBarrioNew)) {
                idBarrioOld.getRegionalList().remove(regional);
                idBarrioOld = em.merge(idBarrioOld);
            }
            if (idBarrioNew != null && !idBarrioNew.equals(idBarrioOld)) {
                idBarrioNew.getRegionalList().add(regional);
                idBarrioNew = em.merge(idBarrioNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getRegionalList().remove(regional);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getRegionalList().add(regional);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getRegionalList().remove(regional);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getRegionalList().add(regional);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Regional oldIdRegionalOfClienteListNewCliente = clienteListNewCliente.getIdRegional();
                    clienteListNewCliente.setIdRegional(regional);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdRegionalOfClienteListNewCliente != null && !oldIdRegionalOfClienteListNewCliente.equals(regional)) {
                        oldIdRegionalOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdRegionalOfClienteListNewCliente = em.merge(oldIdRegionalOfClienteListNewCliente);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Regional oldIdRegionalOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdRegional();
                    empleadoListNewEmpleado.setIdRegional(regional);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdRegionalOfEmpleadoListNewEmpleado != null && !oldIdRegionalOfEmpleadoListNewEmpleado.equals(regional)) {
                        oldIdRegionalOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdRegionalOfEmpleadoListNewEmpleado = em.merge(oldIdRegionalOfEmpleadoListNewEmpleado);
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
                Long id = regional.getIdRegional();
                if (findRegional(id) == null) {
                    throw new NonexistentEntityException("The regional with id " + id + " no longer exists.");
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
            Regional regional;
            try {
                regional = em.getReference(Regional.class, id);
                regional.getIdRegional();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = regional.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regional (" + regional + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idRegional field.");
            }
            List<Empleado> empleadoListOrphanCheck = regional.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regional (" + regional + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idRegional field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Barrio idBarrio = regional.getIdBarrio();
            if (idBarrio != null) {
                idBarrio.getRegionalList().remove(regional);
                idBarrio = em.merge(idBarrio);
            }
            Ciudad idCiudad = regional.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getRegionalList().remove(regional);
                idCiudad = em.merge(idCiudad);
            }
            Departamento idDepartamento = regional.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getRegionalList().remove(regional);
                idDepartamento = em.merge(idDepartamento);
            }
            em.remove(regional);
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

    public List<Regional> findRegionalEntities() {
        return findRegionalEntities(true, -1, -1);
    }

    public List<Regional> findRegionalEntities(int maxResults, int firstResult) {
        return findRegionalEntities(false, maxResults, firstResult);
    }

    private List<Regional> findRegionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regional.class));
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

    public Regional findRegional(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regional.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regional> rt = cq.from(Regional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
