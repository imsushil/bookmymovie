package com.movie.booking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.entity.ScreenEntity;
import com.movie.booking.exception.InvalidRequest;
import com.movie.booking.exception.InvalidScreenName;
import com.movie.booking.exception.SeatAlreadyBookedException;
import com.movie.booking.model.AllAvailableSeats;
import com.movie.booking.model.AvailableSeats;
import com.movie.booking.model.Screen;
import com.movie.booking.repository.ScreenRepository;


/**
 * This is a service class for Screen which does different operations such as 
 * adding new screen to database, fetches available seats etc.
 * 
 * @author sushil
 */
@Service
public class ScreenService {

	@Autowired
	private ScreenRepository screenRepository;

	
	/**
	 * This method adds new screen in the database
	 * 
	 * @param screen
	 */
	@Transactional
	public void addScreen(Screen screen) {
		ScreenEntity screenEntity = new ScreenEntity();
		screenEntity.setName(screen.getName());
		screenEntity.setSeatInfo(screen.getSeatInfo());

		Map<String, List<Integer>> seatsBooked = new HashMap<>();

		for (String row : screen.getSeatInfo().keySet()) {
			seatsBooked.put(row, new ArrayList<Integer>());
		}
		screenEntity.setSeatsBooked(seatsBooked);
		screenRepository.insert(screenEntity);
	}

	
	/**
	 * This method books seats
	 * 
	 * @param screenName
	 * @param seats
	 * @throws SeatAlreadyBookedException
	 * @throws InvalidRequest
	 * @throws InvalidScreenName
	 */
	@Transactional
	public void bookSeats(String screenName, Map<String, List<Integer>> seats)
			throws SeatAlreadyBookedException, InvalidRequest, InvalidScreenName {
		ScreenEntity screenEntity = screenRepository.findByName(screenName);
		if (screenEntity == null) {
			throw new InvalidScreenName("Screen name " + screenName + " is not valid.");
		}
		for (String row : seats.keySet()) {
			if (!screenEntity.getSeatInfo().containsKey(row)) {
				throw new InvalidRequest("Seat number is not valid. Invalid row " + row + ".");
			}
		}

		List<String> listOfBookedSeats = new ArrayList<>();
		for (String row : seats.keySet()) {
			for (Integer seatNo : seats.get(row)) {
				if (screenEntity.getSeatsBooked().get(row).contains(seatNo)) {
					listOfBookedSeats.add(row + "" + seatNo);
				} else {
					screenEntity.getSeatsBooked().get(row).add(seatNo);
				}
			}
		}
		if (!listOfBookedSeats.isEmpty()) {
			throw new SeatAlreadyBookedException("Seat(s) " + listOfBookedSeats + " is/are already booked.");
		}
		screenRepository.save(screenEntity);
	}

	
	/**
	 * This method returns all the available seats in the screen.
	 * 
	 * @param screenName
	 * @return AllAvailableSeats
	 * @throws InvalidScreenName
	 */
	@Transactional
	public AllAvailableSeats getAllAvailableSeats(String screenName) throws InvalidScreenName {
		ScreenEntity screenEntity = screenRepository.findByName(screenName);
		if (screenEntity == null) {
			throw new InvalidScreenName("Screen name " + screenName + " is not valid.");
		}
		AllAvailableSeats allAvailableSeats = new AllAvailableSeats();

		for (String row : screenEntity.getSeatsBooked().keySet()) {
			int seatsInRow = screenEntity.getSeatInfo().get(row).getNumberOfSeats();
			List<Integer> temp = new ArrayList<>();
			for (int i = 0; i < seatsInRow; ++i) {
				if (!screenEntity.getSeatsBooked().get(row).contains(i)) {
					temp.add(i);
				}
			}
			if (!temp.isEmpty()) {
				allAvailableSeats.getSeats().put(row, temp);
			}
		}
		return allAvailableSeats;
	}

