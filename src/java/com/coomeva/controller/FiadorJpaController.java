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
import com.coomeva.entities.Genero;
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Fiador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class FiadorJpaController implements Serializable {

    public FiadorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fiador fiador) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (fiador.getClienteList() == null) {
            fiador.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Barrio idBarrio = fiador.getIdBarrio();
            if (idBarrio != null) {
                idBarrio = em.getReference(idBarrio.getClass(), idBarrio.getIdBarrio());
                fiador.setIdBarrio(idBarrio);
            }
            Ciudad idCiudad = fiador.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                fiador.setIdCiudad(idCiudad);
            }
            Departamento idDepartamento = fiador.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                fiador.setIdDepartamento(idDepartamento);
            }
            Genero idGenero = fiador.getIdGenero();
            if (idGenero != null) {
                idGenero = em.getReference(idGenero.getClass(), idGenero.getIdGenero());
                fiador.setIdGenero(idGenero);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : fiador.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            fiador.setClienteList(attachedClienteList);
            em.persist(fiador);
            if (idBarrio != null) {
                idBarrio.getFiadorList().add(fiador);
                idBarrio = em.merge(idBarrio);
            }
            if (idCiudad != null) {
                idCiudad.getFiadorList().add(fiador);
                idCiudad = em.merge(idCiudad);
            }
            if (idDepartamento != null) {
                idDepartamento.getFiadorList().add(fiador);
                idDepartamento = em.merge(idDepartamento);
            }
            if (idGenero != null) {
                idGenero.getFiadorList().add(fiador);
                idGenero = em.merge(idGenero);
            }
            for (Cliente clienteListCliente : fiador.getClienteList()) {
                Fiador oldIdFiadorOfClienteListCliente = clienteListCliente.getIdFiador();
                clienteListCliente.setIdFiador(fiador);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdFiadorOfClienteListCliente != null) {
                    oldIdFiadorOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdFiadorOfClienteListCliente = em.merge(oldIdFiadorOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFiador(fiador.getIdFiador()) != null) {
                throw new PreexistingEntityException("Fiador " + fiador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fiador fiador) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Fiador persistentFiador = em.find(Fiador.class, fiador.getIdFiador());
            Barrio idBarrioOld = persistentFiador.getIdBarrio();
            Barrio idBarrioNew = fiador.getIdBarrio();
            Ciudad idCiudadOld = persistentFiador.getIdCiudad();
            Ciudad idCiudadNew = fiador.getIdCiudad();
            Departamento idDepartamentoOld = persistentFiador.getIdDepartamento();
            Departamento idDepartamentoNew = fiador.getIdDepartamento();
            Genero idGeneroOld = persistentFiador.getIdGenero();
            Genero idGeneroNew = fiador.getIdGenero();
            List<Cliente> clienteListOld = persistentFiador.getClienteList();
            List<Cliente> clienteListNew = fiador.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idFiador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBarrioNew != null) {
                idBarrioNew = em.getReference(idBarrioNew.getClass(), idBarrioNew.getIdBarrio());
                fiador.setIdBarrio(idBarrioNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                fiador.setIdCiudad(idCiudadNew);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                fiador.setIdDepartamento(idDepartamentoNew);
            }
            if (idGeneroNew != null) {
                idGeneroNew = em.getReference(idGeneroNew.getClass(), idGeneroNew.getIdGenero());
                fiador.setIdGenero(idGeneroNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            fiador.setClienteList(clienteListNew);
            fiador = em.merge(fiador);
            if (idBarrioOld != null && !idBarrioOld.equals(idBarrioNew)) {
                idBarrioOld.getFiadorList().remove(fiador);
                idBarrioOld = em.merge(idBarrioOld);
            }
            if (idBarrioNew != null && !idBarrioNew.equals(idBarrioOld)) {
                idBarrioNew.getFiadorList().add(fiador);
                idBarrioNew = em.merge(idBarrioNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getFiadorList().remove(fiador);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getFiadorList().add(fiador);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getFiadorList().remove(fiador);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getFiadorList().add(fiador);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            if (idGeneroOld != null && !idGeneroOld.equals(idGeneroNew)) {
                idGeneroOld.getFiadorList().remove(fiador);
                idGeneroOld = em.merge(idGeneroOld);
            }
            if (idGeneroNew != null && !idGeneroNew.equals(idGeneroOld)) {
                idGeneroNew.getFiadorList().add(fiador);
                idGeneroNew = em.merge(idGeneroNew);
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Fiador oldIdFiadorOfClienteListNewCliente = clienteListNewCliente.getIdFiador();
                    clienteListNewCliente.setIdFiador(fiador);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdFiadorOfClienteListNewCliente != null && !oldIdFiadorOfClienteListNewCliente.equals(fiador)) {
                        oldIdFiadorOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdFiadorOfClienteListNewCliente = em.merge(oldIdFiadorOfClienteListNewCliente);
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
                Long id = fiador.getIdFiador();
                if (findFiador(id) == null) {
                    throw new NonexistentEntityException("The fiador with id " + id + " no longer exists.");
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
            Fiador fiador;
            try {
                fiador = em.getReference(Fiador.class, id);
                fiador.getIdFiador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fiador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = fiador.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fiador (" + fiador + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idFiador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Barrio idBarrio = fiador.getIdBarrio();
            if (idBarrio != null) {
                idBarrio.getFiadorList().remove(fiador);
                idBarrio = em.merge(idBarrio);
            }
            Ciudad idCiudad = fiador.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getFiadorList().remove(fiador);
                idCiudad = em.merge(idCiudad);
            }
            Departamento idDepartamento = fiador.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getFiadorList().remove(fiador);
                idDepartamento = em.merge(idDepartamento);
            }
            Genero idGenero = fiador.getIdGenero();
            if (idGenero != null) {
                idGenero.getFiadorList().remove(fiador);
                idGenero = em.merge(idGenero);
            }
            em.remove(fiador);
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

    public List<Fiador> findFiadorEntities() {
        return findFiadorEntities(true, -1, -1);
    }

    public List<Fiador> findFiadorEntities(int maxResults, int firstResult) {
        return findFiadorEntities(false, maxResults, firstResult);
    }

    private List<Fiador> findFiadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fiador.class));
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

    public Fiador findFiador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fiador.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fiador> rt = cq.from(Fiador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
