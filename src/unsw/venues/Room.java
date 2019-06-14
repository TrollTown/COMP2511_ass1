package unsw.venues;

import java.time.LocalDate;

public class Room {
	private String name;
	private String size;
	
	public Room (String name, String size) {
		this.name = name;
		this.size= size;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSize() {
		return this.size;
	}
	
	public boolean checkAvailability(LocalDate startDate, LocalDate endDate) {
		return false;
	}
	
	public void addTimePeriod(String id, LocalDate startDate, LocalDate endDate) {
		
	}
}
