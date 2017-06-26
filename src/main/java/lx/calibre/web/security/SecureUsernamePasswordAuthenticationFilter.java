package lx.calibre.web.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecureUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private KeyStore keyStore;

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = request.getParameter(getPasswordParameter());

		if (password != null && password.length() > 0 && keyStore != null) {
			String secure = request.getParameter("secure");
			if (Boolean.parseBoolean(secure)) {
				try {
					password = keyStore.decrypt(password);
				} catch (Throwable t) {
					password = null;
				}
			}
		}

		return password;
	}

	public void setKeyStore(KeyStore keyStore) {
		this.keyStore = keyStore;
	}

	protected KeyStore getKeyStore() {
		return this.keyStore;
	}

}
