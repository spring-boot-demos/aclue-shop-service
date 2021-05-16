package de.aclue.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

	private final RestTemplate warehouseRestTemplate;
	
	public void reserveArticle(Long articleId, Long orderId) {
		Reservation reservation = new Reservation();
		reservation.setArticleId(articleId);
		reservation.setOrderId(orderId);
		
		ReservationResponse response = warehouseRestTemplate.postForObject("/reservations", reservation, ReservationResponse.class);
		log.info("Reservation success, id: {}", response.getReservationId());
	}
	
	@Getter
	@Setter
	public static class Reservation {
		private Long orderId;
		private Long articleId;
	}
	
	@Getter
	@Setter
	public static class ReservationResponse {
		private Long orderId;
		private Long articleId;
		@JsonProperty("reservation_Id")
		private String reservationId;
	}
}

