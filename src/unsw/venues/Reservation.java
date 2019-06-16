package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reservation {
	private String id;
	private String venue;
	private ArrayList<Room> rooms;
	private LocalDate startDate;
	private LocalDate endDate;
	
	/**
	 * Constructor for Reservation object: 5 arguments
	 * @param id Reservation ID
	 * @param venue Venue name
	 * @param rooms Array of Room objects associated with reservation
	 * @param startDate Start date of reservation
	 * @param endDate End date of reservation
	 */
	public Reservation(String id, String venue, ArrayList<Room> rooms, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.venue = venue;
		this.rooms = rooms;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * This method returns the ID of a reservation
	 * @return Name/ID of the reservation
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * This method returns the name of the venue associated with the reservation
	 * @return Name of the venue
	 */
	public String getVenue() {
		return this.venue;
	}
	
	/**
	 * This method returns the list of rooms associated with the reservation
	 * @return List of rooms in the reservation
	 */
	public ArrayList<Room> getRooms() {
		return this.rooms;
	}
	
	/**
	 * This method returns the start date of the reservation
	 * @return Start date of the reservation
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * This method sets the start date of the reservation
	 * @param startDate New start date of the reservation
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * This method returns the end date of the reservation
	 * @return End date of the reservation
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	/**
	 * This method sets the end date of the reservation
	 * @param endDate New end date of the reservation
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * This method adds a room to the list of rooms stored in the reservation
	 * @param room New room to be added to the reservation
	 */
	public void addRoom(Room room) {
		this.rooms.add(room);
	}
	
	/**
	 * This method removes a room from the list of rooms stored in the reservations
	 * @param room The room to be removed
	 */
	public void removeRoom(Room room) {
		this.rooms.remove(room);
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", venue=" + venue + ", rooms=" + rooms + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
}
