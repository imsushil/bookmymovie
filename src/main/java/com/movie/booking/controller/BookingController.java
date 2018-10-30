package com.movie.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.model.AllAvailableSeats;
import com.movie.booking.model.AvailableSeats;
import com.movie.booking.model.BookSeatsRequest;
import com.movie.booking.model.Screen;
import com.movie.booking.service.ScreenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExampleProperty;

/**
 * This is entry point for all APIs related to screen.
 * 
 * @author sushil
 *
 */
@RestController
@RequestMapping(value = "/screens")
@Api(value="Movie ticket booking")
public class BookingController {

	@Autowired
	private ScreenService screenService;

	/**
	 * @param screen
	 * @return
	 */
	@ApiOperation(value="Add a movie screen")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> addScreen(@RequestBody Screen screen) {
		ResponseEntity<String> responseEntity = null;
		try {
			screenService.addScreen(screen);
			responseEntity = new ResponseEntity<>("Success: Screen added successfully.", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Error: Failed to add screen.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * 
	 * @param bookSeatsRequest
	 * @param screenName
	 * @return
	 */
	@ApiOperation(value="Reserve seat(s) with screen name")
	@RequestMapping(value = "/{screenName}/reserve", method = RequestMethod.POST)
	public ResponseEntity<String> bookSeats(@RequestBody BookSeatsRequest bookSeatsRequest,
			@PathVariable("screenName") String screenName) {
		ResponseEntity<String> responseEntity = null;
		try {
			screenService.bookSeats(screenName, bookSeatsRequest.getSeats());
			responseEntity = new ResponseEntity<>("Success: Seats booked successfully.", HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Error: Failed to reserve seats. Reason: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * 
	 * @param screenName
	 * @param status
	 * @return
	 */
	@ApiOperation(value="View all the available seats")
	@RequestMapping(value = "/{screenName}/seats", params = "status", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllAvailableSeats(@PathVariable("screenName") String screenName,
			@RequestParam String status) {
		ResponseEntity<Object> responseEntity = null;
		try {
			AllAvailableSeats availableSeats = screenService.getAllAvailableSeats(screenName);
			responseEntity = new ResponseEntity<>(availableSeats, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * 
	 * @param screenName
	 * @param noOfSeats
	 * @param choice
	 * @return
	 */
	@ApiOperation(value="Fetch a number of available seats around a particular seat choice")
	@RequestMapping(value = "/{screenName}/seats", params = { "numSeats", "choice" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getAvailableSeats(@PathVariable("screenName") String screenName,
			@RequestParam("numSeats") Integer noOfSeats, @RequestParam String choice) {
		ResponseEntity<Object> responseEntity = null;
		try {
			AvailableSeats availableSeats = screenService.getAvailableSeats(screenName, noOfSeats, choice);
			responseEntity = new ResponseEntity<>(availableSeats, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}