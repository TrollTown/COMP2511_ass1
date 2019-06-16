package unsw.venues;

import java.time.LocalDate;

public class TimePeriod {
	private String reservationID;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public TimePeriod(String reservationID, LocalDate startDate, LocalDate endDate) {
		this.reservationID = reservationID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getReservationID() {
		return this.reservationID;
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	public LocalDate getEndDate() {
		return this.endDate;
	}

	@Override
	public String toString() {
		return "TimePeriod [reservationID=" + reservationID + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
