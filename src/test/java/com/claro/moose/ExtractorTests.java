package com.claro.moose;

import com.claro.moose.models.Component;
import com.claro.moose.models.PCEVersion;
import com.claro.moose.repositories.ComponentRepository;
import com.claro.moose.repositories.PCEVersionRepository;
import com.claro.moose.util.extractor.CatalogExtractor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Alenkart Rodriguez on 2/19/2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExtractorTests {

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    PCEVersionRepository pceRepository;

    @Autowired
    CatalogExtractor extractor;

    @Test
    public void findLastPceVersion() {
        PCEVersion v = pceRepository.findFirstByOrderByIdDesc();
        Assert.assertEquals(v.getId().longValue(), 20180703L);
    }
    @Test
    public void findComponentByCatalogId() {
        Component c = componentRepository.findOne(4030829L);
        Assert.assertEquals(c.getCompCode(), "MARCADO_ABREVIADO");
    }

    @Test
    public void extract() {
        extractor.extract(20190101L);
        Assert.assertTrue(true);
    }
}