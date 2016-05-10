package org.telosys.tools.stats;

import org.junit.Before;
import org.junit.Test;
import org.telosys.tools.stats.impl.FilesystemStatsOverviewImpl;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexa on 25/04/2016.
 */
public class FilesystemStatsOverviewTest {

    private FilesystemStatsOverview impl;


    @Before
    public void setup() {
        Configuration testConf = new Configuration();
        testConf.setTelosysFsLocation("src/test/resources/fs1");
        this.impl = new FilesystemStatsOverviewImpl(new PathHelper(testConf));
    }


    @Test
    public void testUsersCount() throws ParseException {
        assertEquals(2, impl.getUsersCount());
    }

    @Test
    public void testProjectsCount() throws ParseException, IOException {
        assertEquals(5, impl.getProjectsCount());
    }

    @Test
    public void testModelsCount() throws ParseException, IOException {
        assertEquals(6, impl.getModelsCount());
    }
}
