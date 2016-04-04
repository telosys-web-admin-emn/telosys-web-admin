package org.telosys.tools.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexa on 29/03/2016.
 */
public class User implements Serializable
{
    private String username;
    private String email;
    private Date creationDate;
    private String country;
    private String language;

	public User(String username, String email, String creationDate, String country, String language) throws ParseException {
        this.username = username;
        this.email = email;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        this.creationDate = formater.parse(creationDate);
        this.country = country;
        this.language = language;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
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
