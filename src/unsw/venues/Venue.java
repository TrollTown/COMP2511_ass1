package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

import unsw.venues.Room.Size;

public class Venue {
	private String name;
	private ArrayList<Room> rooms;
	
	/**
	 * Constructor for Venue objects: 2 args
	 * @param name Name of the venue
	 */
	public Venue (String name) {
		this.name = name;
		this.rooms = new ArrayList<Room>();
	}
	
	/**
	 * This method returns the name of a venue
	 * @return Name of the venue
	 */
	public String getName() {
		return this.name;
	}
	
	/** 
	 * This method returns a list of all the rooms within a venue
	 * @return ArrayList of Room objects associated with venue
	 */
	public ArrayList<Room> getRooms(){
		return this.rooms;
	}
	
	/**
	 * This method adds a new room to the list of rooms associated with a venue
	 * @param name Name of the new room
	 * @param size Size of the new room
	 */
	public void addRoom(String name, String size){
		Room newRoom = new Room(name, Size.valueOf(size));
		this.rooms.add(newRoom);
	}
	
	/**
	 * This method attempts to allocate rooms for a given start date, end date
	 * and number of small, medium & large rooms at a given venue
	 * @param id Reservation ID
	 * @param startDate Start date of the reservation
	 * @param endDate End date of the reservation
	 * @param small Number of small rooms requested
	 * @param medium Number of medium rooms requested
	 * @param large Number of large rooms requested
	 * @return The list of rooms allocated to the reservation OR if unsuccessful in fulfilling the request, an empty list
	 */
	public ArrayList<Room> requestRooms(String id, LocalDate startDate, LocalDate endDate, int small, int medium, int large){
		ArrayList<Room> smallRooms = findRooms(id, startDate, endDate, Size.small);
		ArrayList<Room> mediumRooms = findRooms(id, startDate, endDate, Size.medium);
		ArrayList<Room> largeRooms = findRooms(id, startDate, endDate, Size.large);
		
		if (smallRooms.size() < small || mediumRooms.size() < medium || largeRooms.size() < large) {
			return new ArrayList<Room>(); // Return empty arraylist (no rooms allocated as request cannot be fulfilled)
		}
		ArrayList<Room> allocatedRooms = new ArrayList<Room>();
		
		int smallFilled = 0;
		int mediumFilled = 0;
		int largeFilled = 0;
		
		while (smallFilled < small) {
			smallRooms.get(smallFilled).addTimePeriod(id, startDate, endDate);
			allocatedRooms.add(smallRooms.get(smallFilled));
			smallFilled++;
		}
		
		while (mediumFilled < medium) {
			mediumRooms.get(mediumFilled).addTimePeriod(id, startDate, endDate);
			allocatedRooms.add(mediumRooms.get(mediumFilled));
			mediumFilled++;
		}
		
		while (largeFilled < large) {
			largeRooms.get(largeFilled).addTimePeriod(id, startDate, endDate);
			allocatedRooms.add(largeRooms.get(largeFilled));
			largeFilled++;
		}
		
		return allocatedRooms;
	}
	
	/**
	 * This method checks if a reservation change can be fulfilled by a given venue
	 * @param id ID of the reservation
	 * @param startDate Start date of the reservation
	 * @param endDate End date of the reservation
	 * @param small Number of small rooms requested
	 * @param medium Number of medium rooms requested
	 * @param large Number of large rooms requested
	 * @return
	 */
	public boolean checkChange(String id, LocalDate startDate, LocalDate endDate, int small, int medium , int large) {
		ArrayList<Room> smallRooms = findRooms(id, startDate, endDate, Size.small);
		ArrayList<Room> mediumRooms = findRooms(id, startDate, endDate, Size.medium);
		ArrayList<Room> largeRooms = findRooms(id, startDate, endDate, Size.large);
		
		if (smallRooms.size() < small || mediumRooms.size() < medium || largeRooms.size() < large) {
			return false;
		}
		return true;
	}
	
	/**
	 * This is a helper function to get all the rooms available within a venue for a given
	 * start date, end date and size. 
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @param size
	 * @return
	 */
	private ArrayList<Room> findRooms(String id, LocalDate startDate, LocalDate endDate, Size size) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		for (int i = 0; i < this.rooms.size(); i++) {
			if (this.rooms.get(i).getSize().equals(size)) {
				if (this.rooms.get(i).checkAvailability(id, startDate, endDate)) {
					roomList.add(this.rooms.get(i));
				}
			}
		}
		return roomList;
	}
	
	
	
	
	
}
