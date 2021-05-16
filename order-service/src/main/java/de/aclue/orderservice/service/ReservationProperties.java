package de.aclue.orderservice.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@ConfigurationProperties(prefix = "reservation")
@Validated
@Getter
@Setter
public class ReservationProperties {
	
	@NotNull
	private String url;
	
}
