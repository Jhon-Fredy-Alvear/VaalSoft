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
import com.coomeva.entities.Ciudad;
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
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (ciudad.getRegionalList() == null) {
            ciudad.setRegionalList(new ArrayList<Regional>());
        }
        if (ciudad.getFiadorList() == null) {
            ciudad.setFiadorList(new ArrayList<Fiador>());
        }
        if (ciudad.getClienteList() == null) {
            ciudad.setClienteList(new ArrayList<Cliente>());
        }
        if (ciudad.getEmpleadoList() == null) {
            ciudad.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (ciudad.getEmpresaList() == null) {
            ciudad.setEmpresaList(new ArrayList<Empresa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Regional> attachedRegionalList = new ArrayList<Regional>();
            for (Regional regionalListRegionalToAttach : ciudad.getRegionalList()) {
                regionalListRegionalToAttach = em.getReference(regionalListRegionalToAttach.getClass(), regionalListRegionalToAttach.getIdRegional());
                attachedRegionalList.add(regionalListRegionalToAttach);
            }
            ciudad.setRegionalList(attachedRegionalList);
            List<Fiador> attachedFiadorList = new ArrayList<Fiador>();
            for (Fiador fiadorListFiadorToAttach : ciudad.getFiadorList()) {
                fiadorListFiadorToAttach = em.getReference(fiadorListFiadorToAttach.getClass(), fiadorListFiadorToAttach.getIdFiador());
                attachedFiadorList.add(fiadorListFiadorToAttach);
            }
            ciudad.setFiadorList(attachedFiadorList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : ciudad.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            ciudad.setClienteList(attachedClienteList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : ciudad.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            ciudad.setEmpleadoList(attachedEmpleadoList);
            List<Empresa> attachedEmpresaList = new ArrayList<Empresa>();
            for (Empresa empresaListEmpresaToAttach : ciudad.getEmpresaList()) {
                empresaListEmpresaToAttach = em.getReference(empresaListEmpresaToAttach.getClass(), empresaListEmpresaToAttach.getIdEmpresa());
                attachedEmpresaList.add(empresaListEmpresaToAttach);
            }
            ciudad.setEmpresaList(attachedEmpresaList);
            em.persist(ciudad);
            for (Regional regionalListRegional : ciudad.getRegionalList()) {
                Ciudad oldIdCiudadOfRegionalListRegional = regionalListRegional.getIdCiudad();
                regionalListRegional.setIdCiudad(ciudad);
                regionalListRegional = em.merge(regionalListRegional);
                if (oldIdCiudadOfRegionalListRegional != null) {
                    oldIdCiudadOfRegionalListRegional.getRegionalList().remove(regionalListRegional);
                    oldIdCiudadOfRegionalListRegional = em.merge(oldIdCiudadOfRegionalListRegional);
                }
            }
            for (Fiador fiadorListFiador : ciudad.getFiadorList()) {
                Ciudad oldIdCiudadOfFiadorListFiador = fiadorListFiador.getIdCiudad();
                fiadorListFiador.setIdCiudad(ciudad);
                fiadorListFiador = em.merge(fiadorListFiador);
                if (oldIdCiudadOfFiadorListFiador != null) {
                    oldIdCiudadOfFiadorListFiador.getFiadorList().remove(fiadorListFiador);
                    oldIdCiudadOfFiadorListFiador = em.merge(oldIdCiudadOfFiadorListFiador);
                }
            }
            for (Cliente clienteListCliente : ciudad.getClienteList()) {
                Ciudad oldIdCiudadOfClienteListCliente = clienteListCliente.getIdCiudad();
                clienteListCliente.setIdCiudad(ciudad);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdCiudadOfClienteListCliente != null) {
                    oldIdCiudadOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdCiudadOfClienteListCliente = em.merge(oldIdCiudadOfClienteListCliente);
                }
            }
            for (Empleado empleadoListEmpleado : ciudad.getEmpleadoList()) {
                Ciudad oldIdCiudadOfEmpleadoListEmpleado = empleadoListEmpleado.getIdCiudad();
                empleadoListEmpleado.setIdCiudad(ciudad);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdCiudadOfEmpleadoListEmpleado != null) {
                    oldIdCiudadOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdCiudadOfEmpleadoListEmpleado = em.merge(oldIdCiudadOfEmpleadoListEmpleado);
                }
            }
            for (Empresa empresaListEmpresa : ciudad.getEmpresaList()) {
                Ciudad oldIdCiudadOfEmpresaListEmpresa = empresaListEmpresa.getIdCiudad();
                empresaListEmpresa.setIdCiudad(ciudad);
                empresaListEmpresa = em.merge(empresaListEmpresa);
                if (oldIdCiudadOfEmpresaListEmpresa != null) {
                    oldIdCiudadOfEmpresaListEmpresa.getEmpresaList().remove(empresaListEmpresa);
                    oldIdCiudadOfEmpresaListEmpresa = em.merge(oldIdCiudadOfEmpresaListEmpresa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCiudad(ciudad.getIdCiudad()) != null) {
                throw new PreexistingEntityException("Ciudad " + ciudad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getIdCiudad());
            List<Regional> regionalListOld = persistentCiudad.getRegionalList();
            List<Regional> regionalListNew = ciudad.getRegionalList();
            List<Fiador> fiadorListOld = persistentCiudad.getFiadorList();
            List<Fiador> fiadorListNew = ciudad.getFiadorList();
            List<Cliente> clienteListOld = persistentCiudad.getClienteList();
            List<Cliente> clienteListNew = ciudad.getClienteList();
            List<Empleado> empleadoListOld = persistentCiudad.getEmpleadoList();
            List<Empleado> empleadoListNew = ciudad.getEmpleadoList();
            List<Empresa> empresaListOld = persistentCiudad.getEmpresaList();
            List<Empresa> empresaListNew = ciudad.getEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Regional regionalListOldRegional : regionalListOld) {
                if (!regionalListNew.contains(regionalListOldRegional)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Regional " + regionalListOldRegional + " since its idCiudad field is not nullable.");
                }
            }
            for (Fiador fiadorListOldFiador : fiadorListOld) {
                if (!fiadorListNew.contains(fiadorListOldFiador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fiador " + fiadorListOldFiador + " since its idCiudad field is not nullable.");
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idCiudad field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idCiudad field is not nullable.");
                }
            }
            for (Empresa empresaListOldEmpresa : empresaListOld) {
                if (!empresaListNew.contains(empresaListOldEmpresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empresa " + empresaListOldEmpresa + " since its idCiudad field is not nullable.");
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
            ciudad.setRegionalList(regionalListNew);
            List<Fiador> attachedFiadorListNew = new ArrayList<Fiador>();
            for (Fiador fiadorListNewFiadorToAttach : fiadorListNew) {
                fiadorListNewFiadorToAttach = em.getReference(fiadorListNewFiadorToAttach.getClass(), fiadorListNewFiadorToAttach.getIdFiador());
                attachedFiadorListNew.add(fiadorListNewFiadorToAttach);
            }
            fiadorListNew = attachedFiadorListNew;
            ciudad.setFiadorList(fiadorListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            ciudad.setClienteList(clienteListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            ciudad.setEmpleadoList(empleadoListNew);
            List<Empresa> attachedEmpresaListNew = new ArrayList<Empresa>();
            for (Empresa empresaListNewEmpresaToAttach : empresaListNew) {
                empresaListNewEmpresaToAttach = em.getReference(empresaListNewEmpresaToAttach.getClass(), empresaListNewEmpresaToAttach.getIdEmpresa());
                attachedEmpresaListNew.add(empresaListNewEmpresaToAttach);
            }
            empresaListNew = attachedEmpresaListNew;
            ciudad.setEmpresaList(empresaListNew);
            ciudad = em.merge(ciudad);
            for (Regional regionalListNewRegional : regionalListNew) {
                if (!regionalListOld.contains(regionalListNewRegional)) {
                    Ciudad oldIdCiudadOfRegionalListNewRegional = regionalListNewRegional.getIdCiudad();
                    regionalListNewRegional.setIdCiudad(ciudad);
                    regionalListNewRegional = em.merge(regionalListNewRegional);
                    if (oldIdCiudadOfRegionalListNewRegional != null && !oldIdCiudadOfRegionalListNewRegional.equals(ciudad)) {
                        oldIdCiudadOfRegionalListNewRegional.getRegionalList().remove(regionalListNewRegional);
                        oldIdCiudadOfRegionalListNewRegional = em.merge(oldIdCiudadOfRegionalListNewRegional);
                    }
                }
            }
            for (Fiador fiadorListNewFiador : fiadorListNew) {
                if (!fiadorListOld.contains(fiadorListNewFiador)) {
                    Ciudad oldIdCiudadOfFiadorListNewFiador = fiadorListNewFiador.getIdCiudad();
                    fiadorListNewFiador.setIdCiudad(ciudad);
                    fiadorListNewFiador = em.merge(fiadorListNewFiador);
                    if (oldIdCiudadOfFiadorListNewFiador != null && !oldIdCiudadOfFiadorListNewFiador.equals(ciudad)) {
                        oldIdCiudadOfFiadorListNewFiador.getFiadorList().remove(fiadorListNewFiador);
                        oldIdCiudadOfFiadorListNewFiador = em.merge(oldIdCiudadOfFiadorListNewFiador);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Ciudad oldIdCiudadOfClienteListNewCliente = clienteListNewCliente.getIdCiudad();
                    clienteListNewCliente.setIdCiudad(ciudad);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdCiudadOfClienteListNewCliente != null && !oldIdCiudadOfClienteListNewCliente.equals(ciudad)) {
                        oldIdCiudadOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdCiudadOfClienteListNewCliente = em.merge(oldIdCiudadOfClienteListNewCliente);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Ciudad oldIdCiudadOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdCiudad();
                    empleadoListNewEmpleado.setIdCiudad(ciudad);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdCiudadOfEmpleadoListNewEmpleado != null && !oldIdCiudadOfEmpleadoListNewEmpleado.equals(ciudad)) {
                        oldIdCiudadOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdCiudadOfEmpleadoListNewEmpleado = em.merge(oldIdCiudadOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Empresa empresaListNewEmpresa : empresaListNew) {
                if (!empresaListOld.contains(empresaListNewEmpresa)) {
                    Ciudad oldIdCiudadOfEmpresaListNewEmpresa = empresaListNewEmpresa.getIdCiudad();
                    empresaListNewEmpresa.setIdCiudad(ciudad);
                    empresaListNewEmpresa = em.merge(empresaListNewEmpresa);
                    if (oldIdCiudadOfEmpresaListNewEmpresa != null && !oldIdCiudadOfEmpresaListNewEmpresa.equals(ciudad)) {
                        oldIdCiudadOfEmpresaListNewEmpresa.getEmpresaList().remove(empresaListNewEmpresa);
                        oldIdCiudadOfEmpresaListNewEmpresa = em.merge(oldIdCiudadOfEmpresaListNewEmpresa);
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
                Long id = ciudad.getIdCiudad();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getIdCiudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Regional> regionalListOrphanCheck = ciudad.getRegionalList();
            for (Regional regionalListOrphanCheckRegional : regionalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Regional " + regionalListOrphanCheckRegional + " in its regionalList field has a non-nullable idCiudad field.");
            }
            List<Fiador> fiadorListOrphanCheck = ciudad.getFiadorList();
            for (Fiador fiadorListOrphanCheckFiador : fiadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Fiador " + fiadorListOrphanCheckFiador + " in its fiadorList field has a non-nullable idCiudad field.");
            }
            List<Cliente> clienteListOrphanCheck = ciudad.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idCiudad field.");
            }
            List<Empleado> empleadoListOrphanCheck = ciudad.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idCiudad field.");
            }
            List<Empresa> empresaListOrphanCheck = ciudad.getEmpresaList();
            for (Empresa empresaListOrphanCheckEmpresa : empresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Empresa " + empresaListOrphanCheckEmpresa + " in its empresaList field has a non-nullable idCiudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ciudad);
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

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
