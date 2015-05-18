package web.core.services.util;

import java.util.ArrayList;
import java.util.List;

import web.core.models.entities.Room;

public class RoomList {
	
	private List<Room> rooms = new ArrayList<Room>();


	public RoomList(List<Room> roomlist) {

		this.rooms = roomlist;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}

