package de.aclue.paymentadapter;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentAdapter {

	private final RestTemplate paymentServiceRestTemplate;
	
	public void doPay(Long amount){
		paymentServiceRestTemplate.getForObject("/", Void.class);
		log.info("Payment successfull: {} EUR", amount);
	}

}
