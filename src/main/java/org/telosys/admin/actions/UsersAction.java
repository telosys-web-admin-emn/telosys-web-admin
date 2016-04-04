package org.telosys.admin.actions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.stats.Configuration;
import org.telosys.web.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String usersFilePath = Configuration.getTelosysSaasLocation() + "/fs/user.csv";
    	UsersService usersService = new UsersService(usersFilePath);
    	try {
    		Comparator comparator = new Comparator<UsersStatsImpl>(){
    			public int compare(UsersStatsImpl u1, UsersStatsImpl u2) {
    				return u1.getLogin().compareTo(u2.getLogin());
    			}
    		};
    		httpServletRequest.setAttribute("comparator", comparator);
			httpServletRequest.setAttribute("users", usersService.getUsers());
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    	return "users";
    }

}
