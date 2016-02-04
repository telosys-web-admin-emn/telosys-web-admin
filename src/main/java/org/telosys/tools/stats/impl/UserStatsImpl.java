package org.telosys.tools.stats.impl;

import org.telosys.tools.stats.BundleStats;
import org.telosys.tools.stats.StatsProvider;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.stats.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Alexandre on 04/02/2016.
 */
public class UserStatsImpl implements UserStats {

    private StatsProvider provider;

    private Path dir;

    private User user;


    public UserStatsImpl(StatsProvider provider, Path dir, User user) {
        this.provider = provider;
        this.dir = dir;
        this.user = user;
    }

    @Override
    public String getLogin() {
        return this.user.getLogin();
    }

    @Override
    public String getMail() {
        return this.user.getEmail();
    }

    @Override
    public Date getCreationDate() throws IOException{
        BasicFileAttributes attributes = Files.readAttributes(dir, BasicFileAttributes.class);
        return new Date(attributes.lastModifiedTime().toMillis());
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public int getProjectsCount() {
        return provider.getProjectsStats(this.user.getLogin()).size();
    }

    @Override
    public List<String> getProjectsNames() throws IOException {
        List<String> names = new ArrayList<>();
        for (Path path : Files.newDirectoryStream(dir, Files::isDirectory)) {
            names.add(path.getFileName().toString());
        }
        return names;
    }

    @Override
    public int getModelsCount() {
        return provider.getModelsStats(this.user.getLogin()).size();
    }

    @Override
    public int getBundlesCount() {
        return provider.getBundlesStats(this.user.getLogin()).size();
    }

    @Override
    public List<String> getBundlesNames() {
        return provider.getBundlesStats(this.user.getLogin())
                .stream()
                .map(BundleStats::getBundleName)
                .collect(toList());
    }

    @Override
    public long getDiskUsage() throws IOException{
        return Files.size(this.dir);
    }

    @Override
    public int getGenerationsCount() {
        // TODO how to do it ?
        return 0;
    }
}
