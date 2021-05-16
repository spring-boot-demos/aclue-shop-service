package de.aclue.warehouseservice.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@RestController
@Slf4j
public class ReservationController {

	@PostMapping("/reservations")
	@ResponseStatus(HttpStatus.CREATED)
	public ReservationResponse createReservation(@RequestBody Reservation body) {
		log.info("Successfully created reservation");
		return ReservationResponse.builder()
				.articleId(body.getArticleId())
				.orderId(body.getOrderId())
				.reservation_Id(UUID.randomUUID().toString())
				.build();
	}
	
	
	@Data
	public static class Reservation {
		private Long orderId;
		private Long articleId;
	}

	@Data
	@Builder
	public static class ReservationResponse {
		private Long orderId;
		private Long articleId;
		private String reservation_Id;
	}
}
