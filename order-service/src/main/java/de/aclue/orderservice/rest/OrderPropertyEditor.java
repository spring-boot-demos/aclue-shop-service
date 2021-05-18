package de.aclue.orderservice.rest;

import java.beans.PropertyEditorSupport;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import de.aclue.orderservice.persistence.repostiory.OrderRepository;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Jonas Keßler (jonas.kessler@kalo.de)
 */
@Component
@RequiredArgsConstructor
public class OrderPropertyEditor extends PropertyEditorSupport {

	private final OrderRepository orderRepository;

	@Override
	public void setAsText(String text) {
		long id;
		try {
			id = Long.parseLong(text);
			setValue(this.orderRepository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order mit id=" + id + " nicht gefunden.")));
		} catch (NumberFormatException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Order-Id=%s ist nicht numerisch!", text));
		}

	}

}
