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
import com.coomeva.entities.Cliente;
import com.coomeva.entities.Salario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author GHOST
 */
public class SalarioJpaController implements Serializable {

    public SalarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salario salario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (salario.getClienteList() == null) {
            salario.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : salario.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            salario.setClienteList(attachedClienteList);
            em.persist(salario);
            for (Cliente clienteListCliente : salario.getClienteList()) {
                Salario oldIdSalarioOfClienteListCliente = clienteListCliente.getIdSalario();
                clienteListCliente.setIdSalario(salario);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdSalarioOfClienteListCliente != null) {
                    oldIdSalarioOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdSalarioOfClienteListCliente = em.merge(oldIdSalarioOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSalario(salario.getIdSalario()) != null) {
                throw new PreexistingEntityException("Salario " + salario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salario salario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salario persistentSalario = em.find(Salario.class, salario.getIdSalario());
            List<Cliente> clienteListOld = persistentSalario.getClienteList();
            List<Cliente> clienteListNew = salario.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            salario.setClienteList(clienteListNew);
            salario = em.merge(salario);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setIdSalario(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Salario oldIdSalarioOfClienteListNewCliente = clienteListNewCliente.getIdSalario();
                    clienteListNewCliente.setIdSalario(salario);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdSalarioOfClienteListNewCliente != null && !oldIdSalarioOfClienteListNewCliente.equals(salario)) {
                        oldIdSalarioOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdSalarioOfClienteListNewCliente = em.merge(oldIdSalarioOfClienteListNewCliente);
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
                Integer id = salario.getIdSalario();
                if (findSalario(id) == null) {
                    throw new NonexistentEntityException("The salario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salario salario;
            try {
                salario = em.getReference(Salario.class, id);
                salario.getIdSalario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salario with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = salario.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setIdSalario(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(salario);
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

    public List<Salario> findSalarioEntities() {
        return findSalarioEntities(true, -1, -1);
    }

    public List<Salario> findSalarioEntities(int maxResults, int firstResult) {
        return findSalarioEntities(false, maxResults, firstResult);
    }

    private List<Salario> findSalarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salario.class));
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

    public Salario findSalario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salario> rt = cq.from(Salario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
