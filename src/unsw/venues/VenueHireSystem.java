/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Robert Clifton-Everest
 *
 */
public class VenueHireSystem {
	private ArrayList<Reservation> reservations;
	private ArrayList<Venue> venues;
    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
    public VenueHireSystem() {
        this.reservations = new ArrayList<Reservation>();
        this.venues = new ArrayList<Venue>();
    }
    
    /**
     * Private function that takes in a JSONObject containing a command and executes it by calling
     * the corresponding function in the switch/case statement
     * @param json JSONObject in the command format
     */
    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            addRoom(venue, room, size);
            break;

        case "request":
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            JSONObject result = request(id, start, end, small, medium, large);

            System.out.println(result.toString(2));
            break;

        // TODO Implement other commands
        case "change":
        	String id1 = json.getString("id");
        	LocalDate start1 = LocalDate.parse(json.getString("start"));
        	LocalDate end1 = LocalDate.parse(json.getString("end"));
        	int small1 = json.getInt("small");
        	int medium1 = json.getInt("medium");
        	int large1 = json.getInt("large");
        	
        	JSONObject changeOutput = change(id1, start1, end1, small1, medium1, large1);
        	
        	System.out.println(changeOutput.toString(2));
        	break;
        	
        case "cancel":
        	String id2 = json.getString("id");
        	
        	JSONObject cancelOutput = cancel(id2);
        	
        	System.out.println(cancelOutput.toString(2));
        	break;
        
        case "list":
        	String venue2 = json.getString("venue");
        	JSONArray listOutput = list(venue2);
        	
