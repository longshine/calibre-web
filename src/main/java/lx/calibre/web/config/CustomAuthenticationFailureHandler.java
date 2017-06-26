package lx.calibre.web.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private String defaultFailureUrl;

	private String targetUrlParameter;

	public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (getDefaultFailureUrl() == null) {
			logger.debug("No failure URL set, sending 401 Unauthorized error");

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
		} else {
			saveException(request, exception);

			String failureUrl = determineFailureUrl(request);

			if (isUseForward()) {
				logger.debug("Forwarding to " + failureUrl);
				request.getRequestDispatcher(failureUrl).forward(request, response);
			} else {
				logger.debug("Redirecting to " + failureUrl);
				getRedirectStrategy().sendRedirect(request, response, failureUrl);
			}
		}
	}

	protected String determineFailureUrl(HttpServletRequest request) {
		String failureUrl = getDefaultFailureUrl();
		String targetUrlParameter = getTargetUrlParameter();
		if (targetUrlParameter != null) {
			String targetUrl = StringUtils.trimToNull(request.getParameter(targetUrlParameter));
			if (targetUrl != null) {
				return UriComponentsBuilder.fromUriString(failureUrl).queryParam(targetUrlParameter, targetUrl)
						.toUriString();
			}
		}
		return failureUrl;
	}

	protected String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		super.setDefaultFailureUrl(defaultFailureUrl);
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}

	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}

}
