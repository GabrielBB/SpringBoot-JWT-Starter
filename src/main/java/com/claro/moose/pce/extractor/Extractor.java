package com.claro.moose.pce.extractor;

import com.claro.moose.models.*;
import com.claro.moose.repos.MOOSEController;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * @author Edgar Garcia
 */
public class UpdateBean implements Serializable {

    @Autowired
    private PCEDBHelper pce;
    private Logger log;

    @Autowired
    private MOOSEController controller;

    private long getMooseCatalogVersion() {
        List comp = controller.makeQuery("PCEVersion.lastVersion", null);

        if (comp != null) {
            return comp.get(0) != null ? (Long) comp.get(0) : new Long(0);
        }

        return 0;
    }

    private PCEVersion getPCEVersion(long pceVersion) {
        Map<String, Long> params = new HashMap<String, Long>();
        params.put("pceVersion", pceVersion);

        log.info("Llamando getPCEVersion con parametro: " + pceVersion);

        @SuppressWarnings("unchecked")
        List<PCEVersion> version = controller.makeQuery("PCEVersion.getVersion", params);

        PCEVersion pcVersion;

        if (version.size() > 0) {
            pcVersion = version.get(0);
        } else {
            pcVersion = new PCEVersion(pceVersion);

            controller.persist(pcVersion);
        }

        return pcVersion;
    }

    private Map<Attribute, AttributeDomain> getAtributesDomainsFromComponent(Component component) {
        Map<Attribute, AttributeDomain> map = new HashMap<Attribute, AttributeDomain>();
        for (AttributeDomain ad : component.getListAttrsDomains()) {
            map.put(ad.getAttribute(), ad);
        }

        return map;
    }

