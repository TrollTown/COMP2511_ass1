package unsw.venues;

import java.util.ArrayList;

public class Venue {
	private String name;
	private ArrayList<Room> rooms;
	
	public Venue (String name) {
		this.name = name;
		this.rooms = new ArrayList<Room>();
	}
	
	public ArrayList<Room> getRooms(){
		return this.rooms;
	}
	
	public void addRoom(String name, String size){
		Room newRoom = new Room(name, size);
		this.rooms.add(newRoom);
	}
}