	/**
	 * This method returns a required number the available seats around a given seat number.
	 * 
	 * @param screenName
	 * @param noOfSeats
	 * @param choice
	 * @return AvailableSeats
	 * @throws InvalidScreenName
	 * @throws SeatAlreadyBookedException
	 */
	@Transactional
	public AvailableSeats getAvailableSeats(String screenName, Integer noOfSeats, String choice)
			throws InvalidScreenName, SeatAlreadyBookedException, InvalidRequest {
		ScreenEntity screenEntity = screenRepository.findByName(screenName);
		if (screenEntity == null) {
			throw new InvalidScreenName("Screen name " + screenName + " is not valid.");
		}

		String row = choice.substring(0, 1);
		int seatNo = Integer.parseInt(choice.substring(1));

		if(!screenEntity.getSeatInfo().containsKey(row)) {
			throw new InvalidRequest(choice + " is not a valid seat.");
		}
		
		List<Integer> aisleSeats = screenEntity.getSeatInfo().get(row).getAisleSeats();
		Collections.sort(aisleSeats);

		List<Integer> bookedSeats = screenEntity.getSeatsBooked().get(row);
		Integer totalSeats = screenEntity.getSeatInfo().get(row).getNumberOfSeats();
		
		AvailableSeats availableSeats = new AvailableSeats();
		ArrayList<Integer> seatList;
		if(bookedSeats.contains(seatNo)) {
			throw new SeatAlreadyBookedException(noOfSeats + " countigous seats not found.");
		} else {
			if(seatNo < 0 || seatNo > totalSeats-1) {
				throw new InvalidRequest(choice + " is not a valid seat.");
			} else {
				int seatCount=0, cnt=0;
				seatList = new ArrayList<>();
				for(int i=0; i<noOfSeats; ++i) {
					if(seatNo-i < 0) {
						break;
					}
					if(aisleSeats.contains(seatNo-i)) {
						cnt++;
						if(cnt == 2) break;
					} else{
						cnt = 0;
					}
					if(!bookedSeats.contains(seatNo-i)) {
						seatCount++;
						seatList.add(seatNo-i);
						if(seatCount == noOfSeats) break;
					} else break;
				}
				if(aisleSeats.contains(seatNo)) {
					cnt = 1;
				}
				if(seatCount < noOfSeats) {
					for(int i=1; i<noOfSeats; ++i) {
						if(seatNo+i > totalSeats-1) {
							throw new SeatAlreadyBookedException(noOfSeats + " countigous seats not found.");
						}
						if(aisleSeats.contains(seatNo + i)) {
							cnt++;
							if(cnt == 2) break;
						} else{
							cnt = 0;
						}
						if(!bookedSeats.contains(seatNo + i)) {
							seatCount++;
							seatList.add(seatNo+i);
							if(seatCount == noOfSeats) break;
						} else break;
					}
				}
				if(seatCount < noOfSeats) {
					throw new SeatAlreadyBookedException(noOfSeats + " countigous seats not found.");
				}
			}
		}
		
		/*System.out.println("prev="+prev+" "+after);
		int start = Math.max(prev, seatNo - (noOfSeats - 1));
		int end = Math.min(after, seatNo + (noOfSeats - 1));
		System.out.println("start="+start+" "+end);
		int count = 0;
		List<Integer> temp = new ArrayList<>();
		for (int i = start; i <= end; ++i) {
			if (!bookedSeats.contains(i)) { // seat is available
				temp.add(i);
				count++;
			} else { // seat is booked already
				temp.clear();
				count = 0;
			}
			if (count == noOfSeats) {
				availableSeats.getAvailableSeats().put(row, temp);
				break;
			}
		}*/
		/*if (temp.size() != noOfSeats) {
			throw new SeatAlreadyBookedException(noOfSeats + " countigous seats not found.");
		}*/
		Collections.sort(seatList);
		availableSeats.getAvailableSeats().put(row, seatList);
		return availableSeats;
	}
}
