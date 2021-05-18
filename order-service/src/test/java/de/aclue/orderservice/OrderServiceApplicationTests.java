package de.aclue.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class OrderServiceApplicationTests extends AbstractIntegrationTest {

	@Test
	void contextLoads() {
	}

	@Test
	void ordersApiUnauthorized() throws Exception {
		this.securedMockMvc.perform(MockMvcRequestBuilders.get("/orders"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	void unsecuredApi() throws Exception {
		this.securedMockMvc.perform(MockMvcRequestBuilders.get("/unsecured/moep"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
