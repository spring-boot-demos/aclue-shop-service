package de.aclue.orderservice.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.function.Consumer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.aclue.orderservice.AbstractIntegrationTest;
import de.aclue.orderservice.persistence.entity.OrderEntity;
import de.aclue.orderservice.persistence.entity.OrderStatus;
import de.aclue.orderservice.rest.OrderController.OrderCreationBody;
import de.aclue.orderservice.rest.OrderController.OrderUpdateBody;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
public class OrderControllerTest extends AbstractIntegrationTest {

	@Test
	void readAll() throws Exception {
		mockMvc
				.perform(MockMvcRequestBuilders.get("/orders"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(0)));
	}

	@Test
	void createOrder() throws JsonProcessingException, Exception {
		OrderCreationBody body = new OrderCreationBody();
		body.setArticleId(111111L);

		mockMvc
				.perform(post("/orders")
						.content(objectMapper.writeValueAsString(body))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.*", Matchers.hasSize(3)))
				.andExpect(jsonPath("$.article", Matchers.is(body.getArticleId().intValue())))
				.andExpect(jsonPath("$.id", Matchers.notNullValue()));

		List<OrderEntity> findAll = orderRepository.findAll();
		assertThat(findAll).hasSize(1);

	}

	@Test
	void createOrder_invalid() throws JsonProcessingException, Exception {
		createOrder_invalid(b -> b.setArticleId(null));
		// one createOrder_invalid call for each validation
	}

	private void createOrder_invalid(Consumer<OrderCreationBody> consumer) throws Exception, JsonProcessingException {

		// prepare valid body
		OrderCreationBody body = new OrderCreationBody();
		body.setArticleId(111111L);

		// make it invalid via consumer
		// makes more sense for a body with multiple validations
		consumer.accept(body);

		mockMvc
				.perform(post("/orders")
						.content(objectMapper.writeValueAsString(body))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateOrder() throws JsonProcessingException, Exception {
		OrderEntity order = new OrderEntity();
		order.setArticle(123L);
		orderRepository.save(order);

		OrderUpdateBody body = new OrderUpdateBody();
		body.setStatus(OrderStatus.CONFIRMED);

		mockMvc
				.perform(put("/orders/{id}", order.getId())
						.content(objectMapper.writeValueAsString(body))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void updateOrder_failure() throws JsonProcessingException, Exception {
		OrderEntity order = new OrderEntity();
		order.setArticle(500L);
		orderRepository.save(order);

		OrderUpdateBody body = new OrderUpdateBody();
		body.setStatus(OrderStatus.CONFIRMED);

		/*
		 * MockMvc does not support the default error handling of spring mvc. 
		 * 
		 * In a fully loaded spring application, this would return Http
		 * status 500. In this test application context with mock web environment, an exception is thrown.
		 */
		assertThrows(NestedServletException.class, () -> mockMvc
				.perform(put("/orders/{id}", order.getId())
						.content(objectMapper.writeValueAsString(body))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError()));
	}

}
