package org.telosys.tools.stats;

import org.junit.Before;
import org.junit.Test;
import org.telosys.tools.stats.impl.FilesystemStatsOverviewImpl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexa on 25/04/2016.
 */
public class FilesystemStatsOverviewTest {

    private FilesystemStatsOverview impl = new FilesystemStatsOverviewImpl(new File("fs1"));


    @Before
    public void setup() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("fs1").getFile());
        impl = new FilesystemStatsOverviewImpl(file);
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

    @Test
    public void testDiskUsage() throws ParseException, IOException {
        assertEquals(29658442, impl.getDiskUsage());
    }

}