    private boolean updateCatalog(final long version) throws Exception {
        final String METHOD_NAME = "::updateCatalog::";
        int progreso = 1;
        Map<Long, Component> newComponents = new HashMap<Long, Component>();

        int total = pce.getPCEComponentVersionTotal(version) * 2;

        log.info("Porcentaje total: " + total);
        try {
            log.info(METHOD_NAME + "Trabajando con la version de Catalogo en PCE: " + version);
            long mooseVersion = getMooseCatalogVersion();
            log.info(METHOD_NAME + "La version de Catalogo en MOOSE es: " + mooseVersion);

            // Obtener los componentes y sus ultimas versiones de PCE
            List<Map<String, Object>> pceComps = pce.getPCEComponentsVersion(version);
            PCEVersion versionObj = getPCEVersion(version);

            if (log.isDebugEnabled()) {
                log.debug(METHOD_NAME + " PCEVersion : " + versionObj);
            }

            for (Map<String, Object> pceComp : pceComps) {
                log.info("PROGRESO:" + progreso);

                long catalogId = (long) pceComp.get("CHILD_ID");

                if (newComponents.get(catalogId) == null) {
                    // Buscar el componente en MOOSE
                    Component comp = findComponent(catalogId);
                    if (comp != null) {
                        comp.setCompCode((String) pceComp.get("CAPTION"));
                        comp.setName((String) pceComp.get("NAME_TEXT"));
                        comp.setPceVersion(versionObj);
                        comp.setServicetype((String) pceComp.get("SERVICETYPE"));
                        comp.setItemType((String) pceComp.get("ITEM_TYPE"));


                        log.info("****************************************************************************");
                        log.info(METHOD_NAME + " Catalog ID: " + comp.getCatalogid());
                        log.info(METHOD_NAME + " Component CODE: " + comp.getCompCode());
                        log.info(METHOD_NAME + " List Attr: " + comp.getListAttrsDomains().toString());
                        log.info(METHOD_NAME + " Name: " + comp.getName());
                        log.info(METHOD_NAME + " PCE Version: " + comp.getPceVersion());
                        log.info(METHOD_NAME + " Service Type: " + comp.getServicetype());
                        log.info(METHOD_NAME + " Item Type: " + comp.getItemType());
                        log.info("****************************************************************************");


                        // Verificar si el domino esta actualizado
                        List<Map<String, Object>> listaAtributosDominios = pce.getListAttributes(comp.getCatalogid(), versionObj.getId());
                        Map<Attribute, Domain> map = new HashMap();
                        List<AttributeDomain> ad = new ArrayList();

                        try {
                            for (Map<String, Object> atributoDominio : listaAtributosDominios) {
                                Domain domain = controller.getDomain((String) atributoDominio.get("DOMAIN_NAME"));
                                if (domain == null) {
                                    domain = createDomain((String) atributoDominio.get("DOMAIN_NAME"));
                                }

                                //Actualizar Entries de Dominios
                                List<Map<String, Object>> domainEntries = pce.getDomainEntries((String) atributoDominio.get("DOMAIN_NAME"));
                                List<Entry> entriesFaltantes = new ArrayList();
                                Map<String, Entry> mapEntries = new HashMap();

                                for (Entry e : domain.getEntry()) {
                                    mapEntries.put(e.getValue(), e);
                                }

                                if (domainEntries != null) {
                                    for (Map<String, Object> domainEnty : domainEntries) {
                                        if (!mapEntries.containsKey(domainEnty.get("DISCRETE_CODE"))) {
                                            Entry nuevoEntry = new Entry();
                                            nuevoEntry.setValue((String) domainEnty.get("DISCRETE_CODE"));
                                            nuevoEntry.setDomain(domain);
                                            nuevoEntry.setDecodedValue((String) domainEnty.get("CAPTION_EN"));

                                            controller.merge(nuevoEntry);
                                            entriesFaltantes.add(nuevoEntry);
                                        } else {
                                            Entry entryExistente = mapEntries.get(domainEnty.get("DISCRETE_CODE"));
                                            entryExistente.setDecodedValue((String) domainEnty.get("CAPTION_EN"));
                                            controller.merge(entryExistente);
                                        }

                                    }

                                    domain.getEntry().addAll(entriesFaltantes);
                                    controller.merge(domain);
                                }

                                Attribute attr = controller.getAttribute((int) atributoDominio.get("PROPERTY_ID"));
                                if (attr == null) {
                                    String propertyName = (String) atributoDominio.get("PROPERTY_NAME");
                                    String name = (String) atributoDominio.get("NAME_TEXT");
                                    int propertyId = (int) atributoDominio.get("PROPERTY_ID");

                                    attr = createAttribute(name, propertyName, propertyId, versionObj);
                                }

                                map.put(attr, domain);

                                AttributeDomain attrDomain = controller.getAttributeDomain(attr, domain, comp);
                                if (attrDomain == null) {
                                    attrDomain = new AttributeDomain();
                                    attrDomain.setAttribute(attr);
                                    attrDomain.setDomain(domain);
                                    attrDomain.setComponent(comp);

                                    controller.persist(attrDomain);
                                    ad.add(attrDomain);
                                }

                                comp.getListAttrsDomains().addAll(ad);
                                controller.merge(comp);

                            }
                        } catch (Exception e) {
                            log.error("Exception: ", e);
                        }


                        Map<Attribute, AttributeDomain> attributesDomains = getAtributesDomainsFromComponent(comp);

                        // Verificar si existe algun mapping que contenga un Entry que no exista en el dominio correspondiente
                        List<Mapping> mappings = controller.getMappingByComponent(comp);

                        for (Mapping mapping : mappings) {
                            for (MappingAttributes ma : mapping.getAttributes()) {
                                if (ma.getAttribute() == null) {
                                    continue;
                                }

                                // Primero verificar si tienen el mismo dominio
                                Attribute a = ma.getAttribute();
                                Domain d = attributesDomains.get(a).getDomain();

                                if (d == null) {
                                    continue;
                                }

                                Domain extractionDomain = map.get(a);
                                if (extractionDomain == null) {

                                    AttributeDomain attrDomain = controller.getAttributeDomain(a, d, comp);
                                    if (attrDomain == null) {
                                        attrDomain = new AttributeDomain();
                                        attrDomain.setAttribute(a);
                                        attrDomain.setDomain(d);
                                        attrDomain.setComponent(comp);

                                        controller.persist(attrDomain);
                                        controller.merge(comp);
                                    }
                                    continue;
                                }

                                if (d.getName().equalsIgnoreCase(extractionDomain.getName())) {
                                    if (ma.getValue() != null) {
                                        Entry entry = getEntryFromDomain(ma.getValue(), extractionDomain);
                                        if (entry == null) {
                                            AttributeDomain attrd = controller.getAttributeDomain(a, d, comp);
                                            Domain x = attrd.getDomain();
                                            x.getEntry().add(ma.getValue());
                                            controller.merge(x);
                                        }
                                    }
                                } else {
                                    /*
                                     * El dominio le cambio, procedo a relizar
                                     * los siguientes cambios
                                     * 1. Hago un switch de dominios en el atributo para que
                                     * apunte hacia el ultimo dominio en PCE 2.
                                     * Verifico si el Entry asociado esta
                                     * incluido en el nuevo dominio de PCE 2.1
                                     * Si el Entry esta entonces actualizo el
                                     * valor del Mapping Attribute con el ID del
                                     * entry del nuevo mapping 2.2 Si no esta el
                                     * Entry, lo agrego como un Old Entry en la
                                     * relacion AttributeDomain.
                                     */

                                    AttributeDomain attributeDomainQueDeboCambiar = attributesDomains.get(a);
                                    attributeDomainQueDeboCambiar.setDomain(extractionDomain); // Paso
                                    // #1
                                    // Resuelto
                                    controller.merge(attributeDomainQueDeboCambiar);

                                    Entry entry = getEntryFromDomain(ma.getValue(), extractionDomain);
                                    if (entry != null) { // Entry existe en el
                                        // nuevo
                                        // domino
                                        ma.setValue(entry); // Paso #2.1
                                        // Resuelto
                                        controller.merge(ma);
                                    } else {
                                        attributeDomainQueDeboCambiar.getOldEntries().add(ma.getValue());
                                        controller.merge(attributeDomainQueDeboCambiar); // Paso
                                        // #2.2
                                        // Resuelto
                                    }
                                }
                            }
                        }

                        List<ADSLIPTVMapping> adslIptvMappings = controller.getADSLIPTVMappingByComponent(comp);
                        for (ADSLIPTVMapping mapping : adslIptvMappings) {
                            Collection<AttributeDomain> lst = mapping.getComponent().getListAttrsDomains();
                            Entry bandwithActual = mapping.getBanwitdth();

                            for (AttributeDomain attrDomain : lst) {
                                int propId = attrDomain.getAttribute().getPropertyid();
                                if (propId == 32438 || propId == 31944 || propId == 32439 || propId == 32418) {
                                    Entry entryFound = getEntryFromDomain(bandwithActual, attrDomain.getDomain());

                                    if (entryFound == null) {
                                        // Verificar si el nombre del dominio es
                                        // el
                                        // mismo
                                        if (bandwithActual.getDomain().getName().equalsIgnoreCase(attrDomain.getDomain().getName())) {
                                            // Son iguales, necesito agregar este entry faltante
                                            Domain domain = attrDomain.getDomain();
                                            domain.getEntry().add(bandwithActual);

                                            controller.merge(domain);

                                        } else {
                                            // No son el mismo nombre, le cambiÃ³ el dominio, necesito cambiarle el entry
                                            if (!attrDomain.getOldEntries().contains(bandwithActual)) {
                                                attrDomain.getOldEntries().add(bandwithActual);
                                                System.out.println("*****************Mapping: " + mapping.getId());
                                                controller.merge(attrDomain);
                                            }

                                        }
                                    } else {
                                        mapping.setBanwitdth(entryFound);
                                        controller.merge(mapping);
                                    }
                                }
                            }
                        }
                        controller.merge(comp);

                        newComponents.put(comp.getCatalogid(), comp);
                    } else {
                        Component newComp = new Component();

                        newComp.setCatalogid(catalogId);
                        newComp.setCompCode((String) pceComp.get("CAPTION"));
                        newComp.setName((String) pceComp.get("NAME_TEXT"));
                        newComp.setPceVersion(versionObj);
                        newComp.setServicetype((String) pceComp.get("SERVICETYPE"));
                        newComp.setItemType((String) pceComp.get("ITEM_TYPE"));

                        log.info("****************************************************************************");
                        log.info(METHOD_NAME + " Catalog ID: " + newComp.getCatalogid());
                        log.info(METHOD_NAME + " Component CODE: " + newComp.getCompCode());
                        log.info(METHOD_NAME + " Name: " + newComp.getName());
                        log.info(METHOD_NAME + " PCE Version: " + newComp.getPceVersion());
                        log.info(METHOD_NAME + " Service Type: " + newComp.getServicetype());
                        log.info(METHOD_NAME + " Item Type: " + newComp.getItemType());
                        log.info("****************************************************************************");


                        controller.merge(newComp);

                        List<Map<String, Object>> listaAtributosDominios = pce.getListAttributes(newComp.getCatalogid(), versionObj.getId());
                        Set<AttributeDomain> ad = new HashSet<AttributeDomain>();

                        try {
                            for (Map<String, Object> atributoDominio : listaAtributosDominios) {
                                Domain domain = controller.getDomain((String) atributoDominio.get("DOMAIN_NAME"));
                                if (domain == null) {
                                    domain = createDomain((String) atributoDominio.get("DOMAIN_NAME"));
                                }

                                //Actualizar Entries de Dominios
                                List<Map<String, Object>> domainEntries = pce.getDomainEntries((String) atributoDominio.get("DOMAIN_NAME"));
                                List<Entry> entriesFaltantes = new ArrayList();
                                Map<String, Entry> mapEntries = new HashMap();

                                for (Entry e : domain.getEntry()) {
                                    mapEntries.put(e.getValue(), e);
                                }

                                if (domainEntries != null) {
                                    for (Map<String, Object> domainEntry : domainEntries) {
                                        if (!mapEntries.containsKey(domainEntry.get("DISCRETE_CODE"))) {
                                            Entry nuevoEntry = new Entry();
                                            nuevoEntry.setValue((String) domainEntry.get("DISCRETE_CODE"));
                                            nuevoEntry.setDomain(domain);
                                            nuevoEntry.setDecodedValue((String) domainEntry.get("CAPTION_EN"));

                                            controller.persist(nuevoEntry);
                                            entriesFaltantes.add(nuevoEntry);
                                        } else {
                                            Entry entryExistente = mapEntries.get(domainEntry.get("DISCRETE_CODE"));
                                            entryExistente.setDecodedValue((String) domainEntry.get("CAPTION_EN"));
                                            controller.merge(entryExistente);
                                        }
                                    }

                                    domain.getEntry().addAll(entriesFaltantes);
                                    controller.merge(domain);
                                }

                                Attribute attr = controller.getAttribute((int) atributoDominio.get("PROPERTY_ID"));
                                if (attr == null) {
                                    String propertyName = (String) atributoDominio.get("PROPERTY_NAME");
                                    String name = (String) atributoDominio.get("NAME_TEXT");
                                    int propertyId = (int) atributoDominio.get("PROPERTY_ID");

                                    attr = createAttribute(name, propertyName, propertyId, versionObj);
                                }


                                AttributeDomain attrDomain = new AttributeDomain();
                                attrDomain.setAttribute(attr);
                                attrDomain.setDomain(domain);
                                attrDomain.setComponent(newComp);

                                controller.persist(attrDomain);
                                ad.add(attrDomain);
                            }

                            if (!ad.isEmpty()) {
                                newComp.setListAttrsDomains(ad);
                                controller.merge(newComp);
                            }

                            controller.merge(newComp);
                        } catch (Exception e) {
                            log.error("Exception: ", e);
                        }
                        newComponents.put(newComp.getCatalogid(), newComp);
                    }
                }
            }


            Map<Long, Component> g = new HashMap();
            Map<String, String> par = new HashMap();

            par.put("itemType", "CO");
            @SuppressWarnings("unchecked")
            List<Component> comps = controller.makeQuery("Component.findAll", par);
            for (Component c : comps) {
                g.put(c.getCatalogid(), c);
            }

            for (Long aLong : newComponents.keySet()) {
                Component comp = newComponents.get(aLong);
                List<Component> parents = getParents(comp.getCatalogid(), g);
                comp.setParentcomponent(parents);
                controller.merge(comp);
                log.info("PROGRESO:" + progreso);
            }


            return true;

        } catch (Exception e) {
            log.error(METHOD_NAME + "Exception", e);
            return false;
        }
    }

