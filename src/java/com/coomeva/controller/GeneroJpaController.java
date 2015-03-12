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
import com.coomeva.entities.Fiador;
import java.util.ArrayList;
import java.util.List;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Empleado;
import com.coomeva.entities.Genero;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (genero.getFiadorList() == null) {
            genero.setFiadorList(new ArrayList<Fiador>());
        }
        if (genero.getClienteList() == null) {
            genero.setClienteList(new ArrayList<Cliente>());
        }
        if (genero.getEmpleadoList() == null) {
            genero.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Fiador> attachedFiadorList = new ArrayList<Fiador>();
            for (Fiador fiadorListFiadorToAttach : genero.getFiadorList()) {
                fiadorListFiadorToAttach = em.getReference(fiadorListFiadorToAttach.getClass(), fiadorListFiadorToAttach.getIdFiador());
                attachedFiadorList.add(fiadorListFiadorToAttach);
            }
            genero.setFiadorList(attachedFiadorList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : genero.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            genero.setClienteList(attachedClienteList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : genero.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            genero.setEmpleadoList(attachedEmpleadoList);
            em.persist(genero);
            for (Fiador fiadorListFiador : genero.getFiadorList()) {
                Genero oldIdGeneroOfFiadorListFiador = fiadorListFiador.getIdGenero();
                fiadorListFiador.setIdGenero(genero);
                fiadorListFiador = em.merge(fiadorListFiador);
                if (oldIdGeneroOfFiadorListFiador != null) {
                    oldIdGeneroOfFiadorListFiador.getFiadorList().remove(fiadorListFiador);
                    oldIdGeneroOfFiadorListFiador = em.merge(oldIdGeneroOfFiadorListFiador);
                }
            }
            for (Cliente clienteListCliente : genero.getClienteList()) {
                Genero oldIdGeneroOfClienteListCliente = clienteListCliente.getIdGenero();
                clienteListCliente.setIdGenero(genero);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdGeneroOfClienteListCliente != null) {
                    oldIdGeneroOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdGeneroOfClienteListCliente = em.merge(oldIdGeneroOfClienteListCliente);
                }
            }
            for (Empleado empleadoListEmpleado : genero.getEmpleadoList()) {
                Genero oldIdGeneroOfEmpleadoListEmpleado = empleadoListEmpleado.getIdGenero();
                empleadoListEmpleado.setIdGenero(genero);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdGeneroOfEmpleadoListEmpleado != null) {
                    oldIdGeneroOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdGeneroOfEmpleadoListEmpleado = em.merge(oldIdGeneroOfEmpleadoListEmpleado);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findGenero(genero.getIdGenero()) != null) {
                throw new PreexistingEntityException("Genero " + genero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Genero persistentGenero = em.find(Genero.class, genero.getIdGenero());
            List<Fiador> fiadorListOld = persistentGenero.getFiadorList();
            List<Fiador> fiadorListNew = genero.getFiadorList();
            List<Cliente> clienteListOld = persistentGenero.getClienteList();
            List<Cliente> clienteListNew = genero.getClienteList();
            List<Empleado> empleadoListOld = persistentGenero.getEmpleadoList();
            List<Empleado> empleadoListNew = genero.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Fiador fiadorListOldFiador : fiadorListOld) {
                if (!fiadorListNew.contains(fiadorListOldFiador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fiador " + fiadorListOldFiador + " since its idGenero field is not nullable.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idGenero field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idGenero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Fiador> attachedFiadorListNew = new ArrayList<Fiador>();
            for (Fiador fiadorListNewFiadorToAttach : fiadorListNew) {
                fiadorListNewFiadorToAttach = em.getReference(fiadorListNewFiadorToAttach.getClass(), fiadorListNewFiadorToAttach.getIdFiador());
                attachedFiadorListNew.add(fiadorListNewFiadorToAttach);
            }
            fiadorListNew = attachedFiadorListNew;
            genero.setFiadorList(fiadorListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            genero.setClienteList(clienteListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            genero.setEmpleadoList(empleadoListNew);
            genero = em.merge(genero);
            for (Fiador fiadorListNewFiador : fiadorListNew) {
                if (!fiadorListOld.contains(fiadorListNewFiador)) {
                    Genero oldIdGeneroOfFiadorListNewFiador = fiadorListNewFiador.getIdGenero();
                    fiadorListNewFiador.setIdGenero(genero);
                    fiadorListNewFiador = em.merge(fiadorListNewFiador);
                    if (oldIdGeneroOfFiadorListNewFiador != null && !oldIdGeneroOfFiadorListNewFiador.equals(genero)) {
                        oldIdGeneroOfFiadorListNewFiador.getFiadorList().remove(fiadorListNewFiador);
                        oldIdGeneroOfFiadorListNewFiador = em.merge(oldIdGeneroOfFiadorListNewFiador);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Genero oldIdGeneroOfClienteListNewCliente = clienteListNewCliente.getIdGenero();
                    clienteListNewCliente.setIdGenero(genero);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdGeneroOfClienteListNewCliente != null && !oldIdGeneroOfClienteListNewCliente.equals(genero)) {
                        oldIdGeneroOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdGeneroOfClienteListNewCliente = em.merge(oldIdGeneroOfClienteListNewCliente);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Genero oldIdGeneroOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdGenero();
                    empleadoListNewEmpleado.setIdGenero(genero);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdGeneroOfEmpleadoListNewEmpleado != null && !oldIdGeneroOfEmpleadoListNewEmpleado.equals(genero)) {
                        oldIdGeneroOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdGeneroOfEmpleadoListNewEmpleado = em.merge(oldIdGeneroOfEmpleadoListNewEmpleado);
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
                Integer id = genero.getIdGenero();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getIdGenero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Fiador> fiadorListOrphanCheck = genero.getFiadorList();
            for (Fiador fiadorListOrphanCheckFiador : fiadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Fiador " + fiadorListOrphanCheckFiador + " in its fiadorList field has a non-nullable idGenero field.");
            }
            List<Cliente> clienteListOrphanCheck = genero.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idGenero field.");
            }
            List<Empleado> empleadoListOrphanCheck = genero.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idGenero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genero);
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

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
