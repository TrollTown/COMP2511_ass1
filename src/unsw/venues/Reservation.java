package unsw.venues;

import java.util.ArrayList;

public class Reservation {
	private String id;
	private ArrayList<Room> rooms;
	
	/**
	 * Constructor for Reservation object: 5 arguments
	 * @param id Reservation ID
	 * @param venue Venue name
	 * @param rooms Array of Room objects associated with reservation
	 * @param startDate Start date of reservation
	 * @param endDate End date of reservation
	 */
	public Reservation(String id, String venue, ArrayList<Room> rooms) {
		this.id = id;
		this.rooms = rooms;
	}
	
	/**
	 * This method returns the ID of a reservation
	 * @return Name/ID of the reservation
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * This method returns the list of rooms associated with the reservation
	 * @return List of rooms in the reservation
	 */
	public ArrayList<Room> getRooms() {
		return this.rooms;
	}
	
	/**
	 * This method adds a room to the list of rooms stored in the reservation
	 * @param room New room to be added to the reservation
	 */
	public void addRoom(Room room) {
		this.rooms.add(room);
	}
}
