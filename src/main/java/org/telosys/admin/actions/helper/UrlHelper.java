package org.telosys.admin.actions.helper;

import javax.servlet.http.HttpServletRequest;

public class UrlHelper extends AbstractParameterReplacer{
	public final static String CURRENT_URL_ATTRIBUTE = "currentUrl";
	public final static String FILTER_PARAMETER = "filter";

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
	
	public static HttpServletRequest buildUrlData(HttpServletRequest request)
	{
		request.setAttribute(CURRENT_URL_ATTRIBUTE, getFullUrl(request));
		return request;
	}
	
	public String getUrlWithFilter(HttpServletRequest request, String filterValue)
	{
		return this.replaceParam(getFullUrl(request), request, FILTER_PARAMETER, filterValue);
	}

	@Override
	protected String handleHasNoParamPattern(String fullUrl,
			String paramPattern, String paramValue) {
		return  fullUrl + "&" + paramPattern + paramValue;
	}

	@Override
	protected String handleHasParamPattern(HttpServletRequest request,
			String fullUrl, String paramPattern, String paramName, String paramValue) {
		String paramPatternWithParamValue = paramPattern + request.getParameter(paramName);
		return fullUrl.replace(paramPatternWithParamValue, paramPattern + paramValue);
	}

	@Override
	protected String handleHasNoParameters(String fullUrl, String paramPattern,
			String paramValue) {
		return fullUrl + "?" + paramPattern + paramValue;
	}
	
}