    private List<Component> getParents(long childId, Map<Long, Component> map) {
        List<Map<String, Object>> parents = pce.getParents(childId);
        List<Component> components = new ArrayList<Component>();

        if (parents != null) {
            for (Map<String, Object> parent : parents) {
                // Construir el Objeto Component para este Parent
                long parentId = (long) parent.get("PARENT_ID");

                if (parentId == 0) {
                    components.clear();
                    break;
                }
                // Buscar parent en la base de datos de MOOSE
                // Component comp = controller.findComponent(parentId);
                Component comp = map.get(parentId);
                if (comp != null) {
                    components.add(comp);
                }
            }
        }

        return components;
    }

    private Attribute createAttribute(String name, String propertyName, int propertyID, PCEVersion pceVersion) {
        Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setPceVersion(pceVersion);
        attribute.setPropertyid(propertyID);
        attribute.setPropertyname(propertyName);

        controller.persist(attribute);

        return attribute;
    }

    private Domain createDomain(String name) throws SQLException {
        // Buscar dominio en PCE
        List<Map<String, Object>> entries = pce.getDomainEntries(name);

        Domain d = new Domain();
        d.setName(name);

        for (Map<String, Object> entry : entries) {
            Entry e = new Entry();
            e.setDomain(d);
            e.setDecodedValue((String) entry.get("CAPTION_EN"));
            e.setValue((String) entry.get("DISCRETE_CODE"));

            d.getEntry().add(e);
        }

        controller.persist(d);

        return d;
    }

