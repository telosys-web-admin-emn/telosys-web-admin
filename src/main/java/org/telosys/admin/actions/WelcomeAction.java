package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alexa on 30/03/2016.
 */
public class WelcomeAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        System.out.println("WORKS");
        return "users";
    }
}