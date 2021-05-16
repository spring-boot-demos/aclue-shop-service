package de.aclue.orderservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@TestConfiguration
public class TestConfig {

	private static final boolean PRODUCE_NEW_RECORDS = false;

	  @Bean(destroyMethod = "shutdown")
	  public WireMockServer wireMockServer() {
	    return WireMockUtils.initMockServer("warehouse-service", "http://localhost:8081", 60200, 0, PRODUCE_NEW_RECORDS);
	  }
	  
}
