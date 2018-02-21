package com.claro.moose;

import com.claro.moose.util.extractor.PCEDBHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PCETests {

    @Autowired
    PCEDBHelper pcedbHelper;

	@Test
	public void getPCECatalogVersions() {
        Assert.assertEquals(555, pcedbHelper.getPCECatalogVersions().size());
	}

}
