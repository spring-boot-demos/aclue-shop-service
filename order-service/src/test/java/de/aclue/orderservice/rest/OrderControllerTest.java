package de.aclue.orderservice.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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
	void createOrder() throws Exception {
		OrderCreationBody body = new OrderCreationBody();
		body.setArticleId(123L);

		mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body)))
				.andExpect(status().isCreated());
		
		List<OrderEntity> findAll = orderRepository.findAll();
		assertThat(findAll).hasSize(1);
		
		OrderEntity actualOrder = findAll.get(0);
		assertThat(actualOrder.getArticle()).isEqualTo(body.getArticleId());
	}
	
	@Test
	void updateOrder() throws Exception {
		OrderEntity order = new OrderEntity();
		order.setArticle(123L);
		order = orderRepository.save(order);
		
		OrderUpdateBody body = new OrderUpdateBody();
		body.setStatus(OrderStatus.CONFIRMED);

		mockMvc.perform(put("/orders/{id}", order.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body)))
		.andExpect(status().isNoContent());
		
		
		OrderEntity actualOrder = orderRepository.findById(order.getId()).get();
		assertThat(actualOrder.getStatus()).isEqualTo(OrderStatus.ARTICLE_RESERVED);
	}
	
}
