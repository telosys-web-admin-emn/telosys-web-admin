package org.telosys.admin.actions.helper;

import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Paginator {
	public final static String PAGE_PARAMETER = "page";
	public final static String CURRENT_PAGE_ATTRIBUTE = "currentPage";
	public final static String NEXT_PAGE_ATTRIBUTE = "nextPage";
	public final static String PREVIOUS_PAGE_ATTRIBUTE = "previousPage";
	public final static String MAX_PAGE_ATTRIBUTE = "maxPage";
	public final static String CURRENT_URL_ATTRIBUTE = "currentUrl";
	public final static String PAGINATOR_ATTRIBUTE = "paginator";
	
	/**
	 * Get the full URL with all parameters from request
	 * @param request
	 * @return String the URL
	 */
	public static String getFullUrl(HttpServletRequest request)
	{
		String baseUrl = request.getRequestURL() + "";
    	String queryParameters = request.getQueryString();
    	return queryParameters == null ? baseUrl : baseUrl + "?" + queryParameters;
	}
	
	/**
	 * Add the pagination attributes to the request 
	 * @param request
	 * @param maxPage
	 * @return
	 */
	public static HttpServletRequest buildPagination(HttpServletRequest request, int maxPage)
	{
		int page = getCurrentPage(request);
		request.setAttribute(CURRENT_PAGE_ATTRIBUTE, page);
		request.setAttribute(PREVIOUS_PAGE_ATTRIBUTE, page == 1 ? 1 : page-1);
		request.setAttribute(NEXT_PAGE_ATTRIBUTE, maxPage == page ? maxPage : page+1);
		request.setAttribute(MAX_PAGE_ATTRIBUTE, maxPage);
		request.setAttribute(CURRENT_URL_ATTRIBUTE, getFullUrl(request));
		request.setAttribute(PAGINATOR_ATTRIBUTE, new Paginator());
		return request;
	}
	
	/**
	 * Allow to compute the url in order to include / replace the page parameter
	 * @param request
	 * @param nextPage
	 * @return String the computed url paginated
	 */
	public static String getPreviousPage(HttpServletRequest request)
	{	
		int previousPage = (int) request.getAttribute(PREVIOUS_PAGE_ATTRIBUTE);
		return computePage(request, previousPage);
	}
	
	public static String getNextPage(HttpServletRequest request)
	{
		int nextPage = (int) request.getAttribute(NEXT_PAGE_ATTRIBUTE);
		return computePage(request, nextPage);
	}
	
	public static String getPage(HttpServletRequest request, int page)
	{
		return computePage(request, page);
	}
	
	/**
	 * Compute the new page according to a direction (previous or next)
	 * @param request
	 * @param direction
	 * @return String
	 */
	protected static String computePage(HttpServletRequest request, int newPage)
	{
		String urlParameters = request.getQueryString();
		boolean requestUrlHasParameters = urlParameters != null;
		String paginatedUrl = "";
		String fullUrl = getFullUrl(request);
		if(requestUrlHasParameters) {
			if(urlParameters.indexOf("page=") == -1) {
				paginatedUrl = fullUrl + "&page=" + newPage;
			} else {
				String pagePattern = "page="+request.getAttribute(CURRENT_PAGE_ATTRIBUTE);
				paginatedUrl = fullUrl.replace(pagePattern, "page="+newPage);
			}
		} else {
			paginatedUrl = fullUrl + "?page=" + newPage;
		}
		return paginatedUrl;
	}
	
	/**
	 * Get the page number
	 * @param request
	 * @return int the page number
	 */
	protected static int getCurrentPage(HttpServletRequest request)
    {
    	int page = 1;
    	String pageStringFormat = request.getParameter(PAGE_PARAMETER);
    	if(pageStringFormat != null) {
    		page = Integer.parseInt(pageStringFormat);
    	}
    	return page;
    }
}
