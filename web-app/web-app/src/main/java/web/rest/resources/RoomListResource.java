package web.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import web.rest.resources.RoomResource;

public class RoomListResource extends ResourceSupport {

	private List<RoomResource> rooms = new ArrayList<RoomResource>();

	public List<RoomResource> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomResource> rooms) {
		this.rooms = rooms;
	}
}
