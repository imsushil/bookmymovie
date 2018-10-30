package com.movie.booking.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is a response object for API which returns a particular number of available seats around a given seat number.
 * 
 * @author sushil
 */
public class AvailableSeats {
	private Map<String, List<Integer>> availableSeats;

	public AvailableSeats() {
		availableSeats = new HashMap<String, List<Integer>>();
	}

	public Map<String, List<Integer>> getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Map<String, List<Integer>> availableSeats) {
		this.availableSeats = availableSeats;
	}

}
