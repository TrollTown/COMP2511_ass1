package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {
	private String name;
	private String size;
	private ArrayList<TimePeriod> occupancy;
	
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
		for (int i = 0; i < this.occupancy.size(); i++) {
			if (checkDateRanges(startDate, endDate, this.occupancy.get(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	public void addTimePeriod(String id, LocalDate startDate, LocalDate endDate) {
		TimePeriod period = new TimePeriod(id, startDate, endDate);
		this.occupancy.add(period);
	}
	
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
}
