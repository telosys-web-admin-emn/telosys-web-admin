package org.telosys.tools.stats;

import org.telosys.tools.stats.exception.BadInputException;
import org.telosys.tools.stats.exception.UserNotFoundException;
import org.telosys.tools.stats.model.User;

import java.util.List;

/**
 * Created by Alexandre on 04/02/2016.
 */
public interface UsersStats {

    List<User> getUsers() throws BadInputException;

    User getUser(String login) throws UserNotFoundException, BadInputException;

}
