package com.movie.booking.model;

import java.util.Map;


/**
 * This is model class for Screen which contains name of the screen and seat related information.
 * 
 * @author sushil
 *
 */
public class Screen {
	String name;
	Map<String, SeatInfo> seatInfo;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, SeatInfo> getSeatInfo() {
		return seatInfo;
	}
	public void setSeatInfo(Map<String, SeatInfo> seatInfo) {
		this.seatInfo = seatInfo;
	}

	@Override
	public String toString() {
		return "Screen [name=" + name + ", seatInfo=" + seatInfo + "]";
	}


}
