package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.api.TelosysUsers;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public class UserAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	    UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
	    UsersManager users = UsersManager.getInstance();
	    User mu = users.getUserByLogin("maeln");
	    httpServletRequest.setAttribute("user", mu);

        return "user";
    }
}
