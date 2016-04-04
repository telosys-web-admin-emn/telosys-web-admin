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
    		
    		httpServletRequest.setAttribute("comparator", this.getUsersComparator(httpServletRequest));
			httpServletRequest.setAttribute("users", usersService.getUsers());
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    	return "users";
    }
    
    /**
     * Get the users comparator according to the filter parameter. Only ascending order is handled at the moment
     * @param httpServletRequest
     * @return Comparator
     */
    protected Comparator<UsersStatsImpl> getUsersComparator(HttpServletRequest httpServletRequest)
    {
    	Comparator<UsersStatsImpl> comparator = new Comparator<UsersStatsImpl>(){
			public int compare(UsersStatsImpl u1, UsersStatsImpl u2) {
				String filter = httpServletRequest.getParameter("filter");
		    	if(filter == null) {
		    		filter = "login";
		    	}
		    	String order = httpServletRequest.getParameter("order");
		    	// the result of the comparison
		    	int compared = 0;
				switch(filter){
					case "creationDate":
						compared = u1.getCreationDate().compareTo(u2.getCreationDate());
						break;
					case "mail":
						compared =  u1.getMail().compareTo(u2.getMail());
						break;
					case "diskUsage":
						compared = Double.compare(u1.getDiskUsage(), u2.getDiskUsage());
						break;
					case "projectsCount":
						compared = Integer.compare(u1.getProjectsCount(), u2.getProjectsCount());
						break;
					case "modelsCount":
						compared = Integer.compare(u1.getModelsCount(), u2.getModelsCount());
						break;
					case "generationsCount":
						compared = Integer.compare(u1.getGenerationsCount(), u2.getGenerationsCount());
						break;
					default:
						compared = u1.getLogin().compareTo(u2.getLogin());
				}
				// check if we have to reverse the order
				if(order != null && order.equals("DESC")) {
					compared = -compared;
				}
				return compared;
			}
		};
		return comparator;
    }

}
