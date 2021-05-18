package de.aclue.orderservice.service;

import org.springframework.stereotype.Service;

import de.aclue.orderservice.persistence.entity.OrderEntity;
import de.aclue.orderservice.persistence.entity.OrderStatus;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final ReservationService reservationService;

	public void updateOrder(OrderEntity order, OrderStatus updatedStatus) {
		reservationService.reserveArticle(order.getArticle(), order.getId());
		order.setStatus(OrderStatus.ARTICLE_RESERVED);
		
		
		// payment
	}
	
}
