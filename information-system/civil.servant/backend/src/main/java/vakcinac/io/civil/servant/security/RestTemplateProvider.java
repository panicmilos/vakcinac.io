package vakcinac.io.civil.servant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import vakcinac.io.civil.servant.exceptions.RestTemplateResponseErrorHandler;

@Component
public class RestTemplateProvider {
	
    @Autowired 
    private RestTemplateBuilder builder;
	
	@Bean
	public RestTemplate getRestTemplate() {
		return builder
	           .errorHandler(new RestTemplateResponseErrorHandler())
	           .build();
	}
}
