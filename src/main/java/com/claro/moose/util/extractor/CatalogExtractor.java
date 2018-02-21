package com.claro.moose.util.extractor;

import com.claro.moose.models.*;
import com.claro.moose.repositories.*;

import java.sql.SQLException;
import java.util.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogExtractor {

    @Autowired
    private PCEDBHelper pce;
    @Autowired
    private PCEVersionRepository pceVersionRepo;
    @Autowired
    private ComponentRepository componentRepo;
    @Autowired
    private DomainRepository domainRepo;
    @Autowired
    private EntryRepository entryRepo;
    @Autowired
    private AttributeRepository attributeRepo;
    @Autowired
    private AttributeDomainRepository attributeDomainRepo;

    private Logger log;

    private PCEVersion getMooseCatalogVersion(long versionId) {
        PCEVersion pceVersion = pceVersionRepo.findOne(versionId);

        if (pceVersion == null) {
            pceVersion = new PCEVersion(versionId);
            pceVersionRepo.save(pceVersion);
        }

        return pceVersion;
    }

    private Component mapPCEComponent(Map<String, Object> pceResult, PCEVersion version) {
        Component c = new Component();
        c.setCatalogId((Long) pceResult.get("CHILD_ID"));
        c.setCompCode((String) pceResult.get("CAPTION"));
        c.setName((String) pceResult.get("NAME_TEXT"));
        c.setPceVersion(version);
        c.setServiceType((String) pceResult.get("SERVICETYPE"));
        c.setItemType((String) pceResult.get("ITEM_TYPE"));

        log.info("*****************************************");
        log.info(" Catalog ID: " + c.getCatalogId());
        log.info(" Component CODE: " + c.getCompCode());
        log.info("*****************************************");

        return c;
    }

    public void extract(long version) {
        int progreso = 0;

        int total = pce.getPCEComponentVersionTotal(version);

        log.info("Total de componentes: " + total);
        PCEVersion mooseVersion = pceVersionRepo.findFirstByOrderByIdDesc();
        log.info("La version de Catalogo en MOOSE es: " + mooseVersion.getId());

        List<Map<String, Object>> pceComps = pce.getPCEComponentsByVersion(version);
        PCEVersion versionObj = getMooseCatalogVersion(version);

        for (Map<String, Object> pceComp : pceComps) {
            log.info("Componentes procesados:" + (++progreso + "/" + total));

            Component comp = mapPCEComponent(pceComp, versionObj);

            List<Map<String, Object>> componentAttributes = pce.getComponentAttributes(comp.getCatalogId(),
                    versionObj.getId());

            try {
                for (Map<String, Object> atributoDominio : componentAttributes) {
                    String domainName = (String) atributoDominio.get("DOMAIN_NAME");
                    Domain domain =  createDomain(domainName);

                    Attribute attr = attributeRepo.findByPropertyId((int) atributoDominio.get("PROPERTY_ID"));
                    if (attr == null) {
                        String propertyName = (String) atributoDominio.get("PROPERTY_NAME");
                        String name = (String) atributoDominio.get("NAME_TEXT");
                        int propertyId = (int) atributoDominio.get("PROPERTY_ID");

                        attr = createAttribute(name, propertyName, propertyId, versionObj);
                    }

                    AttributeDomain attrDomain = new AttributeDomain();
                    attrDomain.setAttribute(attr);
                    attrDomain.setDomain(domain);
                    attrDomain.setComponent(comp);

                    attributeDomainRepo.save(attrDomain);
                    comp.getListAttrsDomains().add(attrDomain);
                }

            } catch (Exception e) {
                log.error("Exception: ", e);
            }

            componentRepo.save(comp);
        }

     /*   Map<Long, Component> g = new HashMap();
        Map<String, String> par = new HashMap();

        par.put("itemType", "CO");
        @SuppressWarnings("unchecked")
        List<Component> comps = controller.makeQuery("Component.findAll", par);
        for (Component c : comps) {
            g.put(c.getCatalogId(), c);
        }*/

        /*  for (Long aLong : newComponents.keySet()) {
            Component comp = newComponents.get(aLong);
            List<Component> parents = getParents(comp.getCatalogId(), g);
            comp.setParentComponent(parents);
            controller.merge(comp);
            log.info("PROGRESO:" + progreso);
        }*/
    }

    private Attribute createAttribute(String name, String propertyName, int propertyID, PCEVersion pceVersion) {
        Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setPceVersion(pceVersion);
        attribute.setPropertyid(propertyID);
        attribute.setPropertyname(propertyName);

        attributeRepo.save(attribute);

        return attribute;
    }

    private Domain createDomain(String name) throws SQLException {

        Domain domain = new Domain();
        domain.setName(name);
        domain.setEntry(new ArrayList<Entry>());

        List<Map<String, Object>> entries = pce.getDomainEntries(name);

        for (Map<String, Object> entry : entries) {
            Entry e = new Entry();
            e.setDomain(domain);
            e.setDecodedValue((String) entry.get("CAPTION_EN"));
            e.setValue((String) entry.get("DISCRETE_CODE"));

            entryRepo.save(e);

            domain.getEntry().add(e);
        }

        domainRepo.save(domain);

        return domain;
    }
}