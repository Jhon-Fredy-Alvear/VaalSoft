/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.session;

import com.coomeva.entities.TipoDeObservacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author GHOST
 */
@Stateless
public class TipoDeObservacionFacade extends AbstractFacade<TipoDeObservacion> {
    @PersistenceContext(unitName = "VaalSoftPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoDeObservacionFacade() {
        super(TipoDeObservacion.class);
    }
    
}
