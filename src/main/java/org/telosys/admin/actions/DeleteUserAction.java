package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.api.TelosysUsers;
import org.telosys.tools.commons.DirUtil;
import org.telosys.tools.stats.PathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserAction extends GenericAction
{
	public final static String USER_PARAMETER = "login";

	public DeleteUserAction()
	{
		super();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse httpServletResponse)
	{
		try
		{
			String userLogin = request.getParameter(USER_PARAMETER);
			if(userLogin != null)
			{
				TelosysUsers.deleteUser(userLogin);
				PathHelper ph = PathHelper.getInstance();
				DirUtil.deleteDirectory(ph.getUserDir(userLogin));
			}
			httpServletResponse.sendRedirect("/do/users");
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return "error";
	}
}
