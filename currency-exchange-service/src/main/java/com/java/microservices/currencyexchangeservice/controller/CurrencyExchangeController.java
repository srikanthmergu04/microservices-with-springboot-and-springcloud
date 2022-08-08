package com.java.microservices.currencyexchangeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.java.microservices.currencyexchangeservice.dao.CurrencyExchangeRepository;
import com.java.microservices.currencyexchangeservice.model.CurrencyExchange;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private Environment environment;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

		if (currencyExchange == null) {
			throw new RuntimeException("Unable to Find data for " + from + " to " + to);
		}

		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));

		return currencyExchange;
	}

}
