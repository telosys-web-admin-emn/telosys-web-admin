package org.telosys.admin.actions.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Gives function to provide to the views in order to add filtering and ordering
 */
public class FilterSorter extends UrlHelper{
	public final static String ASCENDING_ORDER = "ASC";
	public final static String DESCENDING_ORDER = "DESC";
	public final static String FILTER_SORTER_ATTRIBUTE = "filterSorter";
	public final static String ORDER_PARAMETER = "order";
	public final static String FILTER_PARAMETER = "filter";

	/**
	 * Add a sorter to a request
	 * @param request
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest buildSorting(HttpServletRequest request)
	{
		request.setAttribute(CURRENT_URL_ATTRIBUTE, getFullUrl(request));
		request.setAttribute(FILTER_SORTER_ATTRIBUTE, new FilterSorter());
		return request;
	}

	/**
	 * Add or replace a filter to a URL
	 * @param request
	 * @param filterName
	 * @return String
	 */
	public String getUrlWithFilter(String fullUrl, HttpServletRequest request, String filterName)
	{
		return this.replaceParam(fullUrl, request, FILTER_PARAMETER, filterName);
	}

	/**
	 * Add or replace a sorter to a URL
	 * @param fullUrl
	 * @param request
	 * @param filterName
	 * @return String
	 */
	public String getUrlWithSorter(String fullUrl, HttpServletRequest request, String filterName)
	{
		// set the future order to ASC by default
		String newOrder = ASCENDING_ORDER;
		String orderParameter = request.getParameter(ORDER_PARAMETER);
		String filterParameter = request.getParameter(FILTER_PARAMETER);
		// Behavior :
		//		* if we filter on the same filter, then we only change the direction ordering
		//		* else if we filter on a new filter, the ordering direction is ASC by default
		if(orderParameter != null && orderParameter.equals(ASCENDING_ORDER) && filterParameter != null && filterParameter.equals(filterName) )
		{
			newOrder = DESCENDING_ORDER;
		}
		return this.replaceParam(fullUrl, request, ORDER_PARAMETER, newOrder);
	}

	/**
	 * Add / replace a sorter and a filter to a URL
	 * @param fullUrl
	 * @param request
	 * @param filterName
	 * @return String
	 */
	public String getUrlWithSorterWithFilter(String fullUrl, HttpServletRequest request, String filterName)
	{
		// add the filter to the url
		String urlWithFilter = this.getUrlWithFilter(fullUrl, request, filterName);

		// add the sorter to the url
		return this.getUrlWithSorter(urlWithFilter, request, filterName);
	}
}
