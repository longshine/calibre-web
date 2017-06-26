package lx.calibre.web.config;

import org.apache.catalina.Context;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class TomcatContainerCustomizer implements EmbeddedServletContainerCustomizer {

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		if (container instanceof TomcatEmbeddedServletContainerFactory) {
			final TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
			tomcat.addContextCustomizers(new TomcatContextCustomizer() {

				@Override
				public void customize(Context context) {
					context.setUseRelativeRedirects(true);	
				}

			});
		}
	}

}
