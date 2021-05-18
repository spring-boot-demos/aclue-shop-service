package de.aclue.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
	void ordersApiAuthorized() throws Exception {
		this.securedMockMvc.perform(get("/orders").with(httpBasic("user1", "user1Pass")))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	void ordersApiForbidden() throws Exception {
		this.securedMockMvc.perform(get("/orders").with(httpBasic("user2", "user2Pass")))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "USER")
	void ordersApiAuthorized_withMockUser() throws Exception {
		this.securedMockMvc.perform(get("/orders"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@WithMockUser(roles = "ANOTHER")
	void ordersApiForbidden_withMockUser() throws Exception {
		this.securedMockMvc.perform(get("/orders"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void unsecuredApi() throws Exception {
		this.securedMockMvc.perform(MockMvcRequestBuilders.get("/unsecured/moep"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
