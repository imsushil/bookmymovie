package com.movie.booking.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.movie.booking.model.SeatInfo;


/**
 * This is an entity object for Screen used for store/retrieve from/to database.
 * 
 * @author sushil
 *
 */
@Document(collection = "screen")
public class ScreenEntity {
	@Id
	private String id;

	private String name;

	private Map<String, SeatInfo> seatInfo;

	private Map<String, List<Integer>> seatsBooked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Map<String, List<Integer>> getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(Map<String, List<Integer>> seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	@Override
	public String toString() {
		return "ScreenEntity [id=" + id + ", name=" + name + ", seatInfo=" + seatInfo + ", seatsBooked=" + seatsBooked
				+ "]";
	}

}
