package com.claro.moose.util.extractor;

import com.claro.moose.models.*;
import com.claro.moose.repositories.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private PCEVersion getMooseCatalogVersion(long versionId) {
        PCEVersion pceVersion = pceVersionRepo.findOne(versionId);

        if (pceVersion == null) {
            pceVersion = new PCEVersion(versionId);
        }

        return pceVersion;
    }

    public void extract(long version) {
        int progreso = 0;

        int total = pce.getPCEComponentVersionTotal(version);

        PCEVersion pceVersion = getMooseCatalogVersion(version); // If it doesn't exists, it is created and returned

        List<Component> pceComponents = pce.getPCEComponentsByVersion(pceVersion);

        for (Component pceComponent : pceComponents) {
          log.info("Componentes procesados:" + (++progreso + "/" + total));

          // In the meantime... in the future we should delete the ID column from the Component Table and set the CATALOG_ID column as the Primary Key, so we don't have to make this search
          Component mooseComponent = componentRepo.findByCatalogId(pceComponent.getCatalogId());
          pceComponent.setId(mooseComponent == null ? null : mooseComponent.getId());
        

          componentRepo.save(pceComponent);

            List<AttributeDomain> attributeDomains = pce.getComponentAttributes(pceComponent);

            for(AttributeDomain attributeDomain : attributeDomains) {
               Domain domain = attributeDomain.getDomain();

               domainRepo.save(domain);

               List<Entry> entries = domain.getEntry();

               for(Entry entry : entries) {
                   entryRepo.save(entry);
               }

               Attribute attribute = attributeDomain.getAttribute();
               attributeRepo.save(attribute);

               attributeDomainRepo.save(attributeDomain);
            }

            /*// Let's add that as a component child
            pceComponent.setListAttrsDomains(new HashSet<AttributeDomain>(attributeDomains));*/
        }
    }
}