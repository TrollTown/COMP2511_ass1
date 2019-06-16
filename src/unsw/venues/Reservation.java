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
	 * @return 
	 */
	public String getID() {
		return this.id;
	}
	
	public String getVenue() {
		return this.venue;
	}
	
	public ArrayList<Room> getRooms() {
		return this.rooms;
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public void addRoom(Room room) {
		this.rooms.add(room);
	}
	
	public void removeRoom(Room room) {
		this.rooms.remove(room);
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", venue=" + venue + ", rooms=" + rooms + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
}
