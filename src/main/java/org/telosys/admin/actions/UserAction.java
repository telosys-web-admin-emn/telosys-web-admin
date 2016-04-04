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
		    UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
		    UsersManager users = UsersManager.getInstance();
		    User m_user = users.getUserByLogin("maeln");
		    UserStats m_userstat = new UsersStatsImpl(m_user, new File(Configuration.getTelosysSaasLocation()+"/fs"));
		    httpServletRequest.setAttribute("user", m_user);
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
