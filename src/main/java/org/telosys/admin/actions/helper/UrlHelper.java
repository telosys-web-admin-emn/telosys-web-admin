package org.telosys.admin.actions.helper;

import javax.servlet.http.HttpServletRequest;

public class UrlHelper extends AbstractParameterReplacer{
	public final static String CURRENT_URL_ATTRIBUTE = "currentUrl";
	
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
	
	public static HttpServletRequest buildUrlHelping(HttpServletRequest request)
	{
		request.setAttribute(CURRENT_URL_ATTRIBUTE, getFullUrl(request));
		return request;
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
