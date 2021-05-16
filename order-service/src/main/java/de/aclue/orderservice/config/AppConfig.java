package de.aclue.orderservice.config;

import java.time.Duration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import de.aclue.orderservice.service.ReservationProperties;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@Configuration
@EnableConfigurationProperties(ReservationProperties.class)
public class AppConfig {

	@Bean
	RestTemplate warehouseRestTemplate(RestTemplateBuilder builder, ReservationProperties reservationProperties) {
		return builder
				.rootUri(reservationProperties.getUrl())
				.setConnectTimeout(Duration.ofSeconds(10L))
				.setReadTimeout(Duration.ofSeconds(15L))
				.build();
	}
}
