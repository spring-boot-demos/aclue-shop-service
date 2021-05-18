package de.aclue.orderservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import de.aclue.orderservice.persistence.repostiory.OrderRepository;
import de.aclue.paymentadapter.PaymentAdapter;

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
	private WebApplicationContext ctx;
	protected MockMvc mockMvc;
	@Autowired
	protected MockMvc securedMockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected OrderRepository orderRepository;

	@Autowired
	private WireMockServer wireMockServer;

	@Autowired
	protected PaymentAdapter paymentAdapterMock;

	@BeforeEach
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}

	@AfterEach
	public void teardown() {

		orderRepository.deleteAll();

		try {
			Mockito.verifyNoMoreInteractions(paymentAdapterMock);
			assertThat(wireMockServer.findUnmatchedRequests().getRequests()).hasSize(0);
		} finally {
			Mockito.reset(paymentAdapterMock);
			wireMockServer.resetRequests();
			wireMockServer.resetToDefaultMappings();
		}
	}

}
