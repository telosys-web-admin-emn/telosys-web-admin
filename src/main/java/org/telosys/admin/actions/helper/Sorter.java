package org.telosys.admin.actions.helper;

import javax.servlet.http.HttpServletRequest;

public class Sorter extends UrlHelper{
	public final static String ASCENDING_ORDER = "ASC";
	public final static String DESCENDING_ORDER = "DESC";
	public final static String SORTER_ATTRIBUTE = "sorter";
	public final static String ORDER_PARAMETER = "order";
	
	/**
	 * Add a sorter to a request
	 * @param request
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest buildSorting(HttpServletRequest request)
	{
		request.setAttribute(SORTER_ATTRIBUTE, new Sorter());
		return request;
	}
	
	public String getUrlWithSorterWithFilter(HttpServletRequest request, String filterName)
	{
		// add the filter to the url
		String urlWithFilter = this.replaceParam(UrlHelper.getFullUrl(request), request, UrlHelper.FILTER_PARAMETER, filterName);
		// set the future order to ASC by default
		String newOrder = ASCENDING_ORDER;
		String orderParameter = request.getParameter(ORDER_PARAMETER);
		if(orderParameter != null && orderParameter == ASCENDING_ORDER)
		{
			newOrder = DESCENDING_ORDER;
		}
		// add the sorter to the url
		String urlWithOrder = this.replaceParam(urlWithFilter, request, ORDER_PARAMETER, newOrder);
		return urlWithOrder;
	}
}
