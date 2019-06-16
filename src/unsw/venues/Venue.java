package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venue {
	private String name;
	private ArrayList<Room> rooms;
	
	public Venue (String name) {
		this.name = name;
		this.rooms = new ArrayList<Room>();
	}
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Room> getRooms(){
		return this.rooms;
	}
	
	public void addRoom(String name, String size){
		Room newRoom = new Room(name, size);
		this.rooms.add(newRoom);
	}
	
	
	
	public ArrayList<Room> requestRooms(String id, LocalDate startDate, LocalDate endDate, int small, int medium, int large){
		ArrayList<Room> smallRooms = getRooms(id, startDate, endDate, "small");
		ArrayList<Room> mediumRooms = getRooms(id, startDate, endDate, "medium");
		ArrayList<Room> largeRooms = getRooms(id, startDate, endDate, "large");
		
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
	
	public boolean checkChange(String id, LocalDate startDate, LocalDate endDate, int small, int medium , int large) {
		ArrayList<Room> smallRooms = getRooms(id, startDate, endDate, "small");
		ArrayList<Room> mediumRooms = getRooms(id, startDate, endDate, "medium");
		ArrayList<Room> largeRooms = getRooms(id, startDate, endDate, "large");
		
		if (smallRooms.size() < small || mediumRooms.size() < medium || largeRooms.size() < large) {
			return false;
		}
		return true;
	}
	
	
	private ArrayList<Room> getRooms(String id, LocalDate startDate, LocalDate endDate, String size) {
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
