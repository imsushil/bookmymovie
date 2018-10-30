package com.movie.booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/health")
@RestController
public class HealthController {
	
	@RequestMapping(value="/status")
	public String checkHealth() {
		return "healthy";
	}
}
