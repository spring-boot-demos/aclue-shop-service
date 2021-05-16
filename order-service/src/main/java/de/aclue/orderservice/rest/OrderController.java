package de.aclue.orderservice.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.aclue.orderservice.persistence.entity.OrderEntity;
import de.aclue.orderservice.persistence.repostiory.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderRepository orderRepository;
	
	@GetMapping
	public List<OrderEntity> readAll(){
		return orderRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderEntity createOrder(@RequestBody OrderCreationBody body) {
		OrderEntity order = new OrderEntity();
		order.setArticle(body.getArticleId());
		order = this.orderRepository.save(order);
		log.info("Order created: {}", order);
		return order;
	}
	
	@Data
	public static class OrderCreationBody {
		private Long articleId;
	}
	
}
