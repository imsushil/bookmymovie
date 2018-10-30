package com.movie.booking.exception;

public class SeatAlreadyBookedException extends Exception{

	private static final long serialVersionUID = -2966882138955604486L;

	public SeatAlreadyBookedException(String message) {
		super(message);
	}
	
}
