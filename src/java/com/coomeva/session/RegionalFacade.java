/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coomeva.session;

import com.coomeva.entities.Regional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author GHOST
 */
@Stateless
public class RegionalFacade extends AbstractFacade<Regional> {
    @PersistenceContext(unitName = "VaalSoftPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegionalFacade() {
        super(Regional.class);
    }
    
}
