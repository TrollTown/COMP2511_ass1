package unsw.venues;

import java.time.LocalDate;

public class TimePeriod implements Comparable<TimePeriod> {
	private String reservationID;
	private LocalDate startDate;
	private LocalDate endDate;
	
	/**
	 * Constructor for TimePeriod object: 3 arguments
	 * @param reservationID Reservation ID
	 * @param startDate Start date of reservation
	 * @param endDate End date of reservation
	 */
	public TimePeriod(String reservationID, LocalDate startDate, LocalDate endDate) {
		this.reservationID = reservationID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * This method returns the reservation ID of the TimePeriod object
	 * @return reservation ID of the TimePeriod object
	 */
	public String getReservationID() {
		return this.reservationID;
	}
	
	/**
	 * This method returns the start date of the TimePeriod object
	 * @return start date of the TimePeriod object
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * This method returns the end date of the TimePeriod object
	 * @return end date of the TimePeriod object
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}

	@Override
	public String toString() {
		return "TimePeriod [reservationID=" + reservationID + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	@Override
	public int compareTo(TimePeriod o) {
		return this.startDate.compareTo(o.getStartDate());
	}
}
