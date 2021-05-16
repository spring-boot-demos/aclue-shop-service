package de.aclue.paymentadapter;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jonas Keﬂler (jonas.kessler@aclue.de)
 */
@Configuration
@ComponentScan
public class PaymentAutoConfiguration {

	@Bean
	RestTemplate paymentServiceRestTemplate(RestTemplateBuilder builder) {
		return builder 
				.rootUri("http://localhost:8082")
				.setConnectTimeout(Duration.ofSeconds(10L))
				.setReadTimeout(Duration.ofSeconds(15L))
				.build();
	}
	
}
