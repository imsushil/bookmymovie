package com.movie.booking.model;

import java.util.List;


/**
 * This class contains number of seats in a row and list of aisle seats in that row.
 * This class is used in {@link Screen}
 * @author sushil
 *
 */
public class SeatInfo {
	Integer numberOfSeats;
	List<Integer> aisleSeats;

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public List<Integer> getAisleSeats() {
		return aisleSeats;
	}

	public void setAisleSeats(List<Integer> aisleSeats) {
		this.aisleSeats = aisleSeats;
	}

	@Override
	public String toString() {
		return "SeatInfo [numberOfSeats=" + numberOfSeats + ", aisleSeats=" + aisleSeats + "]";
	}
}
