package de.aclue.orderservice.config;

import java.time.Duration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@Configuration
@EnableConfigurationProperties(ReservationProperties.class)
public class AppConfig {

	@Bean
	RestTemplate warehouseRestTemplate(RestTemplateBuilder builder, ReservationProperties props) {
		return builder
				.rootUri(props.getUrl())
				.setConnectTimeout(Duration.ofSeconds(10L))
				.setReadTimeout(Duration.ofSeconds(15L))
				.build();
	}
}
