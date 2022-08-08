package com.java.rest.webservices.restfulwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.java.rest.webservices.restfulwebservices.model.Greeting;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/hello-world")
	public String greeting() {
		return "Welcome";
	}

	@GetMapping("/hello-world/{name}")
	public Greeting greetingMsg(@PathVariable String name) {

		Greeting greetingmsg = new Greeting(name);
		return greetingmsg;
	}

	@GetMapping("/msg-internationalized")
	public String greetingInternatioanlized() {
		return messageSource.getMessage("good.morning.message", null, "Default Message",
				LocaleContextHolder.getLocale());
	}

}
