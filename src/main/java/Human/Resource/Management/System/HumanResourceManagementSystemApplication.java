package Human.Resource.Management.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HumanResourceManagementSystemApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(HumanResourceManagementSystemApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HumanResourceManagementSystemApplication.class);
	}
}
