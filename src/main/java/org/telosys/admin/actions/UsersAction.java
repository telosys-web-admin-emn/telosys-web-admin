package org.telosys.admin.actions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.web.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	String s = File.separator;
		String usersFilePath = "C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s+"users.csv"+s;
    	UsersService usersService = new UsersService(usersFilePath);
    	try {
			httpServletRequest.setAttribute("users", usersService.getUsers());
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    	return "users";
    }

}
