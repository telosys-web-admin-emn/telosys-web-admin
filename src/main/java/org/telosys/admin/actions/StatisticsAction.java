package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatisticsAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "statistics";
    }
}
