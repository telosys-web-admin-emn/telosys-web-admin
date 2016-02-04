package org.telosys.tools.stats.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.StatsProvider;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.stats.UsersStats;
import org.telosys.tools.stats.exception.BadInputException;
import org.telosys.tools.stats.exception.UserNotFoundException;
import org.telosys.tools.stats.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre on 04/02/2016.
 */
public class UsersStatsImpl implements UsersStats {


    private StatsProvider provider;


    public UsersStatsImpl(StatsProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<User> getUsers() throws BadInputException {
        try {
            CsvMapper mapper = new CsvMapper();
            // important: we need "array wrapping" (see next section) here:
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            File csvFile = new File(provider.getRoot() + File.separator + Configuration.getUsersCsv()); // or from String, URL etc
            MappingIterator<String[]> it = mapper.readerFor(String[].class)
                    .with(mapper.schema().withColumnSeparator(';'))
                    .readValues(csvFile);


            List<User> users = new ArrayList<>();
            while (it.hasNext()) {
                String[] row = it.next();

                // if the line has the correct number of rows...
                if (row.length == 8) {
                    User user = new User(row[0], row[2]);
                    users.add(user);
                } else throw new BadInputException("Bad number of rows in the users' file");

            }
            return users;

        } catch(Exception e) {
            throw new BadInputException(e.getMessage());
        }
    }

    // TODO perfs ?
    @Override
    public User getUser(String login) throws UserNotFoundException, BadInputException {
        return this.getUsers()
                .stream()
                .filter(u -> u.getLogin().equals(login))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException(login));
    }

}
