package com.claro.moose.controllers;

import com.claro.moose.models.CatalogItem;
import com.claro.moose.models.Component;
import com.claro.moose.repository.ComponentRepository;
import com.claro.moose.repository.PceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Alenkart Rodriguez on 11/28/2017.
 */

@RestController
public class Api {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private PceRepository pceRepository;

    @RequestMapping("/api/person")
    public List<Component> person() {
        return componentRepository.getComponents();
    }

    @RequestMapping("/api/pce")
    public List<CatalogItem> pce(@RequestParam("version") long version) {
        return pceRepository.getPceComponents(version);
    }

    @RequestMapping("/api/getCurrentCatalogVersion")
    public Long getMooseCatalogVersion() {
        return pceRepository.getCurrentCatalogVersion();
    }

    @RequestMapping("/api/getCatalogVersion")
    public List<Long> getCatalogVersion() {
        return pceRepository.getCatalogVersion();
    }

    @RequestMapping(value  = "/api/getCatalogVersion", params = {"version"})
    public long getCatalogVersion(@RequestParam("version") long version) {
        return pceRepository.getCatalogVersion(version);
    }
}
