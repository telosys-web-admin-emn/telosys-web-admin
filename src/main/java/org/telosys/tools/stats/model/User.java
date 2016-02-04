package org.telosys.tools.stats.model;

/**
 * Created by Alexandre on 04/02/2016.
 */
public class User {

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    private String login;

    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
