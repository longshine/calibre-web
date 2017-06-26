package lx.calibre.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.util.UriComponentsBuilder;

public class RedirectLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public RedirectLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	protected String buildRedirectUrlToLoginPage(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException) {
		String url = super.buildRedirectUrlToLoginPage(request, response, authException);
		String continueParamValue = UrlUtils.buildRequestUrl(request);
		return UriComponentsBuilder.fromHttpUrl(url).queryParam("continue", continueParamValue).toUriString();
	}
	
}
