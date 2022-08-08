package com.java.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.microservices.limitsservice.config.Configuration;
import com.java.microservices.limitsservice.model.Limits;

@RestController
@RequestMapping("/limits")
public class LimitsController {

	@Autowired
	private Configuration configuration;

	@GetMapping()
	public Limits getLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}

}
