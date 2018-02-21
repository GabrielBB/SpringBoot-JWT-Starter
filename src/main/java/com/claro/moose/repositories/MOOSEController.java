/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.repos;

import com.claro.moose.models.*;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MOOSEController implements Serializable {

    private static final long serialVersionUID = 1858702820751846611L;

    /*
     * public void persist(Object entity) {
     * entityManager.getTransaction().begin(); entityManager.persist(entity);
     * entityManager.getTransaction().commit(); }
     */
    public void persist(Object entity) {
       // db.save(entity);
    }


    public List<Mapping> getMappingByComponent(Component comp) {
       /* Map<String, Object> map = new HashMap<String, Object>();
        map.put("component", comp);
        List<Mapping> list = db.makeQuery("Mapping.getMappingByComponent", map);

        return list;*/
       return null;
    }

    public List<ADSLIPTVMapping> getADSLIPTVMappingByComponent(Component comp) {
     /*   Map<String, Object> map = new HashMap<String, Object>();
        map.put("component", comp);
        List<ADSLIPTVMapping> list = db.makeQuery("ADSLIPTVMapping.Mapping", map);

        return list;*/

     return null;
    }


    public Attribute getAttribute(int pid) {
       /* Map par = new HashMap();
        par.put("pid", pid);
        List<Attribute> li = db.makeQuery("getAttribute", par);

        if (li.isEmpty()) {
            return null;
        }
        return li.get(0);*/

       return null;
    }

    public void merge(Object entity) {
     //   db.update(entity);
    }


    public List makeQuery(String query, Map parameters) {
       // return db.makeQuery(query, parameters);

        return null;
    }

    public Domain getDomain(String name) {
        /*
        Map map = new HashMap();
        map.put("name", name);

        List<Domain> domains = db.makeQuery("findDomain", map);

        if (domains != null && !domains.isEmpty()) {
            return (Domain) domains.get(0);
        } else {
            return null;
        }*/

        return null;
    }

    public AttributeDomain getAttributeDomain(Attribute atributo, Domain dominio, Component componente) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("atributo", atributo);
        params.put("dominio", dominio);
        params.put("componente", componente);

        List<AttributeDomain> lst = makeQuery("AttributeDomain.buscarAtributoDomino", params);
        if (!lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }
}