    private Entry getEntryFromDomain(Entry entry, Domain d) {
        for (Entry e : d.getEntry()) {
            if (entry.getValue().equalsIgnoreCase(e.getValue())) {
                return e;
            }
        }
        return null;
    }

    private Component findComponent(long catalogId) {
        Map<String, Long> par = new HashMap<String, Long>();
        par.put("catalogId", catalogId);

        List<Component> lst = controller.makeQuery("Component.findByCatalogid", par);

        return lst.size() > 0 ? lst.get(0) : null;
    }

    public boolean persistDomains() {
        List<Map<String, Object>> domains = pce.getAllDomains();

        if (domains != null) {
            for (Map<String, Object> domain : domains) {
                Domain newDomain = new Domain();
                newDomain.setName((String) domain.get("DOMAIN_NAME"));
                newDomain.setEntry(new ArrayList());

                controller.persist(newDomain);
                log.info(" ---- PERSISTIENDO DOMINIO: " + newDomain.getName());

                List<Map<String, Object>> domainEntries = pce.getDomainEntries(newDomain.getName());

                if (domainEntries != null) {
                    for (Map<String, Object> domainEntry : domainEntries) {
                        Entry entry = new Entry();
                        entry.setDomain(newDomain);
                        entry.setDecodedValue((String) domainEntry.get("CAPTION_EN"));
                        entry.setValue((String) domainEntry.get("DISCRETE_CODE"));
                        newDomain.setName((String) domainEntry.get("DOMAIN_NAME"));

                        newDomain.getEntry().add(entry);
                    }
                }

                controller.merge(newDomain);
            }
        }

        return true;
    }
}