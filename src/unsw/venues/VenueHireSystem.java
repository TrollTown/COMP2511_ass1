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
    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
    public VenueHireSystem() {
        // TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public JSONObject cancel(String id2) {
			// TODO Auto-generated method stub
			return null;
		}
	
	public JSONObject list(String id3) {
			// TODO Auto-generated method stub
			return null;
		}
	private void addRoom(String venue, String room, String size) {
        // TODO Process the room command
    }

    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        JSONObject result = new JSONObject();

        
        result.put("status", "success");
        result.put("venue", "Zoo");

        JSONArray rooms = new JSONArray();
        rooms.put("Penguin");
        rooms.put("Hippo");

        result.put("rooms", rooms);
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
    
    public ArrayList<Reservation> getReservations(){
    	return this.reservations;
    }
}
