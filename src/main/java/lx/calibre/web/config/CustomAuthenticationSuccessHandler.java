package lx.calibre.web.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Pattern ALLOWED = Pattern.compile("^/([^/].*)?$");

	private final String context;

	public CustomAuthenticationSuccessHandler(String context) {
		this.context = context;
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String defaultTargetUrl = getDefaultTargetUrl();
		String targetUrl = super.determineTargetUrl(request, response);
		if (defaultTargetUrl.equals(targetUrl) || ALLOWED.matcher(targetUrl).matches()) {
			return appendContext(targetUrl);
		} else {
			return appendContext(defaultTargetUrl);
		}
	}

	private String appendContext(String url) {
		if (context != null && context.length() > 0)
			url = context + url;
		return url;
	}

}
