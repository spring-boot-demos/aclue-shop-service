package de.aclue.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.aclue.orderservice.persistence.repostiory.OrderRepository;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@SpringBootTest
@ActiveProfiles("integrationtest")
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	protected OrderRepository orderRepository;
	
}
