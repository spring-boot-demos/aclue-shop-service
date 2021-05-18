package de.aclue.orderservice.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@ConfigurationProperties(prefix = "reservations")
@Getter
@Setter
@Validated
public class ReservationProperties {

	/**
	 * Url of warehouse-service
	 */
	@NotNull
	private String url;
	
}
