package org.telosys.tools.stats.dto;

import org.telosys.tools.users.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexa on 04/05/2016.
 */
public class UserDTO {

    private String login;
    private String mail;
    private String firstName;
    private String lastName;
    private String lastConnectionDate;
    private String creationDate;
    private String country;
    private String language;


    public UserDTO(String login, String mail, String firstName, String lastName, String lastConnectionDate, String creationDate,
                   String country, String language) {
        this.login = login;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastConnectionDate = lastConnectionDate;
        this.creationDate = creationDate;
        this.country = country;
        this.language = language;
    }

    public static UserDTO fromUser(User user, String datePattern) {
        DateFormat sdf = new SimpleDateFormat(datePattern);
        UserDTO dto = new UserDTO(user.getLogin(), user.getMail(), user.getFirstName(), user.getLastName(),
                sdf.format(user.getLastConnectionDate()), sdf.format(user.getCreationDate()), user.getCountry(), user.getLanguage());
        return dto;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(String lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

