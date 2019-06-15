/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
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
        	String id3 = json.getString("id");
        	
        	JSONObject listOutput = list(id3);
        	
        	System.out.println(listOutput.toString(2));
        	break;
        }
    }

   
	

	public JSONObject change(String id1, LocalDate start1, LocalDate end1, int small1, int medium1, int large1) {
		JSONObject result = new JSONObject();
        JSONArray rooms = new JSONArray();
        
        
		return null;
	}
	
	public JSONObject cancel(String id2) {
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
	
	public JSONObject list(String id3) {
		JSONArray result = new JSONArray();
		for (int i = 0; i < this.venues.size(); i++) {
			Venue venue = this.venues.get(i);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("room", venue);
			ArrayList<JSONObject> reservations = new ArrayList<JSONObject>();
			for (int j = 0; j < this.reservations.size(); j++) {
				Reservation currReservation = this.reservations.get(j);
				JSONObject reservationObj = new JSONObject();
				String reservationID = currReservation.getID();
				String startDate = currReservation.getStartDate().toString();
				String endDate = currReservation.getEndDate().toString();
				reservationObj.put("id", reservationID);
				reservationObj.put("start", startDate);
				reservationObj.put("end", endDate);
				
			}
			result.put(jsonObj);
		}
		return result;
	}
	private void addRoom(String venue, String roomName, String size) {
		if (getVenueByName(venue) != null) {
        		this.venues.get(i).addRoom(roomName, size);
        }
		// If venue doesn't already exist add it and then add the room
		else {
			addVenue(venue);
	        getVenueByName(venue).addRoom(venue, size);
		}
        
    }

    public JSONObject request(String id, LocalDate start, LocalDate end,
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
        		Reservation newReservation = new Reservation(id, this.venues.get(i).getName(), RoomsAllocated ,start, end);
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

    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }
    
    public Reservation getReservationByID(String id){
    	for (int i = 0; i < this.reservations.size(); i++) {
    		if (this.reservations.get(i).getID().equals(id)) {
    			return this.reservations.get(i);
    		}
    	}
    	return null;
    }
    
    public ArrayList<Venue> getVenues(){
    	return this.venues;
    }
    
    private void addVenue(String name) {
    	Venue newVenue = new Venue(name);
    	this.venues.add(newVenue);
    }
    
    private Venue getVenueByName(String name) {
    	for (int i = 0; i < this.venues.size(); i++) {
    		if (this.venues.get(i).getName().equals(name)) {
    			return this.venues.get(i);
    		}
    	}
    	return null;
    }
}
