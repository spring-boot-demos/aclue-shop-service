package de.aclue.orderservice.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jonas Keßler (jonas.kessler@aclue.de)
 */
@RestController
@RequestMapping("/unsecured")
public class DemoController {

	@GetMapping("/moep")
	public void doNothing() {
		
	}
}
