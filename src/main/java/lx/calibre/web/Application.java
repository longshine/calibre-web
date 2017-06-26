package lx.calibre.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.WebApplicationInitializer;

@ComponentScan("lx.calibre")
@EntityScan("lx.calibre.entity")
@EnableJpaRepositories("lx.calibre.repository")
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}

}
