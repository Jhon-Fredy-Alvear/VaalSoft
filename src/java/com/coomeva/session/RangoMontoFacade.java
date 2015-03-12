/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.session;

import com.coomeva.entities.RangoMonto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author GHOST
 */
@Stateless
public class RangoMontoFacade extends AbstractFacade<RangoMonto> {
    @PersistenceContext(unitName = "VaalSoftPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RangoMontoFacade() {
        super(RangoMonto.class);
    }
    
}
