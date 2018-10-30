package com.movie.booking.model;

import java.util.List;
import java.util.Map;


/**
 * This is request object for API which books seats.
 * 
 * @author sushil
 *
 */
public class BookSeatsRequest {
	private Map<String, List<Integer>> seats;

	public Map<String, List<Integer>> getSeats() {
		return seats;
	}

	public void setSeats(Map<String, List<Integer>> seats) {
		this.seats = seats;
	}
}
