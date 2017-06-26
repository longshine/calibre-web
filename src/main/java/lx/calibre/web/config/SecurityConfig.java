package lx.calibre.web.config;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collections;

import javax.inject.Inject;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import lx.calibre.web.security.KeyStore;
import lx.calibre.web.security.SecureFormLoginConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${lx.context}")
	private String context;

	@Inject
	private KeyStore keyStore;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	Provider provider() {
		return new BouncyCastleProvider();
	}

	@Bean
	KeyStore keyStore(Provider provider) throws NoSuchAlgorithmException {
		return new KeyStore(provider, "RSA", 1024);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/assets/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SecureFormLoginConfigurer<HttpSecurity> formLogin = new SecureFormLoginConfigurer<>();
		http.apply(formLogin);
		formLogin.keyStore(keyStore)
			.successHandler(authenticationSuccessHandler())
			.failureHandler(authenticationFailureHandler())
			.loginPage("/login").permitAll();

		ContentNegotiationStrategy contentNegotiationStrategy = http
				.getSharedObject(ContentNegotiationStrategy.class);
		if (contentNegotiationStrategy == null) {
			contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
		}
		MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(
				contentNegotiationStrategy, MediaType.APPLICATION_XHTML_XML,
				new MediaType("image", "*"), MediaType.TEXT_HTML, MediaType.TEXT_PLAIN);
		preferredMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));

		// @formatter:off
		http
			.headers()
				.frameOptions().sameOrigin().and()
			.exceptionHandling()
				.defaultAuthenticationEntryPointFor(new RedirectLoginUrlAuthenticationEntryPoint("/login"), preferredMatcher)
				.and()
			.authorizeRequests()
				.antMatchers("/books/*", "/books/*/cover", "/books/*/thumb/**").permitAll()
				.antMatchers("/books/*/**").authenticated()
				.anyRequest().permitAll();
		// @formatter:on
	}

	@Bean
	AuthenticationSuccessHandler authenticationSuccessHandler() {
		CustomAuthenticationSuccessHandler handler = new CustomAuthenticationSuccessHandler(context);
		handler.setTargetUrlParameter("continue");
		return handler;
	}

	@Bean
	AuthenticationFailureHandler authenticationFailureHandler() {
		CustomAuthenticationFailureHandler handler = new CustomAuthenticationFailureHandler("/login?error");
		handler.setTargetUrlParameter("continue");
		return handler;
	}

}
