package org.telosys.tools.stats;

import org.junit.Before;
import org.junit.Test;
import org.telosys.tools.stats.impl.ModelStatsImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by alexa on 27/04/2016.
 */
public class ModelStatsTest {

    private ModelStats impl;

    @Before
    public void setup() {
        Configuration testConf = new Configuration();
        testConf.setTelosysFsLocation("src/test/resources/fs1");
        testConf.setModelExtension(".model");
        testConf.setTelosysDir("TelosysTools");
        this.impl = new ModelStatsImpl(new PathHelper(testConf), "maeln", "Project_A", "MyModel");
    }

    @Test
    public void testModificationDate() throws ParseException, IOException {
        Date lastModif = impl.getLastModifiedDate();
        assertTrue(new Date().getTime() > lastModif.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse("04/02/2016");
        assertTrue(d.getTime() < lastModif.getTime());
    }



}
