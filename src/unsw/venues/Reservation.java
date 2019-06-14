package unsw.venues;

import java.time.LocalDate;

public class Reservation {
	private String id;
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
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	public LocalDate getEndDate() {
		return this.endDate;
	}
}
