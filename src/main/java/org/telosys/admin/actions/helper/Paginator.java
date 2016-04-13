package org.telosys.admin.actions.helper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Gives function to provide pagination to the views 
 * and also to select a range of items server side (fake pagination)
 */
public class Paginator extends AbstractParameterReplacer {
	public final static String PAGE_PARAMETER = "page";
	public final static String CURRENT_PAGE_ATTRIBUTE = "currentPage";
	public final static String NEXT_PAGE_ATTRIBUTE = "nextPage";
	public final static String PREVIOUS_PAGE_ATTRIBUTE = "previousPage";
	public final static String MAX_PAGE_ATTRIBUTE = "maxPage";
	public final static String PAGINATOR_ATTRIBUTE = "paginator";
	
	
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
	public String getPreviousPage(HttpServletRequest request)
	{	
		int previousPage = (int) request.getAttribute(PREVIOUS_PAGE_ATTRIBUTE);
		return computePage(request, previousPage);
	}
	
	/**
	 * Get the url containing the next page
	 * @param request
	 * @return String the url with the next page
	 */
	public String getNextPage(HttpServletRequest request)
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
	public String getPage(HttpServletRequest request, int page)
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
	protected String computePage(HttpServletRequest request, int newPage)
	{
		return replaceParam(UrlHelper.getFullUrl(request), request, PAGE_PARAMETER, newPage+"");
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

	@Override
	protected String handleHasNoParamPattern(String fullUrl,
			String paramPattern, String paramValue) {
		return fullUrl + "&" + paramPattern + paramValue;
	}

	@Override
	protected String handleHasParamPattern(HttpServletRequest request,
			String fullUrl, String paramPattern, String paramName,
			String paramValue) {
		String pagePatternWithPageNumber = paramPattern + request.getAttribute(CURRENT_PAGE_ATTRIBUTE);
		// else we replace it
		return fullUrl.replace(pagePatternWithPageNumber, paramPattern + paramValue);
	}

	@Override
	protected String handleHasNoParameters(String fullUrl, String paramPattern,
			String paramValue) {
		return fullUrl + "?" + paramPattern + paramValue;
	}
}
