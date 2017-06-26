package lx.calibre.web.config;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import lx.calibre.util.HttpUtils;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Value("${calibre.thumb}")
	private String thumbDir;

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Bean
	public FilterRegistrationBean shallowEtagBean() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new ShallowEtagHeaderFilter());
		frb.addUrlPatterns("/static/**");
		frb.setOrder(2);
		return frb;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/thumb/**")
				.addResourceLocations("file:" + thumbDir)
				.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		GlobalVariableInterceptor globalVariableInterceptor = new GlobalVariableInterceptor();
//		globalVariableInterceptor.setCategoryService(categoryService);
//		registry.addInterceptor(globalVariableInterceptor);
	}

	/**
	 * thymeleaf-add-parameter-to-current-url
	 * {@linkplain http://stackoverflow.com/questions/27623405/thymeleaf-add-parameter-to-current-url}
	 */
	@Bean
	public Function<String, String> currentUrlWithoutParam() {
		return new Function<String, String>() {

			@Override
			public String apply(String param) {
				HttpServletRequest request = HttpUtils.getCurrentRequest();
				String path = urlPathHelper.getPathWithinApplication(request);
				return ServletUriComponentsBuilder.fromRequest(request)
						.replacePath(path).replaceQueryParam(param)
						.scheme(null).host(null).build(false).toUriString();
			}

		};
	}
	
	@Bean
	public Function<Iterable<String>, String> currentUrlWithoutParams() {
		return new Function<Iterable<String>, String>() {

			@Override
			public String apply(Iterable<String> params) {
				HttpServletRequest request = HttpUtils.getCurrentRequest();
				String path = urlPathHelper.getPathWithinApplication(request);
				ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
				builder.replacePath(path);
				for (String param : params) {
					builder.replaceQueryParam(param);
				}
				return builder.scheme(null).host(null).build(false).toUriString();
			}

		};
	}

	public interface Function<T, R> {
		R apply(T t);
	}

}
