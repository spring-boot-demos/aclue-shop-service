package de.aclue.paymentservice.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@RestController
@Slf4j
public class PaymentController {

	@GetMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void pay() {
		log.info("Payed!");
	}
	
}
