package org.telosys.admin.actions.helper;

import java.util.ArrayList;
import java.util.List;
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
		// we pass the paginator to the view so that we can use methods such as getPreviousPage, getNextPage, ...
		request.setAttribute(PAGINATOR_ATTRIBUTE, new Paginator());
		return request;
	}
	
	/**
	 * Get the url containing the previous page
	 * @param request
	 * @param nextPage
	 * @return String the url with the previous page
	 */
	public static String getPreviousPage(HttpServletRequest request)
	{	
		int previousPage = (int) request.getAttribute(PREVIOUS_PAGE_ATTRIBUTE);
		return computePage(request, previousPage);
	}
	
	/**
	 * Get the url containing the next page
	 * @param request
	 * @return String the url with the next page
	 */
	public static String getNextPage(HttpServletRequest request)
	{
		int nextPage = (int) request.getAttribute(NEXT_PAGE_ATTRIBUTE);
		return computePage(request, nextPage);
	}
	
	/**
	 * Get the url containing a specific page
	 * @param request
	 * @param page
	 * @return String url with the specific page
	 */
	public static String getPage(HttpServletRequest request, int page)
	{
		return computePage(request, page);
	}
	
	/**
	 * Allow to get only items in an interval to fake pagination
	 * @param page
	 * @param maxPerPage
	 * @param providedItems
	 * @return List of items which index match the interval
	 */
	public static <T> List<T> getPaginatedItems(int page, int maxPerPage, List<T> providedItems){
		int firstItemIndex = (page-1) * maxPerPage +1;
		int itemIndex = 1;
		int lastItemIndex = page * maxPerPage;
		List<T> paginatedItems = new ArrayList<T>();
		for(T item : providedItems){
			if(itemIndex >= firstItemIndex && itemIndex <= lastItemIndex) {
				paginatedItems.add(item);
			}
			itemIndex++;
			
		}
		return paginatedItems;
	}
	
	/**
	 * Do computation to replace or include a page in the URL
	 * @param request
	 * @param direction
	 * @return String
	 */
	protected static String computePage(HttpServletRequest request, int newPage)
	{
		// the parameters of the current url in string format
		String urlParameters = request.getQueryString();
		boolean requestUrlHasParameters = urlParameters != null;
		// the future returned URL
		String paginatedUrl = "";
		// the current URl with all parameters
		String fullUrl = getFullUrl(request);
		// the pattern of the page parameter in the url
		String pagePattern = "page=";
		if(requestUrlHasParameters) {
			// if we don't have a page parameter yet
			if(urlParameters.indexOf(pagePattern) == -1) {
				// we add it to the list of parameters
				paginatedUrl = fullUrl + "&" + pagePattern + newPage;
			} else {
				String pagePatternWithPageNumber = pagePattern + request.getAttribute(CURRENT_PAGE_ATTRIBUTE);
				// else we replace it
				paginatedUrl = fullUrl.replace(pagePatternWithPageNumber, pagePattern + newPage);
			}
		} else {
			// if we have no parameters, we add the page parameter as the first parameter of the URL
			paginatedUrl = fullUrl + "?" + pagePattern + newPage;
		}
		return paginatedUrl;
	}
	
	/**
	 * Get the current page number
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
