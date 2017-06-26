package lx.calibre.web.security;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SecureFormLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractAuthenticationFilterConfigurer<H, SecureFormLoginConfigurer<H>, SecureUsernamePasswordAuthenticationFilter> {

	public SecureFormLoginConfigurer() {
		super(new SecureUsernamePasswordAuthenticationFilter(), null);
	}

	public SecureFormLoginConfigurer<H> keyStore(KeyStore keyStore) {
		getAuthenticationFilter().setKeyStore(keyStore);
		return this;
	}

	@Override
	public SecureFormLoginConfigurer<H> loginPage(String loginPage) {
		return super.loginPage(loginPage);
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}

}
