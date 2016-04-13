package org.telosys.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

public class UserProjectsAction extends GenericAction {


	private Configuration configuration;

	public UserProjectsAction() {
		super();
		this.configuration = Configuration.getInstance();
	}

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	String userName = httpServletRequest.getParameter("username");
    	if (userName != null) {
    		UsersFileName.setSpecificFileName(configuration.getCsvFile().getPath());
    	    UsersManager users = UsersManager.getInstance();
    	    User mu = users.getUserByLogin(userName);
    	    httpServletRequest.setAttribute("user", mu);
    	}
        return "user";
    }
}
