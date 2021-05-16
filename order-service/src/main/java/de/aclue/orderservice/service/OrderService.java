package de.aclue.orderservice.service;

import org.springframework.stereotype.Service;

import de.aclue.orderservice.persistence.entity.OrderEntity;
import de.aclue.orderservice.persistence.entity.OrderStatus;
import de.aclue.orderservice.persistence.repostiory.OrderRepository;
import de.aclue.paymentadapter.PaymentAdapter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@Service
@RequiredArgsConstructor
public class OrderService {

	private final PaymentAdapter paymentAdapter;
	private final ReservationService reservationService;
	private final OrderRepository orderRepository;
	
	public void processOrder(OrderEntity order, OrderStatus updatedStatus) {
		if(updatedStatus != OrderStatus.CONFIRMED) {
			throw new IllegalArgumentException();
		}
		
		reservationService.reserveArticle(order.getArticle(), order.getId());
		order.setStatus(OrderStatus.ARTICLE_RESERVED);
		orderRepository.save(order);
		
		paymentAdapter.doPay(order.getArticle());
		order.setStatus(OrderStatus.PAYED);
		orderRepository.save(order);
	}
}
