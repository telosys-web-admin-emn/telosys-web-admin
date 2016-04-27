package org.telosys.admin.actions.helper;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * Abstract class defining how to add or replace parameters to a URL
 */
public abstract class AbstractParameterReplacer {
	
	/**
	 * Do computation to replace or include a param in the URL
	 * @param fullUrl we do not necessarily want the url of the request but may be an intermediate one
	 * @param request
	 * @param paramName
	 * @param paramValue
	 * @return String
	 */
	protected String replaceParam(String fullUrl, HttpServletRequest request, String paramName, String paramValue)
	{
		String urlParameters = null;
		try {
			URL url = new URL(fullUrl);
			urlParameters = url.getQuery();
		} catch (MalformedURLException e) {
			// TODO : gérer l'exception
			e.printStackTrace();
		}
		boolean requestUrlHasParameters = urlParameters != null;
		// the future returned URL
		String modifiedUrl = "";
		// the pattern of the parameter in the url
		String paramPattern = paramName+"=";
		if(requestUrlHasParameters) {
			// if we don't have the parameter yet
			if(urlParameters.indexOf(paramPattern) == -1) {
				// we add it to the list of parameters
				modifiedUrl = handleHasNoParamPattern(fullUrl, paramPattern, paramValue);
			} else {
				modifiedUrl = handleHasParamPattern(request, fullUrl, paramPattern, paramName, paramValue);
			}
		} else {
			// if we have no parameters, we add the parameter as the first parameter of the URL
			modifiedUrl = handleHasNoParameters(fullUrl, paramPattern, paramValue);
		}
		return modifiedUrl;
	}
	
	/**
	 * Function called when we want to add a non existing parameter in a URL
	 * @param fullUrl
	 * @param paramPattern
	 * @param paramValue
	 * @return String the url with the parameter added
	 */
	protected abstract String handleHasNoParamPattern(String fullUrl, String paramPattern, String paramValue);
	
	/**
	 * Function called when we want to replace an existing parameter in a URL
	 * @param request
	 * @param fullUrl
	 * @param paramPattern
	 * @param paramName
	 * @param paramValue
	 * @return String the url with the replaced parameter
	 */
	protected abstract String handleHasParamPattern(HttpServletRequest request, String fullUrl, String paramPattern, String paramName, String paramValue);
	
	/**
	 * Function called when we want to add a parameter in an URL having no parameters yet
	 * @param fullUrl
	 * @param paramPattern
	 * @param paramValue
	 * @return String the url with the parameter added
	 */
	protected abstract String handleHasNoParameters(String fullUrl, String paramPattern, String paramValue);
}
