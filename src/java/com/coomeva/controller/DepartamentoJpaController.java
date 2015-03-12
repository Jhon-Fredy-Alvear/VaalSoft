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
import com.coomeva.entities.Regional;
import java.util.ArrayList;
import java.util.List;
import com.coomeva.entities.Fiador;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Departamento;
import com.coomeva.entities.Empleado;
import com.coomeva.entities.Empresa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (departamento.getRegionalList() == null) {
            departamento.setRegionalList(new ArrayList<Regional>());
        }
        if (departamento.getFiadorList() == null) {
            departamento.setFiadorList(new ArrayList<Fiador>());
        }
        if (departamento.getClienteList() == null) {
            departamento.setClienteList(new ArrayList<Cliente>());
        }
        if (departamento.getEmpleadoList() == null) {
            departamento.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (departamento.getEmpresaList() == null) {
            departamento.setEmpresaList(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Regional> attachedRegionalList = new ArrayList<Regional>();
            for (Regional regionalListRegionalToAttach : departamento.getRegionalList()) {
                regionalListRegionalToAttach = em.getReference(regionalListRegionalToAttach.getClass(), regionalListRegionalToAttach.getIdRegional());
                attachedRegionalList.add(regionalListRegionalToAttach);
            }
            departamento.setRegionalList(attachedRegionalList);
            List<Fiador> attachedFiadorList = new ArrayList<Fiador>();
            for (Fiador fiadorListFiadorToAttach : departamento.getFiadorList()) {
                fiadorListFiadorToAttach = em.getReference(fiadorListFiadorToAttach.getClass(), fiadorListFiadorToAttach.getIdFiador());
                attachedFiadorList.add(fiadorListFiadorToAttach);
            }
            departamento.setFiadorList(attachedFiadorList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : departamento.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            departamento.setClienteList(attachedClienteList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : departamento.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            departamento.setEmpleadoList(attachedEmpleadoList);
            List<Empresa> attachedEmpresaList = new ArrayList<Empresa>();
            for (Empresa empresaListEmpresaToAttach : departamento.getEmpresaList()) {
                empresaListEmpresaToAttach = em.getReference(empresaListEmpresaToAttach.getClass(), empresaListEmpresaToAttach.getIdEmpresa());
                attachedEmpresaList.add(empresaListEmpresaToAttach);
            }
            departamento.setEmpresaList(attachedEmpresaList);
            em.persist(departamento);
            for (Regional regionalListRegional : departamento.getRegionalList()) {
                Departamento oldIdDepartamentoOfRegionalListRegional = regionalListRegional.getIdDepartamento();
                regionalListRegional.setIdDepartamento(departamento);
                regionalListRegional = em.merge(regionalListRegional);
                if (oldIdDepartamentoOfRegionalListRegional != null) {
                    oldIdDepartamentoOfRegionalListRegional.getRegionalList().remove(regionalListRegional);
                    oldIdDepartamentoOfRegionalListRegional = em.merge(oldIdDepartamentoOfRegionalListRegional);
                }
            }
            for (Fiador fiadorListFiador : departamento.getFiadorList()) {
                Departamento oldIdDepartamentoOfFiadorListFiador = fiadorListFiador.getIdDepartamento();
                fiadorListFiador.setIdDepartamento(departamento);
                fiadorListFiador = em.merge(fiadorListFiador);
                if (oldIdDepartamentoOfFiadorListFiador != null) {
                    oldIdDepartamentoOfFiadorListFiador.getFiadorList().remove(fiadorListFiador);
                    oldIdDepartamentoOfFiadorListFiador = em.merge(oldIdDepartamentoOfFiadorListFiador);
                }
            }
            for (Cliente clienteListCliente : departamento.getClienteList()) {
                Departamento oldIdDepartamentoOfClienteListCliente = clienteListCliente.getIdDepartamento();
                clienteListCliente.setIdDepartamento(departamento);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdDepartamentoOfClienteListCliente != null) {
                    oldIdDepartamentoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdDepartamentoOfClienteListCliente = em.merge(oldIdDepartamentoOfClienteListCliente);
                }
            }
            for (Empleado empleadoListEmpleado : departamento.getEmpleadoList()) {
                Departamento oldIdDepartamentoOfEmpleadoListEmpleado = empleadoListEmpleado.getIdDepartamento();
                empleadoListEmpleado.setIdDepartamento(departamento);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdDepartamentoOfEmpleadoListEmpleado != null) {
                    oldIdDepartamentoOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdDepartamentoOfEmpleadoListEmpleado = em.merge(oldIdDepartamentoOfEmpleadoListEmpleado);
                }
            }
            for (Empresa empresaListEmpresa : departamento.getEmpresaList()) {
                Departamento oldIdDepartamentoOfEmpresaListEmpresa = empresaListEmpresa.getIdDepartamento();
                empresaListEmpresa.setIdDepartamento(departamento);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldIdDepartamentoOfEmpresaListEmpresa != null) {
                    oldIdDepartamentoOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldIdDepartamentoOfEmpresaListEmpresa = em.merge(oldIdDepartamentoOfEmpresaListEmpresa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDepartamento(departamento.getIdDepartamento()) != null) {
                throw new PreexistingEntityException("Departamento " + departamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDepartamento());
            List<Regional> regionalListOld = persistentDepartamento.getRegionalList();
            List<Regional> regionalListNew = departamento.getRegionalList();
            List<Fiador> fiadorListOld = persistentDepartamento.getFiadorList();
            List<Fiador> fiadorListNew = departamento.getFiadorList();
            List<Cliente> clienteListOld = persistentDepartamento.getClienteList();
            List<Cliente> clienteListNew = departamento.getClienteList();
            List<Empleado> empleadoListOld = persistentDepartamento.getEmpleadoList();
            List<Empleado> empleadoListNew = departamento.getEmpleadoList();
            List<Empresa> empresaListOld = persistentDepartamento.getEmpresaList();
            List<Empresa> empresaListNew = departamento.getEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Regional regionalListOldRegional : regionalListOld) {
                if (!regionalListNew.contains(regionalListOldRegional)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Regional " + regionalListOldRegional + " since its idDepartamento field is not nullable.");
                }
            }
            for (Fiador fiadorListOldFiador : fiadorListOld) {
                if (!fiadorListNew.contains(fiadorListOldFiador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fiador " + fiadorListOldFiador + " since its idDepartamento field is not nullable.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idDepartamento field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idDepartamento field is not nullable.");
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its idDepartamento field is not nullable.");
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
            departamento.setRegionalList(regionalListNew);
            List<Fiador> attachedFiadorListNew = new ArrayList<Fiador>();
            for (Fiador fiadorListNewFiadorToAttach : fiadorListNew) {
                fiadorListNewFiadorToAttach = em.getReference(fiadorListNewFiadorToAttach.getClass(), fiadorListNewFiadorToAttach.getIdFiador());
                attachedFiadorListNew.add(fiadorListNewFiadorToAttach);
            }
            fiadorListNew = attachedFiadorListNew;
            departamento.setFiadorList(fiadorListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            departamento.setClienteList(clienteListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            departamento.setEmpleadoList(empleadoListNew);
            List<Empresa> attachedEmpresaListNew = new ArrayList<Empresa>();
            for (Empresa empresaListNewEmpresaToAttach : empresaListNew) {
                empresaListNewEmpresaToAttach = em.getReference(empresaListNewEmpresaToAttach.getClass(), empresaListNewEmpresaToAttach.getIdEmpresa());
                attachedEmpresaListNew.add(empresaListNewEmpresaToAttach);
            }
            empresaListNew = attachedEmpresaListNew;
            departamento.setEmpresaList(empresaListNew);
            departamento = em.merge(departamento);
            for (Regional regionalListNewRegional : regionalListNew) {
                if (!regionalListOld.contains(regionalListNewRegional)) {
                    Departamento oldIdDepartamentoOfRegionalListNewRegional = regionalListNewRegional.getIdDepartamento();
                    regionalListNewRegional.setIdDepartamento(departamento);
                    regionalListNewRegional = em.merge(regionalListNewRegional);
                    if (oldIdDepartamentoOfRegionalListNewRegional != null && !oldIdDepartamentoOfRegionalListNewRegional.equals(departamento)) {
                        oldIdDepartamentoOfRegionalListNewRegional.getRegionalList().remove(regionalListNewRegional);
                        oldIdDepartamentoOfRegionalListNewRegional = em.merge(oldIdDepartamentoOfRegionalListNewRegional);
                    }
                }
            }
            for (Fiador fiadorListNewFiador : fiadorListNew) {
                if (!fiadorListOld.contains(fiadorListNewFiador)) {
                    Departamento oldIdDepartamentoOfFiadorListNewFiador = fiadorListNewFiador.getIdDepartamento();
                    fiadorListNewFiador.setIdDepartamento(departamento);
                    fiadorListNewFiador = em.merge(fiadorListNewFiador);
                    if (oldIdDepartamentoOfFiadorListNewFiador != null && !oldIdDepartamentoOfFiadorListNewFiador.equals(departamento)) {
                        oldIdDepartamentoOfFiadorListNewFiador.getFiadorList().remove(fiadorListNewFiador);
                        oldIdDepartamentoOfFiadorListNewFiador = em.merge(oldIdDepartamentoOfFiadorListNewFiador);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Departamento oldIdDepartamentoOfClienteListNewCliente = clienteListNewCliente.getIdDepartamento();
                    clienteListNewCliente.setIdDepartamento(departamento);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdDepartamentoOfClienteListNewCliente != null && !oldIdDepartamentoOfClienteListNewCliente.equals(departamento)) {
                        oldIdDepartamentoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdDepartamentoOfClienteListNewCliente = em.merge(oldIdDepartamentoOfClienteListNewCliente);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Departamento oldIdDepartamentoOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdDepartamento();
                    empleadoListNewEmpleado.setIdDepartamento(departamento);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdDepartamentoOfEmpleadoListNewEmpleado != null && !oldIdDepartamentoOfEmpleadoListNewEmpleado.equals(departamento)) {
                        oldIdDepartamentoOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdDepartamentoOfEmpleadoListNewEmpleado = em.merge(oldIdDepartamentoOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Departamento oldIdDepartamentoOfEmpresaListNewEmpresa = empresaListNewEmpresa.getIdDepartamento();
                    empresaListNewEmpresa.setIdDepartamento(departamento);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldIdDepartamentoOfEmpresaListNewEmpresa != null && !oldIdDepartamentoOfEmpresaListNewEmpresa.equals(departamento)) {
                        oldIdDepartamentoOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldIdDepartamentoOfEmpresaListNewEmpresa = em.merge(oldIdDepartamentoOfEmpresaListNewEmpresa);
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
                Long id = departamento.getIdDepartamento();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Regional> regionalListOrphanCheck = departamento.getRegionalList();
            for (Regional regionalListOrphanCheckRegional : regionalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Regional " + regionalListOrphanCheckRegional + " in its regionalList field has a non-nullable idDepartamento field.");
            }
            List<Fiador> fiadorListOrphanCheck = departamento.getFiadorList();
            for (Fiador fiadorListOrphanCheckFiador : fiadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Fiador " + fiadorListOrphanCheckFiador + " in its fiadorList field has a non-nullable idDepartamento field.");
            }
            List<Cliente> clienteListOrphanCheck = departamento.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idDepartamento field.");
            }
            List<Empleado> empleadoListOrphanCheck = departamento.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idDepartamento field.");
            }
            List<Empresa> empresaListOrphanCheck = departamento.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable idDepartamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamento);
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

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
