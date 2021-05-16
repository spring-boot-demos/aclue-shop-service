package de.aclue.orderservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import de.aclue.orderservice.persistence.repostiory.OrderRepository;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@SpringBootTest
@ActiveProfiles("integrationtest")
@AutoConfigureMockMvc
@Import(TestConfig.class)
public abstract class AbstractIntegrationTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected OrderRepository orderRepository;

	@Autowired
	private WireMockServer wireMockServer;

	@AfterEach
	public void teardown() {

		orderRepository.deleteAll();

		try {
			assertThat(wireMockServer.findUnmatchedRequests().getRequests()).hasSize(0);
		} finally {
			wireMockServer.resetRequests();
			wireMockServer.resetToDefaultMappings();
		}
	}

}
