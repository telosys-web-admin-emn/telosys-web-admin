package org.telosys.tools.stats.exception;

/**
 * Created by Alexandre on 04/02/2016.
*/
public class UserNotFoundException extends Exception {

    private String username;

    public UserNotFoundException(String username) {
        super("User not found: " + username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