        	System.out.println(listOutput.toString(2));
        	break;
        }
    }

    /**
     * This method processes any change requests handled by the command parser
     * @param id1 Reservation ID of the reservation to be changed
     * @param start1 New start date of the reservation
     * @param end1 New end date of the reservation
     * @param small1 New number of small rooms requested
     * @param medium1 New number of medium rooms requested
     * @param large1 New number of large rooms requested
     * @return A JSONObject detailing the new reservation OR { "status" : "rejected" } if failed
     */
	private JSONObject change(String id1, LocalDate start1, LocalDate end1, int small1, int medium1, int large1) {
		JSONObject result = new JSONObject();
        JSONArray rooms = new JSONArray();
        boolean allocationSuccessful = false;
        for (int i = 0; i < this.venues.size(); i++) {
        	Venue venue = this.venues.get(i);
        	if (venue.checkChange(id1, start1, end1, small1, medium1, large1)) {
        		Reservation previousReservation = getReservationByID(id1);
        		// Clear rooms in reservation
        		Iterator<Room> itr = previousReservation.getRooms().iterator();
        		while (itr.hasNext()) {
        			itr.next().removeTimePeriods(id1);
        			itr.remove();
        		}
        		// Change dates of reservations
        		previousReservation.setStartDate(start1);
        		previousReservation.setEndDate(end1);
        		
        		// Set new rooms
        		ArrayList<Room> RoomsAllocated = new ArrayList<Room>();
        		RoomsAllocated = this.venues.get(i).requestRooms(id1, start1, end1, small1, medium1, large1);
        		for (int j = 0; j < RoomsAllocated.size(); j++) {
        			rooms.put(RoomsAllocated.get(j).getName());
        			previousReservation.addRoom(RoomsAllocated.get(j));
        		}
        		result.put("venue", this.venues.get(i).getName());
        		allocationSuccessful = true;
        		result.put("status", "success");
        		result.put("rooms", rooms);
        		break;
        	}
        	
        }
        if (allocationSuccessful == false) {
        	result.put("status", "rejected");
        }
        
		return result;
	}
	
	/**
	 * This method processes any cancellation requests handled by the command parser
	 * @param id2 Reservation ID of the reservation to be cancelled
	 * @return JSONObject with status either success or rejected depending on
	 * whether or not the reservation could be found on the system
	 */
	private JSONObject cancel(String id2) {
		JSONObject result = new JSONObject();
		Reservation reservation = getReservationByID(id2);
		if (reservation != null) {
			// For each room contained in the reservation, remove the corresponding time period object
			for (int i = 0; i < reservation.getRooms().size(); i++) {
				reservation.getRooms().get(i).removeTimePeriods(id2);
			}
			this.reservations.remove(getReservationByID(id2));
			result.put("status", "success");
		}
		else {
			result.put("status", "rejected");
		}
		return result;
	}
	
	/**
	 * This method processes any list requests handled by the command parser
	 * @param venue The venue which should have its reservation information displayed
	 * @return A JSONArray of objects which detail the reservation information for each room in the venue
	 */
	private JSONArray list(String venue) {
		JSONArray result = new JSONArray();
		Venue venueObj = getVenueByName(venue);
		for (int i = 0; i < venueObj.getRooms().size(); i++) {
			Room currRoom = venueObj.getRooms().get(i);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("room", currRoom.getName());
			ArrayList<JSONObject> reservations = new ArrayList<JSONObject>();
			Collections.sort(currRoom.getOccupancy());
			for (int j = 0; j < currRoom.getOccupancy().size(); j++) {
				TimePeriod timeSlot = currRoom.getOccupancy().get(j);
				JSONObject reservationObj = new JSONObject();
				String reservationID = timeSlot.getReservationID();
				String startDate = timeSlot.getStartDate().toString();
				String endDate = timeSlot.getEndDate().toString();
				reservationObj.put("id", reservationID);
				reservationObj.put("start", startDate);
				reservationObj.put("end", endDate);
				reservations.add(reservationObj);
			}
			jsonObj.put("reservations", reservations);
			result.put(jsonObj);
		}
		return result;
	}
	
	/**
	 * This is a private method that calls the addRoom method of the relevant venue so that a room is added.
	 * If the venue does not exist, it is first created and then the room is added.
	 * @param venue Name of the venue to which the room is to be added to
	 * @param roomName Name of the room to be added
	 * @param size Size of the room
	 */
	private void addRoom(String venue, String roomName, String size) {
		// If venue doesn't already exist add it and then add the room
		if (getVenueByName(venue) == null) {
			addVenue(venue);
        }	
	    getVenueByName(venue).addRoom(roomName, size);
    }
	
	/**
	 * This is a private method that requests a room by calling the requestRooms method on venues in the order
	 * they were entered into the system.
	 * @param id ID of the reservation
	 * @param start	Start date of the reservation
	 * @param end End date of the reservation
	 * @param small Number of small rooms requested
	 * @param medium Number of medium rooms requested
	 * @param large Number of large rooms requested
	 * @return Returns a JSONObject detailing the reservation if successful or status: rejected if unsuccessful
	 */
    private JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
    	JSONObject result = new JSONObject();
        JSONArray rooms = new JSONArray();
     
        boolean allocationSuccessful = false;
        
        for (int i = 0; i < this.venues.size(); i++) {
        	ArrayList<Room> RoomsAllocated = new ArrayList<Room>();
        	RoomsAllocated = this.venues.get(i).requestRooms(id, start, end, small, medium, large);
        	
        	if (RoomsAllocated.size() != 0) {
        		allocationSuccessful = true;
        		result.put("status", "success");
        		result.put("venue", this.venues.get(i).getName());
        		
        		for (int j = 0; j < RoomsAllocated.size(); j++) {
        			rooms.put(RoomsAllocated.get(j).getName());
        			
        		}
        		
        		// Add the reservation to the venue hire system
        		Reservation newReservation = new Reservation(id, this.venues.get(i).getName(), RoomsAllocated, start, end);
        		this.reservations.add(newReservation);
        		
        		break;
        	}
        }
        
        if (allocationSuccessful == true) {
        	result.put("rooms", rooms);
        }
        
        if (allocationSuccessful == false) {
        	result.put("status", "rejected");
        }
        return result;
    }
    
    /**
     * This is the main function of the venue hire system that takes in input
     * and turns each line into a JSONObject which is then passed to the
     * processCommand function to be executed
     * @param args
     */
    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            //System.out.println(line);
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                
                system.processCommand(command);
                
            }
        }
        sc.close();
    }
    
    /**
     * This method returns the relevant reservation object given its ID
     * @param id ID of the reservation to be retrieved
     * @return Reservation object with the corresponding ID or null
     * if the ID could not be found on the system
     */
    public Reservation getReservationByID(String id){
    	for (int i = 0; i < this.reservations.size(); i++) {
    		if (this.reservations.get(i).getID().equals(id)) {
    			return this.reservations.get(i);
    		}
    	}
    	return null;
    }
    
    /**
     * This method returns the list of venues stored within the system
     * @return ArrayList of all venues stored in the system
     */
    public ArrayList<Venue> getVenues(){
    	return this.venues;
    }
    
    /**
     * This private method adds a venue to the system 
     * (called by the addRoom function if a venue cannot be found)
     * @param name Name of the venue to be added
     */
    private void addVenue(String name) {
    	Venue newVenue = new Venue(name);
    	this.venues.add(newVenue);
    }
    
    /**
     * This private method returns the relevant venue object given its name
     * @param name Name of the venue
     * @return Venue object with the corresponding name
     */
    private Venue getVenueByName(String name) {
    	for (int i = 0; i < this.venues.size(); i++) {
    		if (this.venues.get(i).getName().equals(name)) {
    			return this.venues.get(i);
    		}
    	}
    	return null;
    }
}
