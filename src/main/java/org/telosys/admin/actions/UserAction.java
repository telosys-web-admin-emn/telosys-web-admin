package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.api.TelosysUsers;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class UserAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	    try
	    {
		    String userName = httpServletRequest.getParameter("username");
		    if (userName != null) {
		    	UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
			    UsersManager users = UsersManager.getInstance();
			    User myUser = users.getUserByLogin(userName);
			    UserStats myUserstat = new UsersStatsImpl(myUser, new File(Configuration.getTelosysSaasLocation()+"/fs"));
			    httpServletRequest.setAttribute("user", myUser);
		        User mu = users.getUserByLogin(userName);
		    }
	    } catch(IOException e)
	    {
		    e.printStackTrace();
	    } catch(ParseException e)
	    {
		    e.printStackTrace();
	    }

        return "user";
    }
}
