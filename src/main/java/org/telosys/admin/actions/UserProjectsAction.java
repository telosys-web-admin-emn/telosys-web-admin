package org.telosys.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

public class UserProjectsAction extends GenericAction {


	private PathHelper pathHelper;

	public UserProjectsAction() {
		super();
		this.pathHelper = PathHelper.getInstance();
	}

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	String userName = httpServletRequest.getParameter("username");
    	if (userName != null) {
    		UsersFileName.setSpecificFileName(pathHelper.getCsvFile().getPath());
    	    UsersManager users = UsersManager.getInstance();
    	    User mu = users.getUserByLogin(userName);
    	    httpServletRequest.setAttribute("user", mu);
    	}
        return "user";
    }
}
