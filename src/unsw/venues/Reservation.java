package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reservation {
	private String id;
	private String venue;
	private ArrayList<Room> rooms;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Reservation(String id, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
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
}
