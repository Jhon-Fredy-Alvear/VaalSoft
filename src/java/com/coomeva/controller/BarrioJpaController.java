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
import com.coomeva.entities.Barrio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coomeva.entities.Regional;
import java.util.ArrayList;
import java.util.List;
import com.coomeva.entities.Fiador;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Empleado;
import com.coomeva.entities.Empresa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class BarrioJpaController implements Serializable {

    public BarrioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barrio barrio) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (barrio.getRegionalList() == null) {
            barrio.setRegionalList(new ArrayList<Regional>());
        }
        if (barrio.getFiadorList() == null) {
            barrio.setFiadorList(new ArrayList<Fiador>());
        }
        if (barrio.getClienteList() == null) {
            barrio.setClienteList(new ArrayList<Cliente>());
        }
        if (barrio.getEmpleadoList() == null) {
            barrio.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (barrio.getEmpresaList() == null) {
            barrio.setEmpresaList(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Regional> attachedRegionalList = new ArrayList<Regional>();
            for (Regional regionalListRegionalToAttach : barrio.getRegionalList()) {
                regionalListRegionalToAttach = em.getReference(regionalListRegionalToAttach.getClass(), regionalListRegionalToAttach.getIdRegional());
                attachedRegionalList.add(regionalListRegionalToAttach);
            }
            barrio.setRegionalList(attachedRegionalList);
            List<Fiador> attachedFiadorList = new ArrayList<Fiador>();
            for (Fiador fiadorListFiadorToAttach : barrio.getFiadorList()) {
                fiadorListFiadorToAttach = em.getReference(fiadorListFiadorToAttach.getClass(), fiadorListFiadorToAttach.getIdFiador());
                attachedFiadorList.add(fiadorListFiadorToAttach);
            }
            barrio.setFiadorList(attachedFiadorList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : barrio.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            barrio.setClienteList(attachedClienteList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : barrio.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            barrio.setEmpleadoList(attachedEmpleadoList);
            List<Empresa> attachedEmpresaList = new ArrayList<Empresa>();
            for (Empresa empresaListEmpresaToAttach : barrio.getEmpresaList()) {
                empresaListEmpresaToAttach = em.getReference(empresaListEmpresaToAttach.getClass(), empresaListEmpresaToAttach.getIdEmpresa());
                attachedEmpresaList.add(empresaListEmpresaToAttach);
            }
            barrio.setEmpresaList(attachedEmpresaList);
            em.persist(barrio);
            for (Regional regionalListRegional : barrio.getRegionalList()) {
                Barrio oldIdBarrioOfRegionalListRegional = regionalListRegional.getIdBarrio();
                regionalListRegional.setIdBarrio(barrio);
                regionalListRegional = em.merge(regionalListRegional);
                if (oldIdBarrioOfRegionalListRegional != null) {
                    oldIdBarrioOfRegionalListRegional.getRegionalList().remove(regionalListRegional);
                    oldIdBarrioOfRegionalListRegional = em.merge(oldIdBarrioOfRegionalListRegional);
                }
            }
            for (Fiador fiadorListFiador : barrio.getFiadorList()) {
                Barrio oldIdBarrioOfFiadorListFiador = fiadorListFiador.getIdBarrio();
                fiadorListFiador.setIdBarrio(barrio);
                fiadorListFiador = em.merge(fiadorListFiador);
                if (oldIdBarrioOfFiadorListFiador != null) {
                    oldIdBarrioOfFiadorListFiador.getFiadorList().remove(fiadorListFiador);
                    oldIdBarrioOfFiadorListFiador = em.merge(oldIdBarrioOfFiadorListFiador);
                }
            }
            for (Cliente clienteListCliente : barrio.getClienteList()) {
                Barrio oldBarrioidBarrioOfClienteListCliente = clienteListCliente.getBarrioidBarrio();
                clienteListCliente.setBarrioidBarrio(barrio);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldBarrioidBarrioOfClienteListCliente != null) {
                    oldBarrioidBarrioOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldBarrioidBarrioOfClienteListCliente = em.merge(oldBarrioidBarrioOfClienteListCliente);
                }
            }
            for (Empleado empleadoListEmpleado : barrio.getEmpleadoList()) {
                Barrio oldIdBarrioOfEmpleadoListEmpleado = empleadoListEmpleado.getIdBarrio();
                empleadoListEmpleado.setIdBarrio(barrio);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdBarrioOfEmpleadoListEmpleado != null) {
                    oldIdBarrioOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdBarrioOfEmpleadoListEmpleado = em.merge(oldIdBarrioOfEmpleadoListEmpleado);
                }
            }
            for (Empresa empresaListEmpresa : barrio.getEmpresaList()) {
                Barrio oldIdBarrioOfEmpresaListEmpresa = empresaListEmpresa.getIdBarrio();
                empresaListEmpresa.setIdBarrio(barrio);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldIdBarrioOfEmpresaListEmpresa != null) {
                    oldIdBarrioOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldIdBarrioOfEmpresaListEmpresa = em.merge(oldIdBarrioOfEmpresaListEmpresa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBarrio(barrio.getIdBarrio()) != null) {
                throw new PreexistingEntityException("Barrio " + barrio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barrio barrio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barrio persistentBarrio = em.find(Barrio.class, barrio.getIdBarrio());
            List<Regional> regionalListOld = persistentBarrio.getRegionalList();
            List<Regional> regionalListNew = barrio.getRegionalList();
            List<Fiador> fiadorListOld = persistentBarrio.getFiadorList();
            List<Fiador> fiadorListNew = barrio.getFiadorList();
            List<Cliente> clienteListOld = persistentBarrio.getClienteList();
            List<Cliente> clienteListNew = barrio.getClienteList();
            List<Empleado> empleadoListOld = persistentBarrio.getEmpleadoList();
            List<Empleado> empleadoListNew = barrio.getEmpleadoList();
            List<Empresa> empresaListOld = persistentBarrio.getEmpresaList();
            List<Empresa> empresaListNew = barrio.getEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Regional regionalListOldRegional : regionalListOld) {
                if (!regionalListNew.contains(regionalListOldRegional)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Regional " + regionalListOldRegional + " since its idBarrio field is not nullable.");
                }
            }
            for (Fiador fiadorListOldFiador : fiadorListOld) {
                if (!fiadorListNew.contains(fiadorListOldFiador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fiador " + fiadorListOldFiador + " since its idBarrio field is not nullable.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its barrioidBarrio field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idBarrio field is not nullable.");
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its idBarrio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Regional> attachedRegionalListNew = new ArrayList<Regional>();
            for (Regional regionalListNewRegionalToAttach : regionalListNew) {
                regionalListNewRegionalToAttach = em.getReference(regionalListNewRegionalToAttach.getClass(), regionalListNewRegionalToAttach.getIdRegional());
                attachedRegionalListNew.add(regionalListNewRegionalToAttach);
            }
            regionalListNew = attachedRegionalListNew;
            barrio.setRegionalList(regionalListNew);
            List<Fiador> attachedFiadorListNew = new ArrayList<Fiador>();
            for (Fiador fiadorListNewFiadorToAttach : fiadorListNew) {
                fiadorListNewFiadorToAttach = em.getReference(fiadorListNewFiadorToAttach.getClass(), fiadorListNewFiadorToAttach.getIdFiador());
                attachedFiadorListNew.add(fiadorListNewFiadorToAttach);
            }
            fiadorListNew = attachedFiadorListNew;
            barrio.setFiadorList(fiadorListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            barrio.setClienteList(clienteListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            barrio.setEmpleadoList(empleadoListNew);
            List<Empresa> attachedEmpresaListNew = new ArrayList<Empresa>();
            for (Empresa empresaListNewEmpresaToAttach : empresaListNew) {
                empresaListNewEmpresaToAttach = em.getReference(empresaListNewEmpresaToAttach.getClass(), empresaListNewEmpresaToAttach.getIdEmpresa());
                attachedEmpresaListNew.add(empresaListNewEmpresaToAttach);
            }
            empresaListNew = attachedEmpresaListNew;
            barrio.setEmpresaList(empresaListNew);
            barrio = em.merge(barrio);
            for (Regional regionalListNewRegional : regionalListNew) {
                if (!regionalListOld.contains(regionalListNewRegional)) {
                    Barrio oldIdBarrioOfRegionalListNewRegional = regionalListNewRegional.getIdBarrio();
                    regionalListNewRegional.setIdBarrio(barrio);
                    regionalListNewRegional = em.merge(regionalListNewRegional);
                    if (oldIdBarrioOfRegionalListNewRegional != null && !oldIdBarrioOfRegionalListNewRegional.equals(barrio)) {
                        oldIdBarrioOfRegionalListNewRegional.getRegionalList().remove(regionalListNewRegional);
                        oldIdBarrioOfRegionalListNewRegional = em.merge(oldIdBarrioOfRegionalListNewRegional);
                    }
                }
            }
            for (Fiador fiadorListNewFiador : fiadorListNew) {
                if (!fiadorListOld.contains(fiadorListNewFiador)) {
                    Barrio oldIdBarrioOfFiadorListNewFiador = fiadorListNewFiador.getIdBarrio();
                    fiadorListNewFiador.setIdBarrio(barrio);
                    fiadorListNewFiador = em.merge(fiadorListNewFiador);
                    if (oldIdBarrioOfFiadorListNewFiador != null && !oldIdBarrioOfFiadorListNewFiador.equals(barrio)) {
                        oldIdBarrioOfFiadorListNewFiador.getFiadorList().remove(fiadorListNewFiador);
                        oldIdBarrioOfFiadorListNewFiador = em.merge(oldIdBarrioOfFiadorListNewFiador);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Barrio oldBarrioidBarrioOfClienteListNewCliente = clienteListNewCliente.getBarrioidBarrio();
                    clienteListNewCliente.setBarrioidBarrio(barrio);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldBarrioidBarrioOfClienteListNewCliente != null && !oldBarrioidBarrioOfClienteListNewCliente.equals(barrio)) {
                        oldBarrioidBarrioOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldBarrioidBarrioOfClienteListNewCliente = em.merge(oldBarrioidBarrioOfClienteListNewCliente);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Barrio oldIdBarrioOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdBarrio();
                    empleadoListNewEmpleado.setIdBarrio(barrio);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdBarrioOfEmpleadoListNewEmpleado != null && !oldIdBarrioOfEmpleadoListNewEmpleado.equals(barrio)) {
                        oldIdBarrioOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdBarrioOfEmpleadoListNewEmpleado = em.merge(oldIdBarrioOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Barrio oldIdBarrioOfEmpresaListNewEmpresa = empresaListNewEmpresa.getIdBarrio();
                    empresaListNewEmpresa.setIdBarrio(barrio);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldIdBarrioOfEmpresaListNewEmpresa != null && !oldIdBarrioOfEmpresaListNewEmpresa.equals(barrio)) {
                        oldIdBarrioOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldIdBarrioOfEmpresaListNewEmpresa = em.merge(oldIdBarrioOfEmpresaListNewEmpresa);
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
                Long id = barrio.getIdBarrio();
                if (findBarrio(id) == null) {
                    throw new NonexistentEntityException("The barrio with id " + id + " no longer exists.");
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
            Barrio barrio;
            try {
                barrio = em.getReference(Barrio.class, id);
                barrio.getIdBarrio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barrio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Regional> regionalListOrphanCheck = barrio.getRegionalList();
            for (Regional regionalListOrphanCheckRegional : regionalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrio (" + barrio + ") cannot be destroyed since the Regional " + regionalListOrphanCheckRegional + " in its regionalList field has a non-nullable idBarrio field.");
            }
            List<Fiador> fiadorListOrphanCheck = barrio.getFiadorList();
            for (Fiador fiadorListOrphanCheckFiador : fiadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrio (" + barrio + ") cannot be destroyed since the Fiador " + fiadorListOrphanCheckFiador + " in its fiadorList field has a non-nullable idBarrio field.");
            }
            List<Cliente> clienteListOrphanCheck = barrio.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrio (" + barrio + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable barrioidBarrio field.");
            }
            List<Empleado> empleadoListOrphanCheck = barrio.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrio (" + barrio + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idBarrio field.");
            }
            List<Empresa> empresaListOrphanCheck = barrio.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrio (" + barrio + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable idBarrio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(barrio);
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

    public List<Barrio> findBarrioEntities() {
        return findBarrioEntities(true, -1, -1);
    }

    public List<Barrio> findBarrioEntities(int maxResults, int firstResult) {
        return findBarrioEntities(false, maxResults, firstResult);
    }

    private List<Barrio> findBarrioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barrio.class));
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

    public Barrio findBarrio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barrio.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarrioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barrio> rt = cq.from(Barrio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
