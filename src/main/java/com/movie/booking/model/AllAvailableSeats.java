package com.movie.booking.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is a response object for API which returns all available seats in the screen.
 * 
 * @author sushil
 */
public class AllAvailableSeats {
	private Map<String, List<Integer>> seats;

	public AllAvailableSeats() {
		seats = new HashMap<>();
	}
	
	public Map<String, List<Integer>> getSeats() {
		return seats;
	}

	public void setSeats(Map<String, List<Integer>> seats) {
		this.seats = seats;
	}
}
