package lx.calibre.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HTTP Utilities.
 */
public class HttpUtils {

	/**
	 * Extract real remote IP address from a {@link HttpServletRequest}.
	 * The request may be passed via some proxy or gateway.
	 * 
	 * @return the real remote IP address
	 */
	public static String getRemoteAddr(HttpServletRequest req) {
		String addr = req.getHeader("X-Forwarded-For");
		if (addr == null)
			addr = req.getHeader("X-Real-IP");
		if (addr == null)
			addr = req.getRemoteAddr();
		return addr;
	}
	
	/**
	 * Get User-Agent Header from a {@link HttpServletRequest}.
	 */
	public static String getUserAgent(HttpServletRequest req) {
		return req.getHeader("User-Agent");
	}

	/**
	 * Obtain current request through {@link RequestContextHolder}.
	 */
	public static HttpServletRequest getCurrentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
		HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
		Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
		return servletRequest;
	}

}
