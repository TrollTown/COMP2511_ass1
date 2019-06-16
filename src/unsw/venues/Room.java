package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Room {
	private String name;
	private String size;
	private ArrayList<TimePeriod> occupancy;
	
	/**
	 * Constructor for Room object: 2 arguments
	 * @param name Name of the room
	 * @param size Size of the room
	 */
	public Room (String name, String size) {
		this.name = name;
		this.size= size;
		this.occupancy = new ArrayList<TimePeriod>();
	}
	
	/**
	 * This method returns the name of the room
	 * @return Name of the room
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * This method returns the size of the room
	 * @return Size of the room
	 */
	public String getSize() {
		return this.size;
	}
	
	/**
	 * This method checks whether or not a room is available between a certain start date and end date.
	 * This method takes into account whether or not a room is already reserved under the same reservation ID
	 * when attempting to fulfill a change
	 * @param id Reservation ID 
	 * @param startDate Start date of the reservation
	 * @param endDate End date of the reservation
	 * @return
	 */
	public boolean checkAvailability(String id, LocalDate startDate, LocalDate endDate) {
		for (int i = 0; i < this.occupancy.size(); i++) {
			if (this.occupancy.get(i).getReservationID().equals(id)) continue;
			if (checkDateRanges(startDate, endDate, this.occupancy.get(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method sets a particular time period (start date -> end date) as being occupied
	 * @param id ID of the reservation to be associated with the TimePeriod object
	 * @param startDate Start date of the reservation
	 * @param endDate End date of the reservation
	 */
	public void addTimePeriod(String id, LocalDate startDate, LocalDate endDate) {
		TimePeriod period = new TimePeriod(id, startDate, endDate);
		this.occupancy.add(period);
	}
	
	/**
	 * This method removes the TimePeriod object stored in the Room object
	 * that is associated with a particular reservation ID
	 * @param id ReservationID to remove TimePeriod objects for
	 */
	public void removeTimePeriods(String id) {
		Iterator<TimePeriod> itr = this.occupancy.iterator();
		while (itr.hasNext()) {
			if (itr.next().getReservationID().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	/**
	 * This method returns the occupancy information of the room
	 * @return ArrayList containing all TimePeriod objects associated with this room
	 */
	public ArrayList<TimePeriod> getOccupancy(){
		return this.occupancy;
	}
	
	/**
	 * This is a helper function to check whether the start and end dates of a reservation
	 * collide with a given existing TimePeriod object
	 * @param startDate Start date of the reservation (to be approved)
	 * @param endDate End date of the reservation (to be approved)
	 * @param period TimePeriod object of an existing reservation to be checked against
	 * @return
	 */
	private boolean checkDateRanges(LocalDate startDate, LocalDate endDate, TimePeriod period) {
		if (startDate.compareTo(period.getEndDate()) > 0){
			return true;
		}
		else if (endDate.compareTo(period.getStartDate()) < 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Room [name=" + name + ", size=" + size + ", occupancy=" + occupancy + "]";
	}
}